package knight.nameless.starter.entities;

import knight.nameless.starter.entities.enums.Status;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`PurchaseOrders`")
public class PurchaseOrder {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "po")
    @SequenceGenerator(name = "po", sequenceName = "`PurchaseOrders_Id_seq`", allocationSize = 1)
    private int id;

    @Column(name = "`Status`")
    private Status status;

    @Column(name = "`DateCreated`")
    private LocalDateTime dateCreated;

    @ManyToOne
    @JoinColumn(name = "`ClientId`")
    private Client client;

    @Column(name = "`ExternalOrderNumber`")
    private String externalOrderNumber;
}
