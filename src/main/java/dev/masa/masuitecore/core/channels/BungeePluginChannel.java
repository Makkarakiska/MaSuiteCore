package dev.masa.masuitecore.core.channels;


import lombok.Data;
import lombok.NoArgsConstructor;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Data
@NoArgsConstructor
public class BungeePluginChannel {

    private Plugin plugin;
    private ServerInfo server;
    private Object[] params;

    /**
     * A constructor for BungeePluginChannel
     *
     * @param plugin plugin to use
     * @param server server to send messages
     * @param params params to send
     */
    public BungeePluginChannel(Plugin plugin, ServerInfo server, Object... params) {
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
            server.sendData("BungeeCord", b.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
