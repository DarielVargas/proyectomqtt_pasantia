package knight.nameless.starter.entities;

import knight.nameless.starter.entities.enums.ItemStatus;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`Inventories`")
public class Inventory {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Invt")
    @SequenceGenerator(name = "Invt", sequenceName = "`Inventories_Id_seq`", allocationSize = 1)
    private int id;

    @Column(name = "`ItemStatus`")
    private ItemStatus itemStatus;

    @ManyToOne
    @JoinColumn(name = "`IngredientId`")
    private Ingredient ingredient;

    @Column(name = "`Cantidad`")
    private float cantidad;

    @Column(name = "`DateCreated`")
    private LocalDateTime dateCreated;

    @Column(name = "`DateUpdated`")
    private LocalDateTime dateUpdated;
}
