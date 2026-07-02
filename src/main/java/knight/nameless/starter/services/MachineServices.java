package knight.nameless.starter.services;

import knight.nameless.starter.entities.Machine;
import knight.nameless.starter.repositories.MachineRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MachineServices {

    private final MachineRepository machineRepository;

    public MachineServices(MachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    public void save(Machine machine) {
        machineRepository.save(machine);
    }

    public Optional<Machine> getTubeMillMachineL2() {
        return machineRepository.findById(1);
    }

    public Optional<Machine> findById(int id) {
        return machineRepository.findById(id);
    }
}
