package fi.matiaspaavilainen.masuitecore.core.configuration;

import com.google.common.io.ByteStreams;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;

public class BukkitConfiguration {

    private JavaPlugin plugin;

    private HashMap<String, FileConfiguration> configs = new HashMap<>();

    /**
     * An empty constructor for BukkitConfiguration
     */
    public BukkitConfiguration() {
    }

    /**
     * Loads configuration file
     *
     * @param folder module folder
     * @param config file name
     * @return configuration file to use
     **/
    public FileConfiguration load(String folder, String config) {
        FileConfiguration configuration = null;
        if (configs.containsKey(folder + "/" + config)) {
            return configs.get(folder + "/" + config);
        }
        try {
            configuration = new YamlConfiguration();
            configuration.load(loadFile(folder, config));
            configs.put(folder + "/" + config, configuration);
            return configuration;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Load file
     *
     * @param folder module folder
     * @param config file name
     * @return file to use
     */
    public File loadFile(String folder, String config) {
        File f = null;
        if (folder != null) {
            f = new File("plugins/MaSuite/" + folder);
        } else {
            f = new File("plugins/MaSuite");
        }
        return new File(f, config);
    }

    /**
     * Creates configuration file
     *
     * @param plugin {@link JavaPlugin} to use
     * @param folder module folder
     * @param config file name
     */
    public void create(JavaPlugin plugin, String folder, String config) {
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
                try (InputStream is = plugin.getResource("bukkit/" + config);
                     OutputStream os = new FileOutputStream(configFile)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        }
    }


    /**
     * Add default value to config file
     *
     * @param filePath location of the file
     * @param path     path to value
     * @param value    value
     */
    public void addDefault(String filePath, String path, Object value) {
        String[] fullPath = filePath.split("/");
        File file = null;
        if (fullPath.length == 1) {
            file = this.loadFile(null, fullPath[0]);
        } else {
            file = this.loadFile(fullPath[0], fullPath[1]);
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (config.get(path) == null) {
            config.set(path, value);
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Copy from bungee dir
     *
     * @param plugin {@link JavaPlugin} to use
     * @param folder
     * @param config
     */
    public void copyFromBungee(JavaPlugin plugin, String folder, String config) {
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
                try (InputStream is = plugin.getResource("bungee/" + config);
                     OutputStream os = new FileOutputStream(configFile)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        }
    }

}
