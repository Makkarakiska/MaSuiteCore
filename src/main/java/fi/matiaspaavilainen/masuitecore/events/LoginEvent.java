package fi.matiaspaavilainen.masuitecore.events;

import fi.matiaspaavilainen.masuitecore.MaSuiteCore;
import fi.matiaspaavilainen.masuitecore.managers.MaSuitePlayer;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class LoginEvent implements Listener {

    private MaSuiteCore plugin;

    public LoginEvent(MaSuiteCore p) {
        plugin = p;
    }

    @EventHandler
    public void onLogin(PostLoginEvent e) {
        MaSuitePlayer msp = new MaSuitePlayer();
        msp.setUsername(e.getPlayer().getName());
        msp.setNickname(null);
        msp.setUUID(e.getPlayer().getUniqueId());
        msp.setIpAddress(e.getPlayer().getAddress().getAddress().getHostAddress());
        msp.setFirstLogin(System.currentTimeMillis() / 1000);
        msp.setLastLogin(System.currentTimeMillis() / 1000);
        msp.insert();
        ProxyServer.getInstance().getScheduler().schedule(plugin, msp::requestLocation, 1, TimeUnit.SECONDS);

    }
}
