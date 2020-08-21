package dev.masa.masuitecore.common.services;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import dev.masa.masuitecore.common.interfaces.IDatabaseServiceProvider;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Data
public abstract class AbstractDataService<K, V, P extends IDatabaseServiceProvider>{

    private P plugin;
    protected final ConcurrentHashMap<K, V> cache = new ConcurrentHashMap<>();
    protected final Dao<V, K> dao;

    @SneakyThrows
    public AbstractDataService(IDatabaseServiceProvider plugin, Class<V> clazz) {
        this.plugin = (P) plugin;
        this.dao = DaoManager.createDao(plugin.getDatabaseService().getConnection(), clazz);
        TableUtils.createTableIfNotExists(plugin.getDatabaseService().getConnection(), clazz);
    }


    /**
     * Get object from the cache by key
     *
     * @param key      key to query
     * @param callback callback to use
     */
    public void get(K key, Consumer<Optional<V>> callback) {
        if (this.isCached(key)) {
            callback.accept(this.getFromCache(key));
            return;
        }

        this.getFromDatabase(key, callback);
    }

    /**
     * Create a new <V> and save it to database
     * @param value <V> to save
     * @param callback callback to use
     */
    public abstract void create(V value, Consumer<Boolean> callback);

    /**
     * Update <V> to database
     * @param value <V> to update
     * @param callback callback to use
     */
    public abstract void update(V value, Consumer<Boolean> callback);

    /**
     * Delete <V> from database
     * @param value <V> to delete
     * @param callback callback to use
     */
    public abstract void delete(V value, Consumer<Boolean> callback);

    /**
     * Get value from database by <K>
     * @param key <K> of the <V>
     * @param callback callback to use
     */
    public abstract void getFromDatabase(K key, Consumer<Optional<V>> callback);

    /**
     * Check if <K> is cached
     *
     * @param key <K> of the <V>
     * @return return boolean from the query
     */
    public boolean isCached(K key) {
        return this.cache.containsKey(key);
    }

    /**
     * Get value from cache by <K>
     *
     * @param key <K> of the object
     * @return returns <V> or null
     */
    public Optional<V> getFromCache(K key) {
        return Optional.ofNullable(this.cache.get(key));
    }

    /**
     * Adds item to cache
     *
     * @param key   <K> of the object
     * @param value <V> to add
     */
    public void addToCache(K key, V value) {
        this.cache.put(key, value);
    }

    /**
     * Remove <K> from database
     * @param key <K> of the object
     */
    public void removeFromCache(K key) {
        this.cache.remove(key);
    }

}

