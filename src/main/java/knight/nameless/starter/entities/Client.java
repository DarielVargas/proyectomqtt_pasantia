package knight.nameless.starter.entities;

import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "`Clients`")
public class Client {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client")
    @SequenceGenerator(name = "client", sequenceName = "`Clients_Id_seq`", allocationSize = 1)
    private int id;

    @Column(name = "`FullName`")
    private String fullName;

    @Column(name = "`CompanyName`")
    private String companyName;
}
