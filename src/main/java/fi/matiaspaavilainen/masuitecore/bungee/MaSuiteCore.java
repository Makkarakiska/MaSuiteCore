package fi.matiaspaavilainen.masuitecore.bungee;

import fi.matiaspaavilainen.masuitecore.bungee.events.LeaveEvent;
import fi.matiaspaavilainen.masuitecore.bungee.events.LoginEvent;
import fi.matiaspaavilainen.masuitecore.bungee.listeners.CoreMessageListener;
import fi.matiaspaavilainen.masuitecore.core.Updator;
import fi.matiaspaavilainen.masuitecore.core.configuration.BungeeConfiguration;
import fi.matiaspaavilainen.masuitecore.core.services.PlayerService;
import fi.matiaspaavilainen.masuitecore.core.utils.HibernateUtil;
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
        Metrics metrics = new Metrics(this);

        // Create configuration files
        config.create(this, null, "config.yml");
        config.create(this, null, "messages.yml");

        // Register listeners
        registerListeners();

        // Detect if new version on spigot
        new Updator(getDescription().getVersion(), getDescription().getName(), "60037").checkUpdates();

        config.addDefault("/config.yml", "use-tab-completer", true);

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
