package dev.masa.masuitecore.common.services;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import dev.masa.masuitecore.common.interfaces.IDatabaseServiceProvider;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.Optional;
import java.util.function.Consumer;

@Data
public abstract class AbstractDataService<K, V, P extends IDatabaseServiceProvider>{

    private P plugin;
    protected final Dao<V, K> dao;

    @SneakyThrows
    public AbstractDataService(IDatabaseServiceProvider plugin, Class<V> clazz) {
        this.plugin = (P) plugin;
        this.dao = DaoManager.createDao(plugin.getDatabaseService().getConnection(), clazz);
        this.dao.setObjectCache(true);
        TableUtils.createTableIfNotExists(plugin.getDatabaseService().getConnection(), clazz);
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

}

