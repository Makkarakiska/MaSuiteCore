package fi.matiaspaavilainen.masuitecore.sponge.events;

import fi.matiaspaavilainen.masuitecore.core.objects.MaSuitePlayer;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class LoginEvent {

    @Listener
    public void onJoin(ClientConnectionEvent e) {
        Optional<Player> player = e.getCause().first(Player.class);
        if (player.isPresent()) {
            Player p = player.get();
            MaSuitePlayer msp = new MaSuitePlayer();
            if (msp.find(p.getUniqueId()).getUniqueId() != null) {
                msp = msp.find(p.getUniqueId());
                if (msp.getNickname() != null) {
                    p.getDisplayNameData().displayName().set(Text.of(msp.getNickname()));
                }
            }
            msp.setUsername(p.getName());
            msp.setUniqueId(p.getUniqueId());
            msp.setLastLogin(System.currentTimeMillis() / 1000);
            msp.setFirstLogin(System.currentTimeMillis() / 1000);
            msp.create();
        }

    }
}
