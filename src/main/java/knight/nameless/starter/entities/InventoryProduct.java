package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`InventoryProducts`")
public class InventoryProduct {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invprod")
    @SequenceGenerator(name = "invprod", sequenceName = "`InventoryProducts_Id_seq`", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "`ProductId`")
    private Product product;

    @Column(name = "`EntryAmount`")
    private int entryAmount;

    @Column(name = "`OutputAmount`")
    private int outputAmount;

    @Column(name = "`AvailableAmount`")
    private int availableAmount;

    @Column(name = "`DateCreated`")
    private LocalDateTime dateCreated;

    @Column(name = "`DateUpdated`")
    private LocalDateTime dateUpdated;
}
