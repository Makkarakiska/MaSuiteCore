package fi.matiaspaavilainen.masuitecore.bukkit.events;

import fi.matiaspaavilainen.masuitecore.core.objects.MaSuitePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LoginEvent implements Listener {

    public LoginEvent() {
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent e) {
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
    }
}
