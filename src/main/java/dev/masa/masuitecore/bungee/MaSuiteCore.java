package dev.masa.masuitecore.bungee;

import dev.masa.masuitecore.core.Updator;
import dev.masa.masuitecore.core.services.DatabaseService;
import dev.masa.masuitecore.core.services.PlayerService;
import dev.masa.masuitecore.bungee.listeners.LeaveEvent;
import dev.masa.masuitecore.bungee.listeners.LoginEvent;
import dev.masa.masuitecore.bungee.listeners.CoreMessageListener;
import dev.masa.masuitecore.core.configuration.BungeeConfiguration;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import org.bstats.bungeecord.Metrics;

import java.io.IOException;

public class MaSuiteCore extends Plugin implements Listener {

    @Getter
    public PlayerService playerService;
    @Getter
    private DatabaseService databaseService;

    public BungeeConfiguration config = new BungeeConfiguration();

    private static MaSuiteCore instance;

    @Override
    public void onEnable() {
        instance = this;
        // Load metrics
        Metrics metrics = new Metrics(this, 3125);

        // Create configuration files
        config.create(this, null, "config.yml");
        config.create(this, null, "messages.yml");

        // Register listeners
        registerListeners();

        // Detect if new version on spigot
        new Updator(getDescription().getVersion(), getDescription().getName(), "60037").checkUpdates();

        config.addDefault("/config.yml", "use-tab-completer", true);
        config.addDefault("/config.yml", "teleportation-delay", 750);

        Configuration dbInfo = config.load(null, "config.yml");
        databaseService = new DatabaseService(dbInfo.getString("database.address"), dbInfo.getInt("database.port"), dbInfo.getString("database.name"), dbInfo.getString("database.username"), dbInfo.getString("database.password"));

        playerService = new PlayerService(this);
    }

    @Override
    public void onDisable() {
        try {
            this.getDatabaseService().getConnection().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Register listeners
     */
    private void registerListeners() {
        getProxy().getPluginManager().registerListener(this, new LoginEvent(this));
        getProxy().getPluginManager().registerListener(this, new LeaveEvent(this));
        getProxy().getPluginManager().registerListener(this, new CoreMessageListener());
    }

    public static MaSuiteCore getInstance() {
        return instance;
    }
}
