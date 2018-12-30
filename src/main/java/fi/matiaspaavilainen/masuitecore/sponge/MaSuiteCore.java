package fi.matiaspaavilainen.masuitecore.sponge;

import com.google.inject.Inject;
import fi.matiaspaavilainen.masuitecore.core.configuration.SpongeConfiguration;
import fi.matiaspaavilainen.masuitecore.core.database.ConnectionManager;
import fi.matiaspaavilainen.masuitecore.sponge.events.LeaveEvent;
import fi.matiaspaavilainen.masuitecore.sponge.events.LoginEvent;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.nio.file.Path;
import java.nio.file.Paths;

@Plugin(id = "masuitecore", name = "MaSuiteCore", version = "1.5", description = "")
public class MaSuiteCore {


    public static SpongeConfiguration sc = null;
    public static boolean bungee = true;
    private ConnectionManager cm = null;
    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;

    private Path configFile = Paths.get(configDir + "/config.yml");


    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("Successfully running MaSuiteCore!");
        detectBungee();
        registerListeners();
        setupNoBungee();
    }

    @Listener
    public void onPreInit(GamePreInitializationEvent e) {
        logger.info("Loading config file...");

    }

    @Listener
    public void onClose(GameStoppingServerEvent e) {
        cm.close();
    }

    private void registerListeners() {
        if (!bungee) {
            Sponge.getEventManager().registerListeners(this, new LoginEvent());
            Sponge.getEventManager().registerListeners(this, new LeaveEvent());
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
        String[] dbInfo = {"prefix", "address", "port", "name", "username", "password"};
        cm = new ConnectionManager(dbInfo[0], dbInfo[1], Integer.valueOf(dbInfo[2]), dbInfo[3], dbInfo[4], dbInfo[5]);
        cm.connect();
        cm.getDatabase().createTable("players", "(id INT(10) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(36) UNIQUE NOT NULL, username VARCHAR(16) NOT NULL, nickname VARCHAR(16) NULL, firstLogin BIGINT(15) NOT NULL, lastLogin BIGINT(16) NOT NULL);");
    }

}
