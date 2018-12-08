package fi.matiaspaavilainen.masuitecore;

import fi.matiaspaavilainen.masuitecore.config.Configuration;
import fi.matiaspaavilainen.masuitecore.database.Database;
import fi.matiaspaavilainen.masuitecore.events.LeaveEvent;
import fi.matiaspaavilainen.masuitecore.events.LoginEvent;
import fi.matiaspaavilainen.masuitecore.listeners.MaSuitePlayerGroup;
import fi.matiaspaavilainen.masuitecore.listeners.MaSuitePlayerLocation;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

public class MaSuiteCore extends Plugin implements Listener {

    public static Database db = new Database();
    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this);
        Configuration config = new Configuration();
        config.create(this, null,"config.yml");
        config.create(this, null,"messages.yml");
        db.connect();
        db.createTable("players", "(id INT(10) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(36) UNIQUE NOT NULL, username VARCHAR(16) NOT NULL, nickname VARCHAR(16) NULL, ipAddress VARCHAR(15) NOT NULL, firstLogin BIGINT(15) NOT NULL, lastLogin BIGINT(16) NOT NULL);");
        getProxy().getPluginManager().registerListener(this, new LoginEvent(this));
        getProxy().getPluginManager().registerListener(this, new LeaveEvent());
        getProxy().getPluginManager().registerListener(this, new MaSuitePlayerLocation());
        getProxy().getPluginManager().registerListener(this, new MaSuitePlayerGroup());
        // Detect if new version on spigot
        Updator updator = new Updator();
        updator.checkVersion(this.getDescription(), "60037");

        net.md_5.bungee.config.Configuration settings = config.load(null, "config.yml");
        if (settings.get("get-group-on-join") == null) {
            settings.set("get-group-on-join", true);
            config.save(settings, "config.yml");
        }
    }

    @Override
    public void onDisable(){
        db.hikari.close();
    }
}
