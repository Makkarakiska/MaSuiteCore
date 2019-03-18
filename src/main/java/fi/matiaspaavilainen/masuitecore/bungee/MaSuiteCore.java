package fi.matiaspaavilainen.masuitecore.bungee;

import fi.matiaspaavilainen.masuitecore.bungee.events.LeaveEvent;
import fi.matiaspaavilainen.masuitecore.bungee.events.LoginEvent;
import fi.matiaspaavilainen.masuitecore.bungee.listeners.CoreMessageListener;
import fi.matiaspaavilainen.masuitecore.core.Updator;
import fi.matiaspaavilainen.masuitecore.core.configuration.BungeeConfiguration;
import fi.matiaspaavilainen.masuitecore.core.database.ConnectionManager;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import org.bstats.bungeecord.Metrics;

public class MaSuiteCore extends Plugin implements Listener {

    public BungeeConfiguration config = new BungeeConfiguration();
    private ConnectionManager cm = null;

    @Override
    public void onEnable() {
        // Load metrics
        Metrics metrics = new Metrics(this);

        // Create configuration files
        config.create(this, null, "config.yml");
        config.create(this, null, "messages.yml");

        // Connect to database and create table
        Configuration dbInfo = config.load(null, "config.yml");
        cm = new ConnectionManager(dbInfo.getString("database.table-prefix"), dbInfo.getString("database.address"), dbInfo.getInt("database.port"), dbInfo.getString("database.name"), dbInfo.getString("database.username"), dbInfo.getString("database.password"));
        cm.connect();
        cm.getDatabase().createTable("players", "(id INT(10) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(36) UNIQUE NOT NULL, username VARCHAR(16) NOT NULL, nickname VARCHAR(16) NULL, firstLogin BIGINT(15) NOT NULL, lastLogin BIGINT(16) NOT NULL);");

        // Register listeners
        registerListeners();

        // Detect if new version on spigot
        new Updator(new String[]{getDescription().getVersion(), getDescription().getName(), "60037"}).checkUpdates();

        config.addDefault("/config.yml", "use-tab-completer", false);
    }

    @Override
    public void onDisable() {
        cm.close();
    }

    /**
     * Register listeners
     */
    private void registerListeners() {
        getProxy().getPluginManager().registerListener(this, new LoginEvent(this));
        getProxy().getPluginManager().registerListener(this, new LeaveEvent(this));
        getProxy().getPluginManager().registerListener(this, new CoreMessageListener());
    }
}
