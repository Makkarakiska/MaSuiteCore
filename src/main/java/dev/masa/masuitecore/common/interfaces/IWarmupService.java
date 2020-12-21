package dev.masa.masuitecore.common.interfaces;

import java.util.function.Consumer;

public interface IWarmupService<K> {

    /**
     * Apply warmup for the player
     *
     * @param player           player to add
     * @param bypassPermission permission to bypass warmup
     * @param type             type of the warmup
     * @param callback         callback
     */
    public void applyWarmup(K player, String bypassPermission, String type, Consumer<Boolean> callback);

    /**
     * Add warmup time to cache
     *
     * @param type type of the warmup
     * @param time time of the warmup
     */
    public void addWarmupTime(String type, int time);
}
