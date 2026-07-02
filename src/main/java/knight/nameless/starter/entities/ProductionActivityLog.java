package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`ProductionActivityLogs`")
public class ProductionActivityLog {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prodactlog")
    @SequenceGenerator(name = "prodactlog", sequenceName = "`ProductionActivityLogs_Id_seq`", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "`ProductionId`")
    private Production production;

    @ManyToOne
    @JoinColumn(name = "`OperatorId`")
    private Accounts operator;

    @ManyToOne
    @JoinColumn(name = "`ProductId`")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "`ShiftId`")
    private Shift shift;

    @Column(name = "`DateStarted`")
    private LocalDateTime dateStarted;

    @Column(name = "`DateFinished`")
    private LocalDateTime dateFinished;

    @Column(name = "`Length`")
    private float length;

    @Column(name = "`StartShiftTime`", columnDefinition = "interval")
    @JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
    private Duration startShiftTime;

    @Column(name = "`EndShiftTime`", columnDefinition = "interval")
    @JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
    private Duration endShiftTime;
}
