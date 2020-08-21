package dev.masa.masuitecore.core.utils;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

@Deprecated
public abstract class BukkitWarmup {

    private int time;

    private BukkitTask task;
    protected final Plugin plugin;

    protected BukkitWarmup(int time, Plugin plugin) {
        this.time = time;
        this.plugin = plugin;
    }

    public abstract void count(int current);

    public final void start() {
        task = new BukkitRunnable() {

            @Override
            public void run() {
                count(time);
                if (time-- <= 0) cancel();
            }

        }.runTaskTimerAsynchronously(plugin, 0L, 20L);
    }

}
