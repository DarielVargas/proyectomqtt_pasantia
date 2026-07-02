package knight.nameless.starter.entities;

import knight.nameless.starter.entities.enums.Status;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`Productions`")
public class Production {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prod")
    @SequenceGenerator(name = "prod", sequenceName = "`Productions_Id_seq`", allocationSize = 1)
    private int id;

    @Column(name = "`Status`")
    private Status status;

    @Column(name = "`ProductionOrder`")
    private String productionOrder;

    @ManyToOne
    @JoinColumn(name = "`CreatedById`")
    private Accounts createdBy;

    @Column(name = "`Goal`")
    private float goal;

    @Column(name = "`TotalGoodProduct`")
    private int totalGoodProduct;

    @Column(name = "`TotalBadProduct`")
    private int totalBadProduct;

    @ManyToOne
    @JoinColumn(name = "`MachineId`")
    private Machine machine;

    @Column(name = "`CreatedDateTime`")
    private LocalDateTime createdDateTime;

    @Column(name = "`StartedProductionDateTime`")
    private LocalDateTime startedProductionDateTime;

    @Column(name = "`FinishProductionDateTime`")
    private LocalDateTime finishProductionDateTime;
}
