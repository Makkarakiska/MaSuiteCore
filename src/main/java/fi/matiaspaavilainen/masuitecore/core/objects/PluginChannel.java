package fi.matiaspaavilainen.masuitecore.core.objects;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PluginChannel {

    private JavaPlugin plugin;
    private Player player;
    private Object[] params;

    /**
     * An empty constructor for PluginChannel
     */
    public PluginChannel() {
    }

    /**
     * A constructor for PluginChannel
     *
     * @param plugin plugin to use
     * @param player player to use send messages
     * @param params params to send
     */
    public PluginChannel(JavaPlugin plugin, Player player, Object[] params) {
        this.plugin = plugin;
        this.player = player;
        this.params = params;
    }

    public void send() {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
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
