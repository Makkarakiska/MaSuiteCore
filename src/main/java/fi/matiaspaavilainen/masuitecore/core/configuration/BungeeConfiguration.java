package fi.matiaspaavilainen.masuitecore.core.configuration;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

public class BungeeConfiguration {

    private Configuration configuration;
    private File file;

    private Plugin plugin;

    /**
     * An empty constructor for BungeeConfiguration
     */
    public BungeeConfiguration() {

    }

    /**
     * Constructor for BungeeConfiguration
     *
     * @param configuration {@link Configuration} to use
     * @param file          {@link File} to use
     */
    public BungeeConfiguration(Configuration configuration, File file) {
        this.configuration = configuration;
        this.file = file;
    }

    /**
     * Loads configuration file
     *
     * @param folder module folder
     * @param config file name
     * @return configuration file to use
     */
    public Configuration load(String folder, String config) {
        Configuration configuration = null;
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
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Add default value to config file
     *
     * @param path  path to value
     * @param value value
     */
    public void addDefault(String path, Object value) {
        if (this.configuration.get(path) == null) {
            this.configuration.set(path, value);
            this.save(this.configuration, this.file.getName());
        }
    }

    /**
     * Creates configuration file
     *
     * @param plugin {@link Plugin} to use
     * @param folder module folder
     * @param config file name
     */
    public void create(Plugin plugin, String folder, String config) {
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
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                try (InputStream is = plugin.getResourceAsStream("bungee/" + config);
                     OutputStream os = new FileOutputStream(configFile)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        }
    }

    /**
     * Creates configuration file
     *
     * @param config file name
     */
    public void create(String config) {
        File f = new File("plugins/MaSuite");
        if (!f.exists()) {
            f.mkdir();
        }
        File configFile = new File(f, config);
        configChecker(configFile);
    }

    /**
     * Saves the configuration file
     *
     * @param config file
     * @param file   file name
     */
    public void save(net.md_5.bungee.config.Configuration config, String file) {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File("plugins/MaSuite", file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if configuration exists
     *
     * @param config configuration to check
     */
    private void configChecker(File config) {
        if (!config.exists()) {
            try {
                config.createNewFile();
                try (InputStream is = getClass().getResourceAsStream(config.getName());
                     OutputStream os = new FileOutputStream(config)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        }
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
