package dev.masa.masuitecore.common.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import dev.masa.masuitecore.common.objects.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Id()
    @DatabaseField(dataType = DataType.UUID, id = true, columnName = "uuid", unique = true)
    private UUID uniqueId;

    @DatabaseField(width = 100)
    private String username;
    @DatabaseField(width = 100)
    private String nickname;
    @DatabaseField
    private Long firstLogin;
    @DatabaseField
    private Long lastLogin;

    @Transient
    private Location location;
}
