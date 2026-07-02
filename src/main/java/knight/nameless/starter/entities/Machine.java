package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "`Machines`")
public class Machine {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @Column(name = "`Serial`")
    private String serial;

    @Column(name = "`Model`")
    private String model;

    @Column(name = "`HoursRun`")
    private int hoursRun;

    @Column(name = "`ShowInDashboard`")
    private boolean showInDashboard;

    @Column(name = "`Production`")
    private boolean production;

    @Column(name = "`Status`")
    private boolean status;

    @Column(name = "`Timer`")
    private int timer;

    @Column(name = "`SpeedProductChange`")
    private double speedProductChange;

    @Column(name = "`MaxSpeed`")
    private double maxSpeed;
}
