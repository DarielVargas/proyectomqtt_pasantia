package knight.nameless.starter.services;

import knight.nameless.starter.entities.Accounts;
import knight.nameless.starter.entities.LoginActivityLog;
import knight.nameless.starter.entities.Machine;
import knight.nameless.starter.entities.enums.Role;
import knight.nameless.starter.repositories.LoginActivityLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LoginActivityLogServices {

    private final LoginActivityLogRepository loginActivityLogRepository;

    public LoginActivityLogServices(LoginActivityLogRepository loginActivityLogRepository) {
        this.loginActivityLogRepository = loginActivityLogRepository;
    }

    public void save(LoginActivityLog loginActivityLog) {
        loginActivityLogRepository.save(loginActivityLog);
    }

    public Optional<LoginActivityLog> findByAccountAndLogoutTimeNull(Accounts accounts) {
        return loginActivityLogRepository.findFirstByAccountAndLogoutTimeIsNull(accounts);
    }

    public List<LoginActivityLog> findAllL2LoginActivityLogWhereLogoutTimeIsNull() {
        return loginActivityLogRepository.findAllByMachine_IdAndLogoutTimeIsNull(1);
    }

    public Optional<LoginActivityLog> findByLogoutTimeIsNullAndMachine(Machine machine) {
        return loginActivityLogRepository.findFirstByLogoutTimeIsNullAndMachine_Id(machine.getId());
    }

    public Optional<LoginActivityLog> findByLogoutTimeIsNullAndMachineAndRole(Machine machine, Role role) {
        return loginActivityLogRepository.findFirstByLogoutTimeIsNullAndMachine_IdAndAccount_Role(machine.getId(), role);
    }

    public Optional<LoginActivityLog> findByLastLoginAndMachineAndRole(int machineId, Role role) {
        return loginActivityLogRepository.findFirstByMachine_IdAndAccount_RoleOrderByLoginTimeDesc(machineId, role);
    }

    public Optional<LoginActivityLog> findFirstByMachineAndDateBetween(Machine machine, LocalDateTime start, LocalDateTime end) {
        return loginActivityLogRepository.findFirstByMachine_IdAndAccount_RoleAndLoginTimeGreaterThanEqualAndLoginTimeLessThanEqualOrderByLoginTime(machine.getId(), Role.Operator, start, end);
    }
}
