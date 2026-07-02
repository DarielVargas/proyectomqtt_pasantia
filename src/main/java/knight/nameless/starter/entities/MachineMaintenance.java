package knight.nameless.starter.entities;

import knight.nameless.starter.entities.enums.Unit;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "`MachineMaintenances`")
public class MachineMaintenance {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @ManyToOne
    @JoinColumn(name = "`MachineId`")
    private Machine machine;

    @Column(name = "`Amount`")
    private double amount;

    @Column(name = "`Unit`")
    private Unit unit;

    @Column(name = "`Procedure`")
    private String procedure;

    @Column(name = "`Reference`")
    private String reference;

    @ManyToOne
    @JoinColumn(name = "`MaintenanceMachineDescriptionId`")
    private MaintenanceMachineDescription maintenanceMachineDescription;

    @Column(name = "`WhenNeeded`")
    private boolean whenNeeded;
}
