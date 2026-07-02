package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`SensorReadings`")
public class SensorReading {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sr")
    @SequenceGenerator(name = "sr", sequenceName = "`SensorReadings_Id_seq`", allocationSize = 1)
    private int id;

    @Column(name = "`Date`")
    private LocalDateTime date;

    @Column(name = "`Value`")
    private float value;

    @ManyToOne
    @JoinColumn(name = "`SensorId`")
    private Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "`ProductionId`")
    private Production production;

    @ManyToOne
    @JoinColumn(name = "`OperatorId`")
    private Accounts operator;

    @ManyToOne
    @JoinColumn(name = "`ShiftId`")
    private Shift shift;
}
