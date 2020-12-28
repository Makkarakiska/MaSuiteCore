package dev.masa.masuitecore.common.config;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;

public class ConfigLoader {

    public static CommentedConfigurationNode loadConfig(String file) {
        try {
            File f = new File("plugins/MaSuite/" + file);

            if (f.exists()) {
                System.out.println("File found " + file + " " + f.getAbsolutePath());
            }

            final YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                    .file(new File("plugins/MaSuite/" + file))
                    .defaultOptions(opts -> opts.shouldCopyDefaults(true))
                    .nodeStyle(NodeStyle.BLOCK)
                    .build();

            return loader.load();
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }
        return null;
    }
}
