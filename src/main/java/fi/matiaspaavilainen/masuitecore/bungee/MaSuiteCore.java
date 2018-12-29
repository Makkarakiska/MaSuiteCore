package fi.matiaspaavilainen.masuitecore.bungee;

import fi.matiaspaavilainen.masuitecore.core.Updator;
import fi.matiaspaavilainen.masuitecore.bungee.events.LeaveEvent;
import fi.matiaspaavilainen.masuitecore.bungee.events.LoginEvent;
import fi.matiaspaavilainen.masuitecore.core.configuration.BungeeConfiguration;
import fi.matiaspaavilainen.masuitecore.core.database.Database;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

import java.sql.SQLException;

public class MaSuiteCore extends Plugin implements Listener {

    public static Database db = new Database();
    private BungeeConfiguration config = new BungeeConfiguration();

    @Override
    public void onEnable() {
        // Load metrics
        Metrics metrics = new Metrics(this);

        // Create configuration files
        config.create(null, "config.yml");
        config.create(null, "messages.yml");

        // Connect to database and create table
        db.connect();
        db.createTable("players", "(id INT(10) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(36) UNIQUE NOT NULL, username VARCHAR(16) NOT NULL, nickname VARCHAR(16) NULL, ipAddress VARCHAR(15) NOT NULL, firstLogin BIGINT(15) NOT NULL, lastLogin BIGINT(16) NOT NULL);");

        // Register listeners
        registerListeners();

        // Detect if new version on spigot
        new Updator(new String[]{getDescription().getVersion(), getDescription().getName(), "60037"}).checkUpdates();
    }

    @Override
    public void onDisable() {
        // Close connection if it is not null
        try {
            if (db.hikari.getConnection() != null) {
                db.hikari.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Register listeners
     */
    private void registerListeners() {
        getProxy().getPluginManager().registerListener(this, new LoginEvent());
        getProxy().getPluginManager().registerListener(this, new LeaveEvent());
    }
}
