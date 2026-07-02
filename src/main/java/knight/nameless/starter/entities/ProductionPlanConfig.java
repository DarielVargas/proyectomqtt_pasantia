package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`ProductionPlanConfigs`")
public class ProductionPlanConfig {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prodplanconf")
    @SequenceGenerator(name = "prodplanconf", sequenceName = "`ProductionPlanConfigs_Id_seq`", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "`ProductionPlanId`")
    private ProductionPlan productionPlan;

    @ManyToOne
    @JoinColumn(name = "`PurchaseOrderDetailId`")
    private PurchaseOrderDetail purchaseOrderDetail;

    @Column(name = "`ProductionPlanNumber`")
    private int productionPlanNumber;

    @Column(name = "`Machine`")
    private String machine;

    @Column(name = "`Quantity`")
    private float quantity;

    @Column(name = "`ProductChangeQty`")
    private int productChangeQty;

    @Column(name = "`DieChangeQty`")
    private int dieChangeQty;

    @Column(name = "`OptimalHoursRequired`")
    private float optimalHoursRequired;

    @Column(name = "`TotalTimeRequired`")
    private float totalTimeRequired;

    @Column(name = "`UseInventory`")
    private boolean useInventory;

    @Column(name = "`AvailableInventory`")
    private float availableInventory;

    @Column(name = "`SequenceOrder`")
    private int sequenceOrder;

    @Column(name = "`ScheduledDate`")
    private LocalDateTime scheduledDate;

    @Column(name = "`TotalRolls`")
    private float totalRolls;

    @Column(name = "`TotalBoxes`")
    private float totalBoxes;

    @Column(name = "`UnitsProduced`")
    private float unitsProduced;

    @Column(name = "`HasIngredientShortfall`")
    private boolean hasIngredientShortfall;

    @Column(name = "`InventoryUsed`")
    private float inventoryUsed;
}
