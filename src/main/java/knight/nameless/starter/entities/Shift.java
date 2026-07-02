package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "`Shifts`")
public class Shift {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @Column(name = "`StartTime`", columnDefinition = "interval")
    @JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
    private Duration startTime;

    @Column(name = "`EndTime`", columnDefinition = "interval")
    @JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
    private Duration endTime;

    @Column(name = "`DefaultStartTime`", columnDefinition = "interval")
    @JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
    private Duration defaultStartTime;

    @Column(name = "`DefaultEndTime`", columnDefinition = "interval")
    @JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
    private Duration defaultEndTime;

    @Column(name = "`Description`")
    private String description;

    @Column(name = "`Number`")
    private int number;

    @Column(name = "`DateCreated`")
    private LocalDateTime dateCreated;

    @Column(name = "`DateUpdated`")
    private LocalDateTime dateUpdated;

    @ManyToOne
    @JoinColumn(name = "`MachineId`")
    private Machine machine;

    @Column(name = "`DaysOfWeek`", columnDefinition = "integer[]")
    private List<Integer> daysOfWeek;

    @Column(name = "`Enabled`")
    private boolean enabled;
}
