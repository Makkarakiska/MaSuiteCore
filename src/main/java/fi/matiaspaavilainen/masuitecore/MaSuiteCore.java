package fi.matiaspaavilainen.masuitecore;

import fi.matiaspaavilainen.masuitecore.chat.Colorize;
import fi.matiaspaavilainen.masuitecore.chat.Date;
import fi.matiaspaavilainen.masuitecore.config.Creator;
import fi.matiaspaavilainen.masuitecore.config.Loader;
import fi.matiaspaavilainen.masuitecore.database.Database;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

public class MaSuiteCore extends Plugin {
    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this);
    }
    public static Colorize colorize(){
        return new Colorize();
    }
    public static Date date(){
        return new Date();
    }
    public static Database database(){
        return new Database();
    }
    public static Creator creator(){
        return new Creator();
    }
    public static Loader loader(){
        return new Loader();
    }
}
