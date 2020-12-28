package dev.masa.masuitecore.bungee;

import dev.masa.masuitecore.bungee.listeners.CoreMessageListener;
import dev.masa.masuitecore.bungee.listeners.LeaveEvent;
import dev.masa.masuitecore.bungee.listeners.PlayerLoginEvent;
import dev.masa.masuitecore.bungee.services.PlayerService;
import dev.masa.masuitecore.bungee.services.TeleportService;
import dev.masa.masuitecore.common.config.ConfigLoader;
import dev.masa.masuitecore.common.config.CorePluginConfig;
import dev.masa.masuitecore.common.config.proxy.CoreProxyMessageConfig;
import dev.masa.masuitecore.common.interfaces.IDatabaseServiceProvider;
import dev.masa.masuitecore.common.services.DatabaseService;
import dev.masa.masuitecore.common.utils.Updator;
import lombok.Getter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;

public class MaSuiteCore extends Plugin implements Listener, IDatabaseServiceProvider {

    @Getter
    public PlayerService playerService;
    @Getter
    private DatabaseService databaseService;
    @Getter
    private TeleportService teleportService;

    private static MaSuiteCore instance;

    @Getter
    private CorePluginConfig config;
    @Getter
    private CoreProxyMessageConfig messages;

    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;
        // Load metrics
        Metrics metrics = new Metrics(this, 3125);

        // Register listeners
        registerListeners();

        // Detect if new version on spigot
        new Updator(getDescription().getVersion(), getDescription().getName(), "60037").checkUpdates();

        YamlConfigurationLoader configLoader = YamlConfigurationLoader.builder()
                .file(new File("plugins/MaSuite/config.yml"))
                .defaultOptions(opts -> opts.shouldCopyDefaults(true))
                .nodeStyle(NodeStyle.BLOCK)
                .build();

        CommentedConfigurationNode configNode = configLoader.load();
        this.config = CorePluginConfig.loadFrom(configNode);
        this.config.saveTo(configNode);
        configLoader.save(configNode);


        YamlConfigurationLoader messagesLoader = YamlConfigurationLoader.builder()
                .file(new File("plugins/MaSuite/messages.yml"))
                .defaultOptions(opts -> opts.shouldCopyDefaults(true))
                .nodeStyle(NodeStyle.BLOCK)
                .build();

        CommentedConfigurationNode messagesNode = messagesLoader.load();
        this.messages = CoreProxyMessageConfig.loadFrom(messagesNode);
        this.messages.saveTo(messagesNode);

        messagesLoader.save(messagesNode);




        databaseService = new DatabaseService(
                config.getDatabase().getDatabaseAddress(),
                config.getDatabase().getDatabasePort(),
                config.getDatabase().getDatabaseName(),
                config.getDatabase().getDatabaseUsername(),
                config.getDatabase().getDatabasePassword()
        );

        playerService = new PlayerService(this);
        teleportService = new TeleportService(this);
    }

    @Override
    public void onDisable() {
        try {
            this.getDatabaseService().getConnection().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Register listeners
     */
    private void registerListeners() {
        getProxy().getPluginManager().registerListener(this, new PlayerLoginEvent(this));
        getProxy().getPluginManager().registerListener(this, new LeaveEvent(this));
        getProxy().getPluginManager().registerListener(this, new CoreMessageListener());
    }

    public static MaSuiteCore getInstance() {
        return instance;
    }
}
