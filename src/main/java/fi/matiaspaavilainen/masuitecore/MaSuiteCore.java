package fi.matiaspaavilainen.masuitecore;

import fi.matiaspaavilainen.masuitecore.config.Configuration;
import fi.matiaspaavilainen.masuitecore.database.Database;
import fi.matiaspaavilainen.masuitecore.events.LeaveEvent;
import fi.matiaspaavilainen.masuitecore.events.LoginEvent;
import fi.matiaspaavilainen.masuitecore.listeners.MaSuitePlayerGroup;
import fi.matiaspaavilainen.masuitecore.listeners.MaSuitePlayerLocation;
import fi.matiaspaavilainen.masuitecore.managers.Group;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginDescription;
import net.md_5.bungee.event.EventHandler;
import org.bstats.bungeecord.Metrics;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

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
        getProxy().getPluginManager().registerListener(this, new LoginEvent());
        getProxy().getPluginManager().registerListener(this, new LeaveEvent());
        getProxy().getPluginManager().registerListener(this, new MaSuitePlayerLocation());
        getProxy().getPluginManager().registerListener(this, new MaSuitePlayerGroup());
        getProxy().getPluginManager().registerListener(this, this);
        // Detect if new version on spigot
        Updator updator = new Updator();
        updator.checkVersion(this.getDescription(), "60037");
    }

    @Override
    public void onDisable(){
        db.hikari.close();
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent e) throws IOException {
        if(e.getTag().equals("BungeeCord")){
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
            String subchannel = in.readUTF();
            if(subchannel.equals("MaSuitePlayerGroup")){
                new Debugger().sendMessage("[MaSuiteCore] [MaSuitePlayerGroup] group received");
                MaSuitePlayerGroup.groups.put(UUID.fromString(in.readUTF()), new Group(in.readUTF(), in.readUTF()));
                new Debugger().sendMessage("[MaSuiteCore] [MaSuitePlayerGroup] group saved to cache");
            }
        }
    }
}
