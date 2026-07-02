package knight.nameless.starter.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import knight.nameless.starter.entities.enums.Role;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity()
@Table(name = "`Accounts`", schema = "public")
public class Accounts {

    @jakarta.persistence.Id
    @Column(name = "`Id`")
    private int id;
    @Column(name = "`Title`")
    private String title;
    @Column(name = "`FirstName`")
    private String firstName;
    @Column(name = "`LastName`")
    private String lastName;
    @Column(name = "`Email`")
    private String email;
    @Column(name = "`AvatarUrl`")
    private String avatarUrl;
    @Column(name = "`PasswordHash`")
    private String passwordHash;
    @Column(name = "`AcceptTerms`")
    private Boolean acceptTerms;
    @Column(name = "`Role`")
    private Role role;
    @Column(name = "`VerificationToken`")
    private String verificationToken;
    @Column(name = "`Verified`")
    private LocalDateTime verified;

    @Column(name = "`ResetToken`")
    private String resetToken;
    @Column(name = "`ResetTokenExpires`")
    private LocalDateTime resetTokenExpires;
    @Column(name = "`PasswordReset`")
    private LocalDateTime passwordReset;
    @Column(name = "`Created`")

    private LocalDateTime created;
    @Column(name = "`Updated`")

    private LocalDateTime updated;
    @Column(name = "`Enabled`")

    private Boolean enabled;
    @Column(name = "`Pin`")
    private String pin;

}
