package dev.masa.masuitecore.bukkit.services;

import dev.masa.masuitecore.bukkit.MaSuiteCore;
import dev.masa.masuitecore.common.services.AbstractWarmupService;
import dev.masa.masuitecore.bukkit.utils.BukkitWarmup;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.function.Consumer;

public class WarmupService extends AbstractWarmupService<MaSuiteCore, Player> implements Listener {

    public WarmupService(MaSuiteCore plugin) {
        super(plugin);
    }

    public void applyWarmup(Player player, String bypassPermission, String type, Consumer<Boolean> callback) {
        int warmupTime = 0;

        if (this.warmupTimes.get(type) != null) {
            warmupTime = this.warmupTimes.get(type);
        }

        // If warmup time is 0 or lower
        if (warmupTime <= 0) {
            callback.accept(true);
            return;
        }

        // If player has bypass permission
        if (player.hasPermission(bypassPermission)) {
            callback.accept(true);
            return;
        }

        // Add player to warmups list
        warmups.put(player.getUniqueId(), type);
        // Send teleportation message
        String message = plugin.getMessages().getTeleportationStarted().replace("%time%", String.valueOf(warmupTime));
        plugin.formator.sendMessage(player, message);

        new BukkitWarmup(warmupTime, plugin) {
            @Override
            public void count(int current) {
                if (current == 0) {
                    if (warmups.containsKey(player.getUniqueId())) {
                        callback.accept(true);
                        warmups.remove(player.getUniqueId());
                        return;
                    }
                    callback.accept(false);
                    warmups.remove(player.getUniqueId());
                }
            }
        }.start();
    }

    /**
     * Listen player moving and if player has ongoing warmup then cancel the action
     *
     * @param event PlayerMoveEvent
     */
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        String warmupType = warmups.get(event.getPlayer().getUniqueId());
        if (warmupType == null) {
            return;
        }
        if (warmupTimes.get(warmupType) <= 0) {
            return;
        }

        Location movedFrom = event.getFrom();
        Location movedTo = event.getTo();

        // Moving
        if (movedFrom.getBlockX() != movedTo.getBlockX() || movedFrom.getBlockY() != movedTo.getBlockY() || movedFrom.getBlockZ() != movedTo.getBlockZ()) {
            plugin.formator.sendMessage(event.getPlayer(), plugin.getMessages().getTeleportationCancelled());
            warmups.remove(event.getPlayer().getUniqueId());
        }
    }

}
