package dev.masa.masuitecore.common.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import dev.masa.masuitecore.core.objects.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
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
    @DatabaseField(dataType = DataType.UUID, id = true, columnName = "uuid")
    private UUID uniqueId;

    @DatabaseField
    private String username;
    @DatabaseField
    private String nickname;
    @DatabaseField
    private Long firstLogin;
    @DatabaseField
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
