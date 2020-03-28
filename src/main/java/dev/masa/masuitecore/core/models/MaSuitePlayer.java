package dev.masa.masuitecore.core.models;

import dev.masa.masuitecore.core.objects.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "masuite_players")
@NamedQuery(
        name = "findPlayerByName",
        query = "SELECT p FROM MaSuitePlayer p WHERE p.username = :username"
)
public class MaSuitePlayer {

    @Id
    @Column(name = "uuid")
    @Type(type = "uuid-char")
    private UUID uniqueId;

    private String username;
    private String nickname;

    private Long firstLogin;
    private Long lastLogin;

    @Transient
    private Location location;

    @Transient
    public Location getLocation() {
        return this.location;
    }

    @Transient
    public Location setLocation(Location loc) {
        return this.location = loc;
    }
}
