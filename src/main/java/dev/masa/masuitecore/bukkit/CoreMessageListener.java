package dev.masa.masuitecore.bukkit;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

public class CoreMessageListener implements PluginMessageListener {

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
            if (subchannel.equals("MaSuiteCore")) {
                String childchannel = in.readUTF();
                if (childchannel.equals("AddPlayer")) {
                    MaSuiteCore.onlinePlayers.add(in.readUTF());
                }
                if (childchannel.equals("RemovePlayer")) {
                    MaSuiteCore.onlinePlayers.remove(in.readUTF());
                }
                if(childchannel.equals("ApplyCooldown")) {
                    plugin.cooldownService.applyCooldown(in.readUTF(), UUID.fromString(in.readUTF()));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
