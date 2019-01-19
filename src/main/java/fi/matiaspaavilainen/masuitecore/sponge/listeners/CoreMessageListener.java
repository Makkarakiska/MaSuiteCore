package fi.matiaspaavilainen.masuitecore.sponge.listeners;

import org.spongepowered.api.Platform;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.network.ChannelBinding;
import org.spongepowered.api.network.ChannelBuf;
import org.spongepowered.api.network.RawDataListener;
import org.spongepowered.api.network.RemoteConnection;

import java.util.Optional;

public class CoreMessageListener implements RawDataListener {

    private ChannelBinding.RawDataChannel channel;

    public CoreMessageListener(ChannelBinding.RawDataChannel channel) {
        super();
        this.channel = channel;
        System.out.println("Created!");
    }

    @Override
    public void handlePayload(ChannelBuf data, RemoteConnection connection, Platform.Type side) {
        Optional<Player> player = Sponge.getServer().getPlayer(data.getUTF(0));
        if(player.isPresent()){
            Player p = player.get();
            channel.sendTo(p,buffer -> buffer.writeUTF("MaSuiteDebugMessage").writeUTF(p.getName()));
        }
    }
}
