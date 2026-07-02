package knight.nameless.starter.entities;

import knight.nameless.starter.entities.enums.Status;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`ProductionPlans`")
public class ProductionPlan {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prodplan")
    @SequenceGenerator(name = "prodplan", sequenceName = "`ProductionPlans_Id_seq`", allocationSize = 1)
    private int id;

    @Column(name = "`Status`")
    private Status status;

    @Column(name = "`DateCreated`")
    private LocalDateTime dateCreated;

    @Column(name = "`DateUpdated`")
    private LocalDateTime dateUpdated;

    @ManyToOne
    @JoinColumn(name = "`CreatedById`")
    private Accounts createdBy;

    @ManyToOne
    @JoinColumn(name = "`UpdatedById`")
    private Accounts updatedBy;

    @Column(name = "`Name`")
    private String name;

    @Column(name = "`DateRangeStart`")
    private LocalDateTime dateRangeStart;

    @Column(name = "`DateRangeEnd`")
    private LocalDateTime dateRangeEnd;

    @ManyToOne
    @JoinColumn(name = "`MachineId`")
    private Machine machine;
}
