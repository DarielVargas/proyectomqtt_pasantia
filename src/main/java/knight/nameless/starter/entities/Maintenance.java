package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`Maintenances`")
public class Maintenance {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "maint")
    @SequenceGenerator(name = "maint", sequenceName = "`Maintenances_Id_seq`", allocationSize = 1)
    private int id;

    @Column(name = "`Hours`")
    public float hours;

    @Column(name = "`Date`")
    public LocalDateTime date;

    @Column(name = "`Updated`")
    private LocalDateTime updated;

    @Column(name = "`Photos`")
    private String photos;

    @Column(name = "`Notes`")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "`MachineMaintenanceId`")
    public MachineMaintenance machineMaintenance;

    @ManyToOne
    @JoinColumn(name = "`AccountId`")
    private Accounts account;
}
