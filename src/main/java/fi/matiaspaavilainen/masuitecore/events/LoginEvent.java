package fi.matiaspaavilainen.masuitecore.events;

import fi.matiaspaavilainen.masuitecore.Debugger;
import fi.matiaspaavilainen.masuitecore.MaSuiteCore;
import fi.matiaspaavilainen.masuitecore.config.Configuration;
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

    private Debugger debugger = new Debugger();

    @EventHandler
    public void onLogin(PostLoginEvent e) {
        MaSuitePlayer msp = new MaSuitePlayer();
        if (msp.find(e.getPlayer().getUniqueId()).getUUID() != null) {
            msp = msp.find(e.getPlayer().getUniqueId());
            if (msp.getNickname() != null) {
                e.getPlayer().setDisplayName(msp.getNickname());
            }
        }
        msp.setUsername(e.getPlayer().getName());
        msp.setUUID(e.getPlayer().getUniqueId());
        msp.setIpAddress(e.getPlayer().getAddress().getAddress().getHostAddress());
        msp.setLastLogin(System.currentTimeMillis() / 1000);
        msp.setFirstLogin(System.currentTimeMillis() / 1000);
        msp.insert();
        debugger.sendMessage("[MaSuiteCore] [MaSuitePlayer] saved to database");
        if(new Configuration().load(null, "config.yml").getBoolean("get-group-on-join")){
            if(e.getPlayer() != null){
                ProxyServer.getInstance().getScheduler().schedule(plugin, msp::getGroup, 2, TimeUnit.SECONDS);
            } else {
                ProxyServer.getInstance().getScheduler().schedule(plugin, msp::getGroup, 5, TimeUnit.SECONDS);
            }
        }


    }
}
