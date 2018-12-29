package fi.matiaspaavilainen.masuitecore.bukkit;

import fi.matiaspaavilainen.masuitecore.bukkit.events.LeaveEvent;
import fi.matiaspaavilainen.masuitecore.bukkit.events.LoginEvent;
import fi.matiaspaavilainen.masuitecore.core.Updator;
import fi.matiaspaavilainen.masuitecore.core.configuration.BukkitConfiguration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class MaSuiteCore extends JavaPlugin implements Listener {

    BukkitConfiguration config = new BukkitConfiguration(this);
    public static boolean bungee = true;

    @Override
    public void onEnable() {
        // ;
        config.create(null, "messages.yml");
        config.load(null, "messages.yml").getString("player-not-online");
        // Detect if new version on spigot
        new Updator(new String[]{getDescription().getVersion(), getDescription().getName(), "60037"}).checkUpdates();
        detectBungee();
        registerListeners();
    }

    @Override
    public void onDisable() {
    }

    /**
     * Detect if BungeeCord is enabled in spigot.yml
     */
    private void detectBungee() {
        try {
            FileConfiguration conf = new YamlConfiguration();
            conf.load(new File(getServer().getWorldContainer().getAbsolutePath(), "spigot.yml"));
            bungee = conf.getBoolean("settings.bungeecord");
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

}
