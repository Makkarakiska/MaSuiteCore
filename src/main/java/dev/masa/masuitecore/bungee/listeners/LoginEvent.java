package dev.masa.masuitecore.bungee.listeners;

import dev.masa.masuitecore.bungee.MaSuiteCore;
import dev.masa.masuitecore.core.channels.BungeePluginChannel;
import dev.masa.masuitecore.core.models.MaSuitePlayer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LoginEvent implements Listener {

    private MaSuiteCore plugin;

    public LoginEvent(MaSuiteCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(PostLoginEvent e) {
        plugin.getProxy().getScheduler().runAsync(plugin, () -> {
            MaSuitePlayer msp = plugin.playerService.getPlayer(e.getPlayer().getUniqueId());
            if (msp != null) {
                if (msp.getNickname() != null) {
                    e.getPlayer().setDisplayName(msp.getNickname());
                }
            }

            if (msp == null) {
                msp = new MaSuitePlayer();
                msp.setUsername(e.getPlayer().getName());
                msp.setUniqueId(e.getPlayer().getUniqueId());
                msp.setLastLogin(System.currentTimeMillis() / 1000);
                msp.setFirstLogin(System.currentTimeMillis() / 1000);
                plugin.playerService.createPlayer(msp);
            }


            if (plugin.config.load(null, "config.yml").getBoolean("use-tab-completer")) {
                plugin.getProxy().getScheduler().schedule(plugin, () -> {
                    for (Map.Entry<String, ServerInfo> entry : plugin.getProxy().getServers().entrySet()) {
                        ServerInfo serverInfo = entry.getValue();
                        serverInfo.ping((result, error) -> {
                            if (error == null) {
                                new BungeePluginChannel(plugin, serverInfo,
                                        "MaSuiteCore",
                                        "AddPlayer",
                                        e.getPlayer().getName()
                                ).send();
                            }
                        });
                    }
                }, 1, TimeUnit.SECONDS);
            }
        });
    }
}
