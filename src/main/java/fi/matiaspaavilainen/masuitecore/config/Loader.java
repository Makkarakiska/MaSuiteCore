package fi.matiaspaavilainen.masuitecore.config;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;

public class Loader {
    public static Configuration load(String config) {
        Configuration configuration = null;
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File("plugins/MaSuite", config));
            return configuration;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
