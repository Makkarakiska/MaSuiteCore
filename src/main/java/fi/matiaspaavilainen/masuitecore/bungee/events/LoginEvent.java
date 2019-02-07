package fi.matiaspaavilainen.masuitecore.bungee.events;

import fi.matiaspaavilainen.masuitecore.bungee.MaSuiteCore;
import fi.matiaspaavilainen.masuitecore.core.channels.BungeePluginChannel;
import fi.matiaspaavilainen.masuitecore.core.objects.MaSuitePlayer;
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
        MaSuitePlayer msp = new MaSuitePlayer();
        if (msp.find(e.getPlayer().getUniqueId()).getUniqueId() != null) {
            msp = msp.find(e.getPlayer().getUniqueId());
            if (msp.getNickname() != null) {
                e.getPlayer().setDisplayName(msp.getNickname());
            }
        }
        msp.setUsername(e.getPlayer().getName());
        msp.setUniqueId(e.getPlayer().getUniqueId());
        msp.setLastLogin(System.currentTimeMillis() / 1000);
        msp.setFirstLogin(System.currentTimeMillis() / 1000);
        msp.create();

        plugin.getProxy().getScheduler().schedule(plugin, () -> {
            for (Map.Entry<String, ServerInfo> entry : plugin.getProxy().getServers().entrySet()) {
                ServerInfo serverInfo = entry.getValue();
                serverInfo.ping((result, error) -> {
                    if (error == null) {
                        new BungeePluginChannel(plugin, serverInfo, new Object[]{
                                "MaSuiteCore",
                                "AddPlayer",
                                e.getPlayer().getName()
                        }).send();
                    }
                });
            }
        }, 1, TimeUnit.SECONDS);
    }
}
