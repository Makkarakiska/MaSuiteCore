package dev.masa.masuitecore.bukkit;

import co.aikar.commands.PaperCommandManager;
import dev.masa.masuitecore.bukkit.chat.Formator;
import dev.masa.masuitecore.bukkit.commands.MaSuiteCommand;
import dev.masa.masuitecore.core.Updator;
import dev.masa.masuitecore.core.configuration.BukkitConfiguration;
import dev.masa.masuitecore.core.services.CooldownService;
import dev.masa.masuitecore.core.services.WarmupService;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class MaSuiteCore extends JavaPlugin implements Listener {

    public BukkitConfiguration config = new BukkitConfiguration();
    public Formator formator = new Formator();

    public static CooldownService cooldownService = new CooldownService();
    public static WarmupService warmupService;
    public static List<String> onlinePlayers = new ArrayList<>();

    @Override
    public void onEnable() {
        // Detect if new version on spigot
        config.create(this, null, "messages.yml");
        config.addDefault("/messages.yml", "in-cooldown", "&cYou can use that command after %time% seconds");
        config.addDefault("/messages.yml", "teleportation-started", "&7You will be teleported in &a%time%&7 seconds! &cDont move!");
        config.addDefault("/messages.yml", "teleportation-cancelled", "&cTeleportation cancelled");

        new Updator(getDescription().getVersion(), getDescription().getName(), "60037").checkUpdates();

        registerListeners();

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new MaSuiteCommand(this));

        warmupService = new WarmupService(this);
        getServer().getPluginManager().registerEvents(warmupService, this);
    }

    /**
     * Register listeners
     */
    private void registerListeners() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new CoreMessageListener(this));
    }

}
