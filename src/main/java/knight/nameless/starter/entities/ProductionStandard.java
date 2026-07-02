package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "`ProductionStandards`")
public class ProductionStandard {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prodstd")
    @SequenceGenerator(name = "prodstd", sequenceName = "`ProductionStandards_Id_seq`", allocationSize = 1)
    private int id;

    @Column(name = "`Name`")
    private String name;

    @ManyToOne
    @JoinColumn(name = "`MachineId`")
    private Machine machine;

    @Column(name = "`BoardGrade`")
    private String boardGrade;

    @Column(name = "`MaxWorkSpeed`")
    private float maxWorkSpeed;

    @Column(name = "`RowPerProduct`")
    private int rowPerProduct;

    @Column(name = "`ProductChangeTimeInHours`")
    private float productChangeTimeInHours;

    @Column(name = "`DieChangeTimeInHours`")
    private float dieChangeTimeInHours;

    @Column(name = "`TotalRollLengthInMeters`")
    private float totalRollLengthInMeters;

    @Column(name = "`BlankLengthInMeters`")
    private float blankLengthInMeters;

    @Column(name = "`ProductionAndWhiteWastePercentage`")
    private float productionAndWhiteWastePercentage;

    @Column(name = "`OthersPercentage`")
    private float othersPercentage;
}
