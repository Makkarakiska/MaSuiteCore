package fi.matiaspaavilainen.masuitecore.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "masuite_players")
public class MaSuitePlayer {

    @Id
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    private UUID uniqueId;

    private String username;
    private String nickname;

    private Long firstLogin;
    private Long lastLogin;
}
