package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`Alarms`")
public class Alarm {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alarms")
    @SequenceGenerator(name = "alarms", sequenceName = "`Alarms_Id_seq`", allocationSize = 1)
    private int id;

    @Column(name = "`Start`")
    public LocalDateTime start;

    @Column(name = "`End`")
    private LocalDateTime end;

    @ManyToOne
    @JoinColumn(name = "`AlarmReasonId`")
    private AlarmReason alarmReason;

    @ManyToOne
    @JoinColumn(name = "`OperatorId`")
    private Accounts operator;

    @ManyToOne
    @JoinColumn(name = "`MachineId`")
    private Machine machine;

    @ManyToOne
    @JoinColumn(name = "`SensorReadingId`")
    private SensorReading sensorReading;

    @Column(name = "`Notes`")
    private String notes;
}
