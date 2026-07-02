package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`LoginActivityLogs`")
public class LoginActivityLog {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actlog")
    @SequenceGenerator(name = "actlog", sequenceName = "`LoginActivityLogs_Id_seq`", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "`AccountId`")
    private Accounts account;

    @Column(name = "`LoginTime`")
    private LocalDateTime loginTime;

    @Column(name = "`LogoutTime`")
    private LocalDateTime logoutTime;

    @Column(name = "`Note`")
    private String note;

    @ManyToOne
    @JoinColumn(name = "`MachineId`")
    private Machine machine;
}
