package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`ShiftDays`")
public class ShiftDay {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shiftday")
    @SequenceGenerator(name = "shiftday", sequenceName = "`ShiftDays_Id_seq`", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "`MachineId`")
    private Machine machine;

    @Column(name = "`Date`")
    private LocalDateTime date;

    @Column(name = "`ShiftCount`")
    private int shiftCount;

    @Column(name = "`DateCreated`")
    private LocalDateTime dateCreated;

    @Column(name = "`DateUpdated`")
    private LocalDateTime dateUpdated;
}
