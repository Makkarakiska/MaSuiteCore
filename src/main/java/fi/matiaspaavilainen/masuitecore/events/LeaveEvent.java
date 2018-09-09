package fi.matiaspaavilainen.masuitecore.events;

import fi.matiaspaavilainen.masuitecore.listeners.MaSuitePlayerGroup;
import fi.matiaspaavilainen.masuitecore.managers.MaSuitePlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LeaveEvent implements Listener {

    @EventHandler
    public void onLeave(PlayerDisconnectEvent e){
        MaSuitePlayer msp = new MaSuitePlayer();
        msp = msp.find(e.getPlayer().getUniqueId());
        msp.setLastLogin(System.currentTimeMillis() / 1000);
        msp.update(msp);
        MaSuitePlayerGroup.groups.remove(e.getPlayer().getUniqueId());
    }

}
