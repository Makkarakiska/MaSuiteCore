package fi.matiaspaavilainen.masuitecore.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.UUID;

public class CoreMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener{

    private MaSuiteCore plugin;

    CoreMessageListener(MaSuiteCore p) {
        plugin = p;
    }

    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));

        String subchannel = null;
        try {
            subchannel = in.readUTF();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);

            if (subchannel.equals("MaSuitePlayerLocation")) {
                Player p = Bukkit.getPlayer(UUID.fromString(in.readUTF()));
                if (p == null) {
                    return;
                }
                Location loc = p.getLocation();

                out.writeUTF("MaSuitePlayerLocation");
                out.writeUTF(String.valueOf(p.getUniqueId()));
                out.writeUTF(loc.getWorld().getName());
                out.writeDouble(loc.getX());
                out.writeDouble(loc.getY());
                out.writeDouble(loc.getZ());
                out.writeFloat(loc.getYaw());
                out.writeFloat(loc.getPitch());
                player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
