package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "`ProductionPlanIngredients`")
public class ProductionPlanIngredient {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prodplaningred")
    @SequenceGenerator(name = "prodplaningred", sequenceName = "`ProductionPlanIngredients_Id_seq`", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "`ProductionPlanId`")
    private ProductionPlan productionPlan;

    @ManyToOne
    @JoinColumn(name = "`IngredientId`")
    private Ingredient ingredient;

    @Column(name = "`Code`")
    private String code;

    @Column(name = "`Description`")
    private String description;

    @Column(name = "`Required`")
    private float required;

    @Column(name = "`Available`")
    private float available;

    @Column(name = "`Shortfall`")
    private float shortfall;

    @Column(name = "`Units`")
    private String units;
}
