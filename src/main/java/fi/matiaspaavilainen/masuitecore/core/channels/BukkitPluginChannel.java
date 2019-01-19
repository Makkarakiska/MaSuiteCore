package fi.matiaspaavilainen.masuitecore.core.channels;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BukkitPluginChannel {
    private JavaPlugin plugin;
    private Player player;
    private Object[] params;

    /**
     * An empty constructor for BukkitPluginChannel
     */
    public BukkitPluginChannel() {
    }

    /**
     * A constructor for BukkitPluginChannel
     *
     * @param plugin plugin to use
     * @param player player to use send messages
     * @param params params to send
     */
    public BukkitPluginChannel(JavaPlugin plugin, Player player, Object[] params) {
        this.plugin = plugin;
        this.player = player;
        this.params = params;
    }

    /**
     * Send given data to BungeeCord
     */
    public void send() {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream();
             DataOutputStream out = new DataOutputStream(b)) {
            for (Object param : params) {
                if (param instanceof String) {
                    out.writeUTF((String) param);
                } else if (param instanceof Integer) {
                    out.writeInt((int) param);
                } else if (param instanceof Double) {
                    out.writeDouble((double) param);
                } else if (param instanceof Float) {
                    out.writeFloat((float) param);
                } else if (param instanceof Boolean) {
                    out.writeBoolean((boolean) param);
                } else if (param instanceof Short) {
                    out.writeShort((short) param);
                } else if (param instanceof Long) {
                    out.writeLong((long) param);
                } else if (param instanceof Byte) {
                    out.writeByte((byte) param);
                } else if (param instanceof Character) {
                    out.writeChar((char) param);
                }
            }
            this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> this.player.sendPluginMessage(this.plugin, "BungeeCord", b.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return plugin to be used
     */
    public JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     * @param plugin plugin to be used
     */
    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * @return get the player to send messages
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @param player get the player to send messages
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return params to send
     */
    public Object[] getParams() {
        return params;
    }

    /**
     * @param params params to send
     */
    public void setParams(Object[] params) {
        this.params = params;
    }
}
