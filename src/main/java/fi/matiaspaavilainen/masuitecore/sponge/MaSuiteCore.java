package fi.matiaspaavilainen.masuitecore.sponge;

import com.google.inject.Inject;
import fi.matiaspaavilainen.masuitecore.core.configuration.SpongeConfiguration;
import fi.matiaspaavilainen.masuitecore.core.database.ConnectionManager;
import fi.matiaspaavilainen.masuitecore.sponge.events.LeaveEvent;
import fi.matiaspaavilainen.masuitecore.sponge.events.LoginEvent;
import fi.matiaspaavilainen.masuitecore.sponge.listeners.CoreMessageListener;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.slf4j.Logger;
import org.spongepowered.api.Platform;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.network.ChannelBinding;
import org.spongepowered.api.network.ChannelRegistrar;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Plugin(id = "masuitecore", name = "MaSuiteCore", version = "1.5")
public class MaSuiteCore {

    public static boolean bungee = true;
    public SpongeConfiguration sc = new SpongeConfiguration();
    private ConnectionManager cm = new ConnectionManager();


    private ChannelBinding.RawDataChannel channel;
    @Inject
    @ConfigDir(sharedRoot = false)
    private File config;


    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("Successfully running MaSuiteCore!");
        detectBungee();
        registerListeners();
    }

    @Listener
    public void onPreInit(GamePreInitializationEvent e) {
        logger.info("Loading config file...");
        sc.setup(config);
        sc.create(null, "config.conf");
        sc.getConfig().getNode("database").setComment("Database settings");
        sc.getConfig().getNode("database", "name").setValue("minecraft");
        sc.getConfig().getNode("database", "address").setValue("localhost");
        sc.getConfig().getNode("database", "port").setValue(3306);
        sc.getConfig().getNode("database", "username").setValue("minecraft");
        sc.getConfig().getNode("database", "password").setValue("minecraft");
        sc.getConfig().getNode("database", "table-prefix").setValue("masuite_");
        sc.getConfig().getNode("debug").setValue(false).setComment("Set true for debug messages");
        sc.save();
    }

    @Listener
    public void onClose(GameStoppingServerEvent e) {
        if (!bungee) {
            cm.close();
        }
        Sponge.getChannelRegistrar().unbindChannel(channel);
    }

    @Listener
    public void onJoin(ClientConnectionEvent e) {
        Optional<Player> player = e.getCause().first(Player.class);
        if (player.isPresent()) {
            Player p = player.get();
            Task task = Task.builder()
                    .execute(() -> {
                        channel.sendTo(p, buffer -> buffer.writeUTF("MaSuiteDebugMessage").writeUTF(p.getName()));
                        System.out.println("Send");
                    })
                    .async().delay(500, TimeUnit.MILLISECONDS).submit(this);
        }
    }


    private void registerListeners() {
        if (!bungee) {
            Sponge.getEventManager().registerListeners(this, new LoginEvent());
            Sponge.getEventManager().registerListeners(this, new LeaveEvent());
        } else {
            ChannelBinding.RawDataChannel channel = Sponge.getGame().getChannelRegistrar().createRawChannel(this, "BungeeCord");
            channel.addListener(Platform.Type.SERVER, new CoreMessageListener(channel));
            this.channel = channel;
        }
    }

    private void detectBungee() {
        // TODO: Add configs
        bungee = true;
        if (!bungee) {
            setupNoBungee();
        }
    }

    private void setupNoBungee() {
        // TODO: Add configs
        CommentedConfigurationNode dbInfo = sc.getConfig();
        cm = new ConnectionManager();
        cm.connect();
        cm.getDatabase().createTable("players", "(id INT(10) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(36) UNIQUE NOT NULL, username VARCHAR(16) NOT NULL, nickname VARCHAR(16) NULL, firstLogin BIGINT(15) NOT NULL, lastLogin BIGINT(16) NOT NULL);");
    }

}
