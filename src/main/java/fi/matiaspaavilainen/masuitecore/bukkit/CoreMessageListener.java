package fi.matiaspaavilainen.masuitecore.bukkit;

import fi.matiaspaavilainen.masuitecore.core.adapters.BukkitAdapter;
import fi.matiaspaavilainen.masuitecore.core.objects.Location;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;
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
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);

            if (subchannel.equals("PlaySound")) {
                Player p = Bukkit.getPlayer(UUID.fromString(in.readUTF()));
                if (p == null) {
                    return;
                }

                Location location = new Location().fromString(in.readUTF());

                String soundString = in.readUTF().toUpperCase();
                if (EnumUtils.isValidEnum(Sound.class, soundString)) {
                    player.playSound(BukkitAdapter.adapt(location), Sound.valueOf(soundString), in.readFloat(), in.readFloat());
                } else {
                    System.out.println("[MaSuite] (" + soundString + " ) is not a valid sound!! ");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
