package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "`Sensors`")
public class Sensor {

    @Id
    @Column(name = "`Id`")
    private int id;

    @Column(name = "`Name`")
    private String name;

    @Column(name = "`Description`")
    private String description;

    @Column(name = "`Color`")
    private String color;

    @Column(name = "`Max`")
    private float max;

    @Column(name = "`Min`")
    private float min;

    @Column(name = "`Active`")
    private boolean active;

    @Column(name = "`ShowInReport`")
    private boolean showInReport;

    @Column(name = "`IsBinary`")
    private boolean isBinary;

    @ManyToOne
    @JoinColumn(name = "`MachineId`")
    private Machine machine;

    @ManyToOne
    @JoinColumn(name = "`SensorTypeId`")
    private SensorType sensorType;
}
