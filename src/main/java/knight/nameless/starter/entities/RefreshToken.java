package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`RefreshToken`")
public class RefreshToken {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rt")
    @SequenceGenerator(name = "rt", sequenceName = "`RefreshToken_Id_seq`", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "`AccountId`")
    private Accounts account;

    @Column(name = "`Token`")
    private String token;

    @Column(name = "`Expires`")
    private LocalDateTime expires;

    @Column(name = "`Created`")
    private LocalDateTime created;

    @Column(name = "`CreatedByIp`")
    private String createdByIp;

    @Column(name = "`Revoked`")
    private LocalDateTime revoked;

    @Column(name = "`RevokedByIp`")
    private String revokedByIp;

    @Column(name = "`ReplacedByToken`")
    private String replacedByToken;

    @Column(name = "`ReasonRevoked`")
    private String reasonRevoked;
}
