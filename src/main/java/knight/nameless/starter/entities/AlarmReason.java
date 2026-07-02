package knight.nameless.starter.entities;

import knight.nameless.starter.entities.enums.StopType;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`AlarmReasons`")
public class AlarmReason {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alarmreason")
    @SequenceGenerator(name = "alarmreason", sequenceName = "`AlarmReasons_Id_seq`", allocationSize = 1)
    private int id;

    @Column(name = "`Codigo`")
    private String codigo;

    @Column(name = "`Description`")
    private String description;

    @Column(name = "`StopType`")
    private StopType stopType;

    @Column(name = "`DateEmailSent`")
    private LocalDateTime dateEmailSent;

    @Column(name = "`EmailSent`")
    private boolean emailSent;

    @ManyToOne
    @JoinColumn(name = "`MachineId`")
    private Machine machine;

    @Column(name = "`Enabled`")
    private boolean enabled;

    @Column(name = "`FailedComponent`")
    private String failedComponent;

    @ManyToOne
    @JoinColumn(name = "`MaintenanceMachineDescriptionId`")
    private MaintenanceMachineDescription maintenanceMachineDescription;
}
