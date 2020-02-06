package dev.masa.masuitecore.core.services;

import dev.masa.masuitecore.bukkit.MaSuiteCore;
import dev.masa.masuitecore.core.utils.BukkitWarmup;
import org.bukkit.Location;
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

    public void applyWarmup(UUID uuid, String type, Consumer<Boolean> callback) {
        int warmupTime = this.warmupTimes.get(type);
        if(warmupTime <= 0) {
            callback.accept(true);
            return;
        }

        warmups.put(uuid, type);
        String message = plugin.config.load(null, "messages.yml").getString("teleportation-started").replace("%time%", String.valueOf(warmupTime));
        plugin.formator.sendMessage(plugin.getServer().getPlayer(uuid), message);

        new BukkitWarmup(warmupTime, plugin) {
            @Override
            public void count(int current) {
                if (current == 0) {
                    if (warmups.containsKey(uuid)) {
                        callback.accept(true);
                        warmups.remove(uuid);
                        return;
                    }
                    callback.accept(false);
                    warmups.remove(uuid);
                }
            }
        }.start();
    }

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
