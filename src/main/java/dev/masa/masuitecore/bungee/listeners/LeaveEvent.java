package dev.masa.masuitecore.bungee.listeners;

import dev.masa.masuitecore.bungee.MaSuiteCore;
import dev.masa.masuitecore.core.channels.BungeePluginChannel;
import dev.masa.masuitecore.core.models.MaSuitePlayer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Map;

public class LeaveEvent implements Listener {

    private MaSuiteCore plugin;

    public LeaveEvent(MaSuiteCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerDisconnectEvent e) {
        plugin.getProxy().getScheduler().runAsync(plugin, () -> {
            MaSuitePlayer msp = plugin.playerService.getPlayer(e.getPlayer().getUniqueId());
            msp.setLastLogin(System.currentTimeMillis() / 1000);
            plugin.playerService.updatePlayer(msp);

            if (plugin.config.load(null, "config.yml").getBoolean("use-tab-completer")) {
                for (Map.Entry<String, ServerInfo> entry : plugin.getProxy().getServers().entrySet()) {
                    ServerInfo serverInfo = entry.getValue();
                    serverInfo.ping((result, error) -> {
                        if (error == null) {
                            new BungeePluginChannel(plugin, serverInfo,
                                    "MaSuiteCore",
                                    "RemovePlayer",
                                    e.getPlayer().getName()
                            ).send();
                        }
                    });
                }
            }
        });

    }

}
