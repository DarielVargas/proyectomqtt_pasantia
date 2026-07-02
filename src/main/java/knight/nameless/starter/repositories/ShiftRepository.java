package knight.nameless.starter.repositories;

import knight.nameless.starter.entities.Machine;
import knight.nameless.starter.entities.Shift;
import knight.nameless.starter.models.shift.ShiftModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    List<ShiftModel> findAllByMachine_IdAndEnabledOrderByStartTime(int machineId, boolean enabled);

    List<ShiftModel> findAllByMachineAndEnabledOrderByStartTime(Machine machine, boolean enabled);
}
