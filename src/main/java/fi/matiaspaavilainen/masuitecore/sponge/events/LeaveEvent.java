package fi.matiaspaavilainen.masuitecore.sponge.events;

import fi.matiaspaavilainen.masuitecore.core.objects.MaSuitePlayer;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.util.Optional;

public class LeaveEvent {

    @Listener
    public void onLeave(ClientConnectionEvent.Disconnect e) {
        Optional<Player> player = e.getCause().first(Player.class);
        if (player.isPresent()) {
            MaSuitePlayer msp = new MaSuitePlayer();
            msp = msp.find(player.get().getUniqueId());
            msp.setLastLogin(System.currentTimeMillis() / 1000);
            msp.update();
        }
    }
}
