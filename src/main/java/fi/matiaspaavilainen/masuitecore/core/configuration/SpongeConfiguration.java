package fi.matiaspaavilainen.masuitecore.core.configuration;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;

import java.io.File;
import java.io.IOException;

public class SpongeConfiguration {

    private File rootFolder;
    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private CommentedConfigurationNode confNode;

    public SpongeConfiguration() {
    }

    public void setup(File folder) {
        this.rootFolder = folder;
    }

    public CommentedConfigurationNode getConfig() {
        return this.confNode;
    }

    public void create(String folder, String config) {
        if (!rootFolder.exists()) {
            rootFolder.mkdir();
        }

        try {
            File file = null;
            if (folder != null) {
                file = new File(rootFolder + "/" + folder, config);
            } else {
                file = new File(rootFolder, config);
            }

            loader = HoconConfigurationLoader.builder().setFile(file).build();
            if (!file.exists()) {
                file.createNewFile();
                confNode = loader.load();
                loader.save(confNode);
            }

            confNode = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(String folder, String config) {
        Sponge.getAssetManager().getAsset("");
    }

    public void save() {
        try {
            loader.save(confNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
