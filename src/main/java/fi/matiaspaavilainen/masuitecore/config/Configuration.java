package fi.matiaspaavilainen.masuitecore.config;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

public class Configuration {
    public net.md_5.bungee.config.Configuration load(String folder, String config) {
        net.md_5.bungee.config.Configuration configuration = null;
        File f = null;
        if (folder != null) {
            f = new File("plugins/MaSuite/" + folder);
        } else {
            f = new File("plugins/MaSuite");
        }
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(f, config));
            return configuration;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public net.md_5.bungee.config.Configuration load(String config) {
        net.md_5.bungee.config.Configuration configuration = null;
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File("plugins/MaSuite/", config));
            return configuration;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void create(Plugin p, String folder, String config) {
        File f = null;
        if (folder != null) {
            f = new File("plugins/MaSuite/" + folder);
        } else {
            f = new File("plugins/MaSuite");
        }

        if (!f.exists()) {
            f.mkdir();
        }
        File configFile = new File(f, config);
        configChecker(p, config, configFile);
    }

    public void create(Plugin p, String config) {
        File f = new File("plugins/MaSuite");
        if (!f.exists()) {
            f.mkdir();
        }
        File configFile = new File(f, config);
        configChecker(p, config, configFile);
    }

    public void save(net.md_5.bungee.config.Configuration config, String file) {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File("plugins/MaSuite", file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configChecker(Plugin p, String config, File configFile) {
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                try (InputStream is = p.getResourceAsStream(config);
                     OutputStream os = new FileOutputStream(configFile)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        }
    }
}
