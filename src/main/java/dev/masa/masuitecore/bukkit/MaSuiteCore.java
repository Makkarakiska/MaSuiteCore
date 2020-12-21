package dev.masa.masuitecore.bukkit;

import co.aikar.commands.PaperCommandManager;
import dev.masa.masuitecore.bukkit.chat.Formator;
import dev.masa.masuitecore.bukkit.commands.MaSuiteCommand;
import dev.masa.masuitecore.bukkit.services.WarmupService;
import dev.masa.masuitecore.common.config.ConfigLoader;
import dev.masa.masuitecore.common.config.server.CoreServerMessageConfig;
import dev.masa.masuitecore.common.services.CooldownService;
import dev.masa.masuitecore.common.utils.Updator;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class MaSuiteCore extends JavaPlugin implements Listener {

    public Formator formator = new Formator();

    @Getter
    public static CooldownService cooldownService = new CooldownService();

    @Getter
    public static WarmupService warmupService;

    @Getter
    public static List<String> onlinePlayers = new ArrayList<>();

    private static MaSuiteCore instance;

    @Getter
    private CoreServerMessageConfig messages;

    @SneakyThrows
    @Override
    public void onEnable() {
        // Detect if new version on spigot
        this.messages = CoreServerMessageConfig.loadFrom(ConfigLoader.loadConfig("config.yml"));

        new Updator(getDescription().getVersion(), getDescription().getName(), "60037").checkUpdates();

        registerListeners();

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new MaSuiteCommand(this));

        warmupService = new WarmupService(this);
        getServer().getPluginManager().registerEvents(warmupService, this);

        instance = this;
    }

    /**
     * Register listeners
     */
    private void registerListeners() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new CoreMessageListener(this));
    }

    public static MaSuiteCore getInstance() {
        return instance;
    }

}
