package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`MaintenanceMachineDescriptions`")
public class MaintenanceMachineDescription {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mmd")
    @SequenceGenerator(name = "mmd", sequenceName = "`MaintenanceMachineDescriptions_Id_seq`", allocationSize = 1)
    private int id;

    @Column(name = "`Name`")
    private String name;

    @Column(name = "`Description`")
    private String description;

    @Column(name = "`DateCreated`")
    private LocalDateTime dateCreated;

    @Column(name = "`DateUpdated`")
    private LocalDateTime dateUpdated;
}
