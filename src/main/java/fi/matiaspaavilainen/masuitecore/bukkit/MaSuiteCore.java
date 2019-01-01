package fi.matiaspaavilainen.masuitecore.bukkit;

import fi.matiaspaavilainen.masuitecore.bukkit.events.LeaveEvent;
import fi.matiaspaavilainen.masuitecore.bukkit.events.LoginEvent;
import fi.matiaspaavilainen.masuitecore.core.Updator;
import fi.matiaspaavilainen.masuitecore.core.configuration.BukkitConfiguration;
import fi.matiaspaavilainen.masuitecore.core.database.ConnectionManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class MaSuiteCore extends JavaPlugin implements Listener {

    private BukkitConfiguration config = new BukkitConfiguration();
    private ConnectionManager cm = null;
    public static boolean bungee = true;

    @Override
    public void onEnable() {
        // Detect if new version on spigot
        config.create(this, null, "messages.yml");
        new Updator(new String[]{getDescription().getVersion(), getDescription().getName(), "60037"}).checkUpdates();
        detectBungee();
        registerListeners();
    }

    @Override
    public void onDisable() {
        if (!bungee) {
            cm.close();
        }
    }

    /**
     * Detect if BungeeCord is enabled in spigot.yml and if disabled run {@link #setupNoBungee()}
     */
    private void detectBungee() {
        try {
            FileConfiguration conf = new YamlConfiguration();
            conf.load(new File(getServer().getWorldContainer().getAbsolutePath(), "spigot.yml"));
            bungee = conf.getBoolean("settings.bungeecord");
            if (!bungee) {
                setupNoBungee();
            }

        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Register listeners
     */
    private void registerListeners() {
        if (!bungee) {
            getServer().getPluginManager().registerEvents(new LeaveEvent(), this);
            getServer().getPluginManager().registerEvents(new LoginEvent(), this);
        } else {
            getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
            getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new CoreMessageListener(this));
        }
    }

    /**
     * Setup for standalone server
     */
    private void setupNoBungee() {
        Metrics metrics = new Metrics(this);
        config.create(this, null, "bungee/config.yml");
        FileConfiguration dbInfo = config.load(null, "bungee/config.yml");
        cm = new ConnectionManager(dbInfo.getString("database.table-prefix"), dbInfo.getString("database.address"), dbInfo.getInt("database.port"), dbInfo.getString("database.name"), dbInfo.getString("database.username"), dbInfo.getString("database.password"));
        cm.connect();
        cm.getDatabase().createTable("players", "(id INT(10) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(36) UNIQUE NOT NULL, username VARCHAR(16) NOT NULL, nickname VARCHAR(16) NULL, firstLogin BIGINT(15) NOT NULL, lastLogin BIGINT(16) NOT NULL);");
    }

}
