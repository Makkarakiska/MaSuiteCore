package fi.matiaspaavilainen.masuitecore.bukkit.events;

import fi.matiaspaavilainen.masuitecore.core.managers.MaSuitePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        MaSuitePlayer msp = new MaSuitePlayer();
        msp = msp.find(e.getPlayer().getUniqueId());
        msp.setLastLogin(System.currentTimeMillis() / 1000);
        msp.update();
    }

}
