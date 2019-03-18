package fi.matiaspaavilainen.masuitecore.bungee.events;

import fi.matiaspaavilainen.masuitecore.bungee.MaSuiteCore;
import fi.matiaspaavilainen.masuitecore.core.channels.BungeePluginChannel;
import fi.matiaspaavilainen.masuitecore.core.objects.MaSuitePlayer;
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
        MaSuitePlayer msp = new MaSuitePlayer();
        msp = msp.find(e.getPlayer().getUniqueId());
        msp.setLastLogin(System.currentTimeMillis() / 1000);
        msp.update();

        if (plugin.config.load(null, "config.yml").getBoolean("use-tab-completer")) {
            for (Map.Entry<String, ServerInfo> entry : plugin.getProxy().getServers().entrySet()) {
                ServerInfo serverInfo = entry.getValue();
                serverInfo.ping((result, error) -> {
                    if (error == null) {
                        new BungeePluginChannel(plugin, serverInfo, new Object[]{
                                "MaSuiteCore",
                                "RemovePlayer",
                                e.getPlayer().getName()
                        }).send();
                    }
                });
            }
        }
    }

}
