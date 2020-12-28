package dev.masa.masuitecore.bukkit;

import dev.masa.masuitecore.common.objects.Location;
import dev.masa.masuitecore.common.utils.BukkitAdapter;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

@AllArgsConstructor
public class CoreMessageListener implements PluginMessageListener {

    private final MaSuiteCore plugin;

    public void onPluginMessageReceived(String channel, Player sender, byte[] message) {
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
                if (childchannel.equals("ApplyCooldown")) {
                    MaSuiteCore.cooldownService.applyCooldown(in.readUTF(), UUID.fromString(in.readUTF()));
                }
                if (childchannel.equals("PlayerToLocation")) {
                    Player player = Bukkit.getPlayer(in.readUTF());

                    if (player == null) return;

                    Location loc = new Location().deserialize(in.readUTF());

                    org.bukkit.Location bukkitLocation = BukkitAdapter.adapt(loc);
                    if (bukkitLocation.getWorld() == null) {
                        this.plugin.getLogger().log(Level.WARNING, "Could not find world " + loc.getWorld());
                        return;
                    }

                    player.teleport(bukkitLocation);
                }
                if (childchannel.equals("PlayerToPlayer")) {
                    Player player = Bukkit.getPlayer(in.readUTF());
                    Player target = Bukkit.getPlayer(in.readUTF());
                    Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
                        if (player != null && target != null) {
                            this.plugin.getTeleportQueue().add(player.getUniqueId());
                            player.leaveVehicle();

                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                player.teleport(target);
                            }, 1);

                        }
                    }, 5);

                    if (player != null) {
                        Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> this.plugin.getTeleportQueue().remove(player.getUniqueId()), 100);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
