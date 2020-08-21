package dev.masa.masuitecore.bungee.listeners;

import dev.masa.masuitecore.bungee.MaSuiteCore;
import dev.masa.masuitecore.common.models.MaSuitePlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LeaveEvent implements Listener {

    private MaSuiteCore plugin;

    public LeaveEvent(MaSuiteCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerDisconnectEvent event) {
        plugin.getPlayerService().getPlayer(event.getPlayer().getUniqueId(), playerResult -> {
            if (playerResult.isPresent()) {
                MaSuitePlayer player = playerResult.get();
                player.setLastLogin(System.currentTimeMillis() / 1000);
                plugin.getPlayerService().update(player, success -> {
                    if (success) {
                        plugin.getPlayerService().removePlayerFromServers(player);
                    }
                });
            }
        });
    }
}
