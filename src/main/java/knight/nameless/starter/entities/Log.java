package knight.nameless.starter.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "`Logs`")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {

    @Id
    @Column(name = "`Id`")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logss")
    @SequenceGenerator(name = "logss", sequenceName = "`Logs_Id_seq`", allocationSize = 1)
    private int id;

    @Column(name = "`DateCreated`")
    private LocalDateTime dateCreated;

    @Column(name = "`Value`")
    private float value;

    @Column(name = "`Reference`")
    private String reference;

    public Log(float value, String reference) {
        this.value = value;
        this.reference = "L2--> " + reference;
        this.dateCreated = LocalDateTime.now();
    }
}
