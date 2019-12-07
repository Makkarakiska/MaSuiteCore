package fi.matiaspaavilainen.masuitecore.core.objects;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.md_5.bungee.api.config.ServerInfo;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

@NoArgsConstructor
@Embeddable
@Data
@Access(AccessType.FIELD)
public class Location {


    @Transient private ServerInfo server;

    private String world;
    private Double x;
    private Double y;
    private Double z;
    private Float yaw = 0.0F;
    private Float pitch = 0.0F;

    /**
     * Minecraft location for BungeeCord
     *
     * @param server location's server
     * @param world  location's world
     * @param x      x-axis of location
     * @param y      y-axis of location
     * @param z      z-axis of location
     * @param yaw    yaw-axis of location
     * @param pitch  pitch of location
     */
    public Location(ServerInfo server, String world, Double x, Double y, Double z, Float yaw, Float pitch) {
        this.server = server;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * Minecraft location for BungeeCord
     *
     * @param server location's server
     * @param world  location's world
     * @param x      x-axis of location
     * @param y      y-axis of location
     * @param z      z-axis of location
     */
    public Location(ServerInfo server, String world, Double x, Double y, Double z) {
        this.server = server;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Minecraft location for BungeeCord
     *
     * @param world location's world
     * @param x     x-axis of location
     * @param y     y-axis of location
     * @param z     z-axis of location
     * @param yaw   yaw-axis of location
     * @param pitch pitch of location
     */
    public Location(String world, Double x, Double y, Double z, Float yaw, Float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    /**
     * Minecraft location for BungeeCord
     *
     * @param world location's world
     * @param x     x-axis of location
     * @param y     y-axis of location
     * @param z     z-axis of location
     */
    public Location(String world, Double x, Double y, Double z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Convert {@link Location} to string
     */
    public String toString() {
        return this.world + ":" + this.x + ":" + this.y + ":" + this.z + ":" + this.yaw + ":" + this.pitch;

    }

    /**
     * Convert string to {@link Location}
     *
     * @param string build from {@link #toString()}
     * @return {@link Location} built from {@link #toString()}
     */
    public Location fromString(String string) {
        String[] loc = string.split(":");
        return new Location(loc[0],
                Double.parseDouble(loc[1]),
                Double.parseDouble(loc[2]),
                Double.parseDouble(loc[3]),
                Float.parseFloat(loc[4]),
                Float.parseFloat(loc[5]));
    }
}
