package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`Ingredients`")
public class Ingredient {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient")
    @SequenceGenerator(name = "ingredient", sequenceName = "`Ingredients_Id_seq`", allocationSize = 1)
    private int id;

    @Column(name = "`Code`")
    private String code;

    @Column(name = "`ComponentDescription`")
    private String componentDescription;

    @Column(name = "`DateCreated`")
    private LocalDateTime dateCreated;

    @Column(name = "`DateUpdated`")
    private LocalDateTime dateUpdated;
}
