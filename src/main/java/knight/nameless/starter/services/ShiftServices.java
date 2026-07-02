package knight.nameless.starter.services;

import knight.nameless.starter.entities.Machine;
import knight.nameless.starter.models.shift.ShiftModel;
import knight.nameless.starter.repositories.ShiftRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ShiftServices {

    private final ShiftRepository shiftRepository;

    public ShiftServices(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    public List<ShiftModel> getAllByMachine(Machine machine) {
        return shiftRepository.findAllByMachineAndEnabledOrderByStartTime(machine, true);
    }

    public List<ShiftModel> getAllByMachine(int machineId) {
        return shiftRepository.findAllByMachine_IdAndEnabledOrderByStartTime(machineId, true);
    }

    public ShiftModel getShiftByTime(LocalDateTime localDateTime, Machine machine) {
        Optional<ShiftModel> optional = findShiftByCurrentTime(localDateTime, machine);
        return optional.orElse(getShiftByMachineAndDate(machine, localDateTime));

    }

    public ShiftModel getShiftByTime(LocalDateTime localDateTime, int machine) {
        Optional<ShiftModel> optional = findShiftByCurrentTime(localDateTime, machine);
        return optional.orElse(getShiftByMachineAndDate(machine, localDateTime));

    }

    public ShiftModel getShiftByMachineAndDate(Machine machine, LocalDateTime localDateTime) {

        List<ShiftModel> shiftList = getAllByMachine(machine);
        for (ShiftModel shift : shiftList) {

            //En .net DayOfWeek el domingo es 0, en java 7
            var hasDay = shift.getDaysOfWeek().contains(0) && localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY || shift.getDaysOfWeek().contains(localDateTime.getDayOfWeek().getValue());
            if (hasDay) return shift;
        }

        return shiftList.getFirst();

    }

    public ShiftModel getShiftByMachineAndDate(int machine, LocalDateTime localDateTime) {

        List<ShiftModel> shiftList = getAllByMachine(machine);
        for (ShiftModel shift : shiftList) {

            //En .net DayOfWeek el domingo es 0, en java 7
            var hasDay = shift.getDaysOfWeek().contains(0) && localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY || shift.getDaysOfWeek().contains(localDateTime.getDayOfWeek().getValue());
            if (hasDay) return shift;
        }

        return shiftList.getFirst();

    }

    public ShiftModel findShiftCloseToNow(LocalDateTime localDateTime, Machine machine) {

        List<ShiftModel> shiftList = getAllByMachine(machine);

        // Filtrar los turnos de hoy, y que la hora de inicio sea antes de la hora de
        // inicio del turno
        var filtered = shiftList.stream().filter((ShiftModel shift) -> {
            LocalDateTime time = LocalDateTime.now().withHour(shift.getStartTime().toHoursPart()).withMinute(shift.getStartTime().toMinutesPart());

            boolean timeBeforeStart = !Duration.between(localDateTime, time).isNegative();
            //En .net DayOfWeek el domingo es 0, en java 7
            var dayOfShift = shift.getDaysOfWeek().contains(0) && localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY || shift.getDaysOfWeek().contains(localDateTime.getDayOfWeek().getValue());

            return dayOfShift && timeBeforeStart;
        }).toList();

        if (!filtered.isEmpty()) {

            var shift = filtered.stream().min((o1, o2) -> {
                Duration startTime = o1.getStartTime();

                var start = LocalDateTime.now().withHour(startTime.toHoursPart()).withMinute(startTime.toMinutesPart());

                Duration startTime2 = o2.getStartTime();

                var start2 = LocalDateTime.now().withHour(startTime2.toHoursPart()).withMinute(startTime2.toMinutesPart());

                Duration first = Duration.between(localDateTime, start);
                Duration second = Duration.between(localDateTime, start2);
                return first.compareTo(second);
            });
            return shift.orElse(null);
        } else if (!shiftList.isEmpty()) return shiftList.getFirst();
        return null;
    }

    public Optional<ShiftModel> findShiftByCurrentTime(LocalDateTime localDateTime, Machine machine) {

        List<ShiftModel> shiftList = getAllByMachine(machine);

        for (ShiftModel shift : shiftList) {

            //En .net DayOfWeek el domingo es 0, en java 7
            var hasDay = shift.getDaysOfWeek().contains(0) && localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY || shift.getDaysOfWeek().contains(localDateTime.getDayOfWeek().getValue());

            if (hasDay) {

                boolean inRange;
                Duration startTime = shift.getStartTime();
                Duration endTime = shift.getEndTime();

                var start = localDateTime.withHour(startTime.toHoursPart()).withMinute(startTime.toMinutesPart()).withSecond(0).withNano(0);

                var end = localDateTime.withHour(endTime.toHoursPart()).withMinute(endTime.toMinutesPart()).withSecond(59).withNano(0);


                // Si el turno pasa al siguiente día
                if (endTime.compareTo(startTime) < 0) {
                    final LocalDateTime other = localDateTime.withHour(shift.getStartTime().toHoursPart()).withMinute(shift.getStartTime().toMinutesPart());
                    var media = localDateTime.compareTo(other);
                    if (media < 0) {
                        start = start.minusDays(1);
                    } else {
                        end = end.plusDays(1);
                    }
                }

                inRange = localDateTime.isAfter(start) && localDateTime.isBefore(end);

                if (inRange) return Optional.of(shift);

            }
        }

        return Optional.empty();
    }

    public Optional<ShiftModel> findShiftByCurrentTime(LocalDateTime localDateTime, int machine) {

        List<ShiftModel> shiftList = getAllByMachine(machine);

        for (ShiftModel shift : shiftList) {

            boolean hasDay = shift.getDaysOfWeek().contains(localDateTime.getDayOfWeek().getValue());
            final boolean isSaturday = localDateTime.getDayOfWeek() == DayOfWeek.SATURDAY;
            var correct = shiftList.stream().filter(shiftModel -> shiftModel.getDaysOfWeek().contains(0) && localDateTime.getDayOfWeek() == DayOfWeek.SUNDAY || shiftModel.getDaysOfWeek().contains(localDateTime.getDayOfWeek().getValue())).filter(shiftModel -> {

                Duration startTime = shiftModel.getStartTime();
                Duration endTime = shiftModel.getEndTime();

                var start = localDateTime.withHour(startTime.toHoursPart()).withMinute(startTime.toMinutesPart()).withSecond(0).withNano(0);

                var end = localDateTime.withHour(endTime.toHoursPart()).withMinute(endTime.toMinutesPart()).withSecond(59).withNano(0);

                return localDateTime.isAfter(start) && localDateTime.isBefore(end);

            }).findFirst();

            if (hasDay || (isSaturday && correct.isPresent() && shift.getId() == correct.get().getId())) {

                boolean inRange;
                Duration startTime = shift.getStartTime();
                Duration endTime = shift.getEndTime();

                var start = localDateTime.withHour(startTime.toHoursPart()).withMinute(startTime.toMinutesPart()).withSecond(0).withNano(0);

                var end = localDateTime.withHour(endTime.toHoursPart()).withMinute(endTime.toMinutesPart()).withSecond(59);

                // Si el turno pasa al siguiente día
                if (endTime.compareTo(startTime) < 0) {
                    final LocalDateTime other = localDateTime.withHour(shift.getStartTime().toHoursPart()).withMinute(shift.getStartTime().toMinutesPart());
                    var media = localDateTime.compareTo(other);
                    if (media < 0) {
                        start = start.minusDays(1);
                    } else {
                        end = end.plusDays(1);
                    }

                }

                inRange = localDateTime.isAfter(start) && localDateTime.isBefore(end);

                if (inRange) return Optional.of(shift);

            }

        }

        return Optional.empty();
    }
}