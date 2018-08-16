package fi.matiaspaavilainen.masuitecore.config;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.*;

public class Creator {

    public static void create(Plugin p, String config) {
        File f = new File("plugins/MaSuite");
        if (!f.exists()) {
            f.mkdir();
        }
        File configFile = new File(f, config);
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
