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
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MaSuiteCore extends JavaPlugin implements Listener {

    public Formator formator = new Formator();

    @Getter
    public static CooldownService cooldownService;

    @Getter
    public static WarmupService warmupService;

    @Getter
    public static List<String> onlinePlayers = new ArrayList<>();

    private static MaSuiteCore instance;

    @Getter
    private CoreServerMessageConfig messages;

    @Getter
    private List<UUID> teleportQueue = new ArrayList<>();

    @SneakyThrows
    @Override
    public void onEnable() {

        YamlConfigurationLoader messagesLoader = YamlConfigurationLoader.builder()
                .file(new File("plugins/MaSuite/messages.yml"))
                .defaultOptions(opts -> opts.shouldCopyDefaults(true))
                .nodeStyle(NodeStyle.BLOCK)
                .build();

        CommentedConfigurationNode messagesNode = messagesLoader.load();
        this.messages = CoreServerMessageConfig.loadFrom(messagesNode);
        this.messages.saveTo(messagesNode);

        messagesLoader.save(messagesNode);
        this.messages = CoreServerMessageConfig.loadFrom(ConfigLoader.loadConfig("config.yml"));

        // Detect if new version on spigot
        new Updator(getDescription().getVersion(), getDescription().getName(), "60037").checkUpdates();

        registerListeners();

        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new MaSuiteCommand(this));

        warmupService = new WarmupService(this);
        cooldownService = new CooldownService();
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
