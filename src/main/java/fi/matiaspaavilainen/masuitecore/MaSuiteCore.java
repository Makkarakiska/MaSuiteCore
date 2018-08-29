package fi.matiaspaavilainen.masuitecore;

import fi.matiaspaavilainen.masuitecore.config.Configuration;
import fi.matiaspaavilainen.masuitecore.database.Database;
import fi.matiaspaavilainen.masuitecore.events.LeaveEvent;
import fi.matiaspaavilainen.masuitecore.events.LoginEvent;
import fi.matiaspaavilainen.masuitecore.listeners.PluginMessageListener;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

public class MaSuiteCore extends Plugin {

    public static Database db = new Database();
    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this);
        Configuration config = new Configuration();
        config.create(this, null,"config.yml");
        config.create(this, null,"messages.yml");
        db.connect();
        db.createTable("players", "(id INT(10) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT, uuid VARCHAR(36) UNIQUE NOT NULL, username VARCHAR(16) NOT NULL, nickname VARCHAR(16) NULL, ipAddress VARCHAR(15) NOT NULL, firstLogin BIGINT(15) NOT NULL, lastLogin BIGINT(16) NOT NULL);");
        getProxy().getPluginManager().registerListener(this, new LoginEvent());
        getProxy().getPluginManager().registerListener(this, new LeaveEvent());
        getProxy().getPluginManager().registerListener(this, new PluginMessageListener());
    }
}
