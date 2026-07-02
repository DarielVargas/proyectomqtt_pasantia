package knight.nameless.starter.entities;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "`SensorTypes`")
public class SensorType {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;
    @Column(name = "`Name`")
    private String name;
    @Column(name = "`Description`")
    private String description;
}
