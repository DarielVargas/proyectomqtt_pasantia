package knight.nameless.starter.entities;

import knight.nameless.starter.entities.enums.ProductSize;
import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`Products`")
public class Product {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product")
    @SequenceGenerator(name = "product", sequenceName = "`Products_Id_seq`", allocationSize = 1)
    private int id;

    @Column(name = "`Code`")
    private String code;

    @Column(name = "`Description`")
    private String description;

    @Column(name = "`Format`")
    private String format;

    @Column(name = "`SizeLabel`")
    private ProductSize sizeLabel;

    @Column(name = "`UnitsPerCase`")
    private Integer unitsPerCase;

    @ManyToOne
    @JoinColumn(name = "`ProductionStandardId`")
    private ProductionStandard productionStandard;

    @Column(name = "`DateCreated`")
    private LocalDateTime dateCreated;

    @Column(name = "`DateUpdated`")
    private LocalDateTime dateUpdated;
}
