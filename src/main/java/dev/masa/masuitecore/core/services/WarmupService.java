package dev.masa.masuitecore.core.services;

import dev.masa.masuitecore.bukkit.MaSuiteCore;
import dev.masa.masuitecore.core.utils.BukkitWarmup;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.*;
import java.util.function.Consumer;

public class WarmupService implements Listener {

    public HashMap<UUID, String> warmups = new HashMap<>();
    public HashMap<String, Integer> warmupTimes = new HashMap<>();

    private MaSuiteCore plugin;

    public WarmupService(MaSuiteCore plugin) {
        this.plugin = plugin;
    }

    /**
     * Apply warmup for the player
     *
     * @param player           player to add
     * @param bypassPermission permission to bypass warmup
     * @param type             type of the warmup
     * @param callback         callback
     */
    public void applyWarmup(Player player, String bypassPermission, String type, Consumer<Boolean> callback) {
        int warmupTime = this.warmupTimes.get(type);

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
        String message = plugin.config.load(null, "messages.yml").getString("teleportation-started").replace("%time%", String.valueOf(warmupTime));
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
     * Add warmup time to cache
     *
     * @param type type of the warmup
     * @param time time of the warmup
     */
    public void addWarmupTime(String type, int time) {
        warmupTimes.put(type, time);
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
            plugin.formator.sendMessage(event.getPlayer(), plugin.config.load(null, "messages.yml").getString("teleportation-cancelled"));
            warmups.remove(event.getPlayer().getUniqueId());
        }
    }

}
