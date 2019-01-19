package fi.matiaspaavilainen.masuitecore.core.channels;


import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeePluginChannel {

    private Plugin plugin;
    private ServerInfo server;
    private Object[] params;

    /**
     * An empty constructor for PluginChannel
     */
    public BungeePluginChannel() {
    }

    /**
     * A constructor for PluginChannel
     *
     * @param plugin plugin to use
     * @param server server to send messages
     * @param params params to send
     */
    public BungeePluginChannel(Plugin plugin, ServerInfo server, Object[] params) {
        this.plugin = plugin;
        this.server = server;
        this.params = params;
    }

    /**
     * Send given data to player's server
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
            this.plugin.getProxy().getScheduler().runAsync(this.plugin, () -> server.sendData("BungeeCord", b.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return plugin to be used
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * @param plugin plugin to be used
     */
    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * @return get the server to send messages
     */
    public ServerInfo getServer() {
        return server;
    }

    /**
     * @param server get the player to send messages
     */
    public void setServer(ServerInfo server) {
        this.server = server;
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
