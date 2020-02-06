package dev.masa.masuitecore.bungee;

import dev.masa.masuitecore.core.Updator;
import dev.masa.masuitecore.core.services.PlayerService;
import dev.masa.masuitecore.core.utils.HibernateUtil;
import dev.masa.masuitecore.bungee.events.LeaveEvent;
import dev.masa.masuitecore.bungee.events.LoginEvent;
import dev.masa.masuitecore.bungee.listeners.CoreMessageListener;
import dev.masa.masuitecore.core.configuration.BungeeConfiguration;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

public class MaSuiteCore extends Plugin implements Listener {

    public PlayerService playerService;
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

        playerService = new PlayerService();
    }

    @Override
    public void onDisable() {
        HibernateUtil.shutdown();
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
