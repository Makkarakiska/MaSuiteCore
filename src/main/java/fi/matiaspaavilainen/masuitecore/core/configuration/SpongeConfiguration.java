package fi.matiaspaavilainen.masuitecore.core.configuration;

import fi.matiaspaavilainen.masuitecore.sponge.MaSuiteCore;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class SpongeConfiguration {

    private Path configDir;
    private Path configFile;
    private ConfigurationLoader<CommentedConfigurationNode> configLoader;
    private CommentedConfigurationNode configNode;

    private MaSuiteCore plugin;

    public SpongeConfiguration(MaSuiteCore plugin, Path configDir, Path configFile) {
        this.plugin = plugin;
        this.configDir = configDir;
        this.configFile = configFile;
        configLoader = HoconConfigurationLoader.builder().setPath(configFile).build();
    }

    //Creates config file not directory
    public void setup() {
        System.out.println("adad1");
        try {
            configNode = configLoader.load();
            System.out.println("adad2");
            if (!configNode.hasMapChildren()) { // is empty
                System.out.println("adad3");
                Optional<Asset> conf = Sponge.getAssetManager().getAsset("config.conf");
                if (conf.isPresent()) {
                    System.out.println("adad4");
                    ConfigurationLoader<CommentedConfigurationNode> defaultLoader = HoconConfigurationLoader.builder().setURL(conf.get().getUrl()).build();
                    try {
                        System.out.println("adad5");
                        configNode = defaultLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Loads config file if it already exists
    public void load() {
        try {
            configNode = configLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //And obviously saves config whenever needed.
    public void save() {
        try {
            configLoader.save(configNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Path getConfigDir() {
        return configDir;
    }

    public CommentedConfigurationNode get() {
        return configNode;
    }
}
