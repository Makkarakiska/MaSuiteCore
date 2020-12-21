package dev.masa.masuitecore.common.services;

import dev.masa.masuitecore.common.interfaces.IWarmupService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class AbstractWarmupService<T, K> implements IWarmupService<K> {

    public HashMap<UUID, String> warmups = new HashMap<>();
    public HashMap<String, Integer> warmupTimes = new HashMap<>();

    protected final T plugin;

    public AbstractWarmupService(T plugin) {
        this.plugin = plugin;
    }

    public void applyWarmup(K player, String bypassPermission, String type, Consumer<Boolean> callback) {
        throw new NotImplementedException();
    }

    public void addWarmupTime(String type, int time) {
        warmupTimes.put(type, time);
    }
}
