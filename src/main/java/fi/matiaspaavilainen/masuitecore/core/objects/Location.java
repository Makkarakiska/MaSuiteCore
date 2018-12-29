package fi.matiaspaavilainen.masuitecore.core.objects;

import net.md_5.bungee.api.config.ServerInfo;

public class Location {

    private ServerInfo server;
    private String world;
    private Double x;
    private Double y;
    private Double z;
    private Float yaw;
    private Float pitch;

    /**
     * Empty constructor for BungeeCord Location
     */
    public Location() {

    }

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
     * @return location's server
     */
    public ServerInfo getServer() {
        return server;
    }

    /**
     * @param server location's server
     */
    public void setServer(ServerInfo server) {
        this.server = server;
    }

    /**
     * @return location's world
     */
    public String getWorld() {
        return world;
    }

    /**
     * @param world location's world
     */
    public void setWorld(String world) {
        this.world = world;
    }

    /**
     * @return x-axis of location
     */
    public Double getX() {
        return x;
    }

    /**
     * @param x x-axis of location
     */
    public void setX(Double x) {
        this.x = x;
    }

    /**
     * @return y-axis of location
     */
    public Double getY() {
        return y;
    }

    /**
     * @param y y-axis of location
     */
    public void setY(Double y) {
        this.y = y;
    }

    /**
     * @return z-axis of location
     */
    public Double getZ() {
        return z;
    }

    /**
     * @param z z-axis of location
     */
    public void setZ(Double z) {
        this.z = z;
    }

    /**
     * @return yaw-axis of location
     */
    public Float getYaw() {
        return yaw;
    }

    /**
     * @param yaw yaw-axis of location
     */
    public void setYaw(Float yaw) {
        this.yaw = yaw;
    }

    /**
     * @return pitch of location
     */
    public Float getPitch() {
        return pitch;
    }

    /**
     * @param pitch pitch of location
     */
    public void setPitch(Float pitch) {
        this.pitch = pitch;
    }
}
