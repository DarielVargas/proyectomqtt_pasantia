package knight.nameless.starter.mqtt;

import knight.nameless.starter.entities.*;
import knight.nameless.starter.entities.enums.Role;
import knight.nameless.starter.models.accounts.*;
import knight.nameless.starter.models.hmi.RangoTurno;
import knight.nameless.starter.models.shift.ShiftModel;
import knight.nameless.starter.mqtt.config.RawMessage;
import knight.nameless.starter.services.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tocrhz.mqtt.publisher.MqttPublisher;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class MqttLogin {

    private final AccountsServices accountsServices;
    private final LoginActivityLogServices loginActivityLogServices;
    private final MqttPublisher publisher;
    private final MachineServices machineServices;
    private final ModelMapper modelMapper;
    private final ShiftServices shiftServices;
    private final RestTemplate restTemplate;
    Logger logger = LoggerFactory.getLogger(MqttLogin.class);
    @Value("${api.url}")
    private String api;
    private final String BaseTopic = "Selladora/L1/HMI/Entrada/";

    public MqttLogin(AccountsServices accountsServices,
                     LoginActivityLogServices loginActivityLogServices,
                     MqttPublisher publisher,
                     MachineServices machineServices,
                     ModelMapper modelMapper,
                     ShiftServices shiftServices,
                     RestTemplate restTemplate) {

        this.accountsServices = accountsServices;
        this.loginActivityLogServices = loginActivityLogServices;
        this.publisher = publisher;
        this.machineServices = machineServices;
        this.modelMapper = modelMapper;
        this.shiftServices = shiftServices;
        this.restTemplate = restTemplate;
    }

    public void loginOperador(RawMessage message) {

        try {

            logger.info("login topic message {}", message);

            ObjectMapper mapper = new ObjectMapper();
            LoginModel loginModel = mapper.readValue(message.getPayload(), LoginModel.class);
            Optional<Accounts> accountByPin = accountsServices.findByPinAndRole(loginModel.getPin(), Role.Operator);
            boolean valid = accountByPin.isPresent();

            logger.info("login topic has pin? {}", valid);

            Optional<Machine> optionalMachine = machineServices.getTubeMillMachineL2();

            optionalMachine.ifPresent(machine -> accountByPin.ifPresentOrElse(accounts -> {

                logger.info("login topic machine found");

                LoginResponse response = modelMapper.map(valid ? accountByPin.get() : new Accounts(), LoginResponse.class);

                response.setCorrecto(valid);
                response.setIncorrecto(!valid);
                publisher.send(BaseTopic + "ValidarLogin", response);
                publisher.send(BaseTopic + "Usuario", new CurrentAccount(accounts.getPin()));

                var loginActivityLogs = loginActivityLogServices.findAllL2LoginActivityLogWhereLogoutTimeIsNull();

                loginActivityLogs.forEach(loginActivityLog -> {
                    loginActivityLog.setLogoutTime(LocalDateTime.now());
                    loginActivityLog.setNote("Forced logout");
                    loginActivityLogServices.save(loginActivityLog);
                    logger.info("Forced logout");
                });

                logInByAccountAndMachine(accounts, machine);

                logger.info("login valido");

                Optional<ShiftModel> optionalShift = shiftServices.findShiftByCurrentTime(LocalDateTime.now(), machine);
                optionalShift.ifPresentOrElse(shift -> {

                    logger.error("turno encontrado");

                    estadoTurno(shift);

                    notificarCambioTurno("inicioTurno", true, shift.getStartTime());
                    notificarCambioTurno("finTurno", false, shift.getEndTime());

                }, () -> {

                    logger.error("turno no encontrado");

                    publisher.send(BaseTopic + "EstadoMaquina", new RangoTurno(false));

                    ShiftModel shift = shiftServices.findShiftCloseToNow(LocalDateTime.now(), machine);

                    if (shift != null) {
                        notificarCambioTurno("inicioTurno", true, shift.getStartTime());
                        notificarCambioTurno("finTurno", false, shift.getEndTime());
                    } else {
                        logger.error("No se encontró turno cercano para la maquina");
                    }
                });

            }, () -> {

                logger.info("pin no valido");

                LoginResponse response = modelMapper.map(valid ? accountByPin.get() : new Accounts(), LoginResponse.class);
                response.setCorrecto(valid);
                response.setIncorrecto(!valid);
                publisher.send("ValidarLogin", response);
            }));
        } catch (JsonProcessingException e) {

            logger.error(e.getMessage());
        }
    }

    public void confirmarTurno(RawMessage message) {

        try {

            ObjectMapper mapper = new ObjectMapper();
            ConfirmarTurno confirmarTurno = mapper.readValue(message.getPayload(), ConfirmarTurno.class);
            Optional<Machine> optionalMachine = machineServices.findById(6);
            Optional<Accounts> accountsServicesByPin = accountsServices.findByPinAndRole(confirmarTurno.getPin(), Role.Operator);
            optionalMachine.ifPresent(machine -> accountsServicesByPin.ifPresent(accounts -> {

                if (confirmarTurno.isIncorrecto()) {

                    try {
                        restTemplate.postForEntity(api + "Accounts/shift-alert?machineId=" + machine.getId() + "&operatorId=" + accounts.getId(), null, String.class);
                    } catch (Exception e) {
                        logger.error("Error notificando turno incorrecto {}", e.getMessage());
                    }
                }
            }));
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }

    private void notificarCambioTurno(String nombre, boolean dentro, Duration shift) {

        var time = LocalDateTime.now().withHour(shift.toHoursPart()).withMinute(shift.toMinutesPart());

        Instant instant = time.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);

        Timer timer = new Timer(nombre);
        timer.schedule(new RangoTurnoTask(dentro), date);
    }

    private void estadoTurno(ShiftModel shift) {

        String startShift = String.format("%02d:%02d",
                shift.getStartTime().toHoursPart(),
                shift.getStartTime().toMinutesPart()
        );

        String endShift = String.format("%02d:%02d",
                shift.getEndTime().toHoursPart(),
                shift.getEndTime().toMinutesPart()
        );

        publisher.send(BaseTopic + "Turno", new CurrentShift(startShift, endShift));
        publisher.send(BaseTopic + "EstadoMaquina", new RangoTurno(true));
    }

    private void logInByAccountAndMachine(Accounts accounts, Machine machine) {

        LoginActivityLog loginActivityLog = new LoginActivityLog();
        loginActivityLog.setLoginTime(LocalDateTime.now());
        loginActivityLog.setAccount(accounts);
        loginActivityLog.setMachine(machine);
        loginActivityLogServices.save(loginActivityLog);
    }

    public void logout(RawMessage message) {

        try {

            ObjectMapper mapper = new ObjectMapper();

            LoginModel loginModel = mapper.readValue(message.getPayload(), LoginModel.class);
            Optional<Accounts> accountByPin = accountsServices.findByPin(loginModel.getPin());

            accountByPin.ifPresent(_ -> {

                var loginActivityLogs = loginActivityLogServices.findAllL2LoginActivityLogWhereLogoutTimeIsNull();

                loginActivityLogs.forEach(loginActivityLog -> {
                    loginActivityLog.setLogoutTime(LocalDateTime.now());
                    loginActivityLogServices.save(loginActivityLog);
                });

                publisher.send(BaseTopic + "Usuario", new CurrentAccount());
                publisher.send(BaseTopic + "ValidarLogin", new LoginResponse());

            });
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }

    public void loginMecanico(RawMessage message) {

        try {

            ObjectMapper mapper = new ObjectMapper();
            LoginModel loginModel = mapper.readValue(message.getPayload(), LoginModel.class);
            Optional<Machine> optionalMachine = machineServices.getTubeMillMachineL2();
            Optional<Accounts> accountsServicesByPin = accountsServices.findByPinAndRole(loginModel.getPin(), Role.Maintenance);
            boolean valid = accountsServicesByPin.isPresent();
            LoginResponse response = modelMapper.map(valid ? accountsServicesByPin.get() : new Accounts(), LoginResponse.class);
            response.setCorrecto(valid);
            response.setIncorrecto(!valid);
            publisher.send(BaseTopic + "ValidarLoginMecanico", response);

            optionalMachine.ifPresent(machine -> accountsServicesByPin.ifPresent(accounts -> {

                Optional<LoginActivityLog> optionalLoginActivityLog = loginActivityLogServices.findByAccountAndLogoutTimeNull(accounts);
                publisher.send(BaseTopic + "Usuario", new CurrentAccount(accounts.getPin()));

                optionalLoginActivityLog.ifPresentOrElse(loginActivityLog -> {

                    loginActivityLog.setLogoutTime(LocalDateTime.now());
                    loginActivityLog.setNote("Forced logout");
                    loginActivityLogServices.save(loginActivityLog);
                    logInByAccountAndMachine(accounts, machine);

                }, () -> logInByAccountAndMachine(accounts, machine));
            }));
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }

    private class RangoTurnoTask extends TimerTask {

        private final boolean dentro;

        protected RangoTurnoTask(boolean dentro) {
            super();
            this.dentro = dentro;
        }

        @Override
        public void run() {
            publisher.send(BaseTopic + "EstadoMaquina", new RangoTurno(dentro));
        }
    }
}
