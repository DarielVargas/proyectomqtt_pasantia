package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`ProductIngredients`")
public class ProductIngredient {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prodingrnd")
    @SequenceGenerator(name = "prodingrnd", sequenceName = "`ProductIngredients_Id_seq`", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "`ProductId`")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "`IngredientId`")
    private Ingredient ingredient;

    @Column(name = "`Quantity`")
    private BigDecimal quantity;

    @Column(name = "`Units`")
    private String units;

    @Column(name = "`DateCreated`")
    private LocalDateTime dateCreated;

    @Column(name = "`DateUpdated`")
    private LocalDateTime dateUpdated;
}
