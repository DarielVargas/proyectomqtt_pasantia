package knight.nameless.starter.entities;

import knight.nameless.starter.entities.enums.Status;
import lombok.Data;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`ProductionDetails`")
public class ProductionDetails {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proddet")
    @SequenceGenerator(name = "proddet", sequenceName = "`ProductionDetails_Id_seq`", allocationSize = 1)
    private int id;

    @Column(name = "`Status`")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "`ProductionId`")
    private Production production;

    @ManyToOne
    @JoinColumn(name = "`OperatorId`")
    private Accounts operator;

    @Column(name = "`GoodProduct`")
    private int goodProduct;

    @Column(name = "`BadProduct`")
    private int badProduct;

    @Column(name = "`BoxNumber`")
    private int boxNumber;

    @Column(name = "`TotalProducts`")
    private int totalProducts;

    @Column(name = "`LabelPrinted`")
    private boolean labelPrinted;

    @ManyToOne
    @JoinColumn(name = "`ShiftId`")
    private Shift shift;

    @Column(name = "`StartShiftTime`", columnDefinition = "interval")
    @JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
    private Duration startShiftTime;

    @Column(name = "`EndShiftTime`", columnDefinition = "interval")
    @JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
    private Duration endShiftTime;

    @Column(name = "`DateCreated`")
    private LocalDateTime dateCreated;

    @Column(name = "`DateUpdated`")
    private LocalDateTime dateUpdated;
}
