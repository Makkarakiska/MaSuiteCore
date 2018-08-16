package fi.matiaspaavilainen.masuitecore;

import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

public class MaSuiteCore extends Plugin {

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this);
    }
}
