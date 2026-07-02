package knight.nameless.starter.entities;

import knight.nameless.starter.entities.enums.OrderStatus;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`PurchaseOrderDetails`")
public class PurchaseOrderDetail {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pod")
    @SequenceGenerator(name = "pod", sequenceName = "`PurchaseOrderDetails_Id_seq`", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "`PurchaseOrderId`")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "`ProductId`")
    private Product product;

    @Column(name = "`Quantity`")
    private int quantity;

    @Column(name = "`DueDate`")
    private LocalDateTime dueDate;

    @Column(name = "`OrderStatus`")
    private OrderStatus orderStatus;

    @Column(name = "`ProductionPlanConfigId`")
    private Integer productionPlanConfigId;
}
