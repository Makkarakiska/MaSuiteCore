package dev.masa.masuitecore.common.objects;

import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Location {

    @DatabaseField
    private String server;
    @DatabaseField
    private String world;
    @DatabaseField
    private Double x;
    @DatabaseField
    private Double y;
    @DatabaseField
    private Double z;
    @DatabaseField
    private Float yaw = 0.0F;
    @DatabaseField
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
    public Location(String server, String world, Double x, Double y, Double z, Float yaw, Float pitch) {
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
    public Location(String server, String world, Double x, Double y, Double z) {
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
     * @deprecated
     */
    public String toString() {
        return this.world + ":" + this.x + ":" + this.y + ":" + this.z + ":" + this.yaw + ":" + this.pitch;

    }

    public String serialize() {
        return new Gson().toJson(this);
    }

    public Location deserialize(String json) {
        return new Gson().fromJson(json, Location.class);
    }

    /**
     * Convert string to {@link Location}
     *
     * @param string build from {@link #toString()}
     * @return {@link Location} built from {@link #toString()}
     * @deprecated
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
