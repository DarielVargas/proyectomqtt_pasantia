package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "`SensorDefaults`")
public class SensorDefault {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensordef")
    @SequenceGenerator(name = "sensordef", sequenceName = "`SensorDefaults_Id_seq`", allocationSize = 1)
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

    @ManyToOne
    @JoinColumn(name = "`SensorTypeId`")
    private SensorType sensorType;
}
