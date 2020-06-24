package dev.masa.masuitecore.core.services;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.table.TableUtils;
import dev.masa.masuitecore.bungee.MaSuiteCore;
import dev.masa.masuitecore.bungee.events.MaSuitePlayerCreationEvent;
import dev.masa.masuitecore.bungee.events.MaSuitePlayerUpdateEvent;
import dev.masa.masuitecore.core.models.MaSuitePlayer;
import dev.masa.masuitecore.core.objects.Location;
import lombok.SneakyThrows;

import java.util.*;

public class PlayerService {

    Dao<MaSuitePlayer, UUID> playerDao;
    public HashMap<UUID, MaSuitePlayer> players = new HashMap<>();

    private MaSuiteCore plugin;

    @SneakyThrows
    public PlayerService(MaSuiteCore plugin) {
        this.plugin = plugin;
        this.playerDao = DaoManager.createDao(plugin.getDatabaseService().getConnection(), MaSuitePlayer.class);
        TableUtils.createTableIfNotExists(plugin.getDatabaseService().getConnection(), MaSuitePlayer.class);
    }

    /**
     * Get {@link MaSuitePlayer} from UUID
     *
     * @param uuid uuid of the player
     * @return returns {@link MaSuitePlayer} or null
     */
    public MaSuitePlayer getPlayer(UUID uuid) {
        return this.loadPlayer(uuid);
    }

    /**
     * Get {@link MaSuitePlayer} from username
     *
     * @param username username of the player
     * @return returns {@link MaSuitePlayer} or null
     */
    public MaSuitePlayer getPlayer(String username) {
        return this.loadPlayer(username);
    }

    /**
     * Get all {@link MaSuitePlayer}s from cache or database
     *
     * @param cachedData do we load players from cache or from database (might cause huge lag spikes)
     * @return returns a list of {@link MaSuitePlayer}s
     */
    @SneakyThrows
    public List<MaSuitePlayer> getAllPlayers(boolean cachedData) {
        if (cachedData) {
            return new ArrayList<>(players.values());
        }
        return playerDao.queryForAll();
    }

    /**
     * Loads {@link MaSuitePlayer} from cache or database
     *
     * @param uuid uuid of the player
     * @return returns {@link MaSuitePlayer} or null
     */
    @SneakyThrows
    private MaSuitePlayer loadPlayer(UUID uuid) {
        // Check cache
        MaSuitePlayer cachedPlayer = players.get(uuid);
        if (cachedPlayer != null) {
            return cachedPlayer;
        }

        // Search player from database
        MaSuitePlayer player = playerDao.queryForId(uuid);

        // Add player into cache if not null
        if (player != null) {
            players.put(player.getUniqueId(), player);
        }
        return player;
    }

    /**
     * Loads {@link MaSuitePlayer} from cache or database
     *
     * @param username username of the player
     * @return returns {@link MaSuitePlayer} or null
     */
    @SneakyThrows
    private MaSuitePlayer loadPlayer(String username) {

        // Check cache
        Optional<MaSuitePlayer> cachedHome = players.values().stream().filter(player -> player.getUsername().equalsIgnoreCase(username)).findFirst();
        if (cachedHome.isPresent()) {
            return cachedHome.get();
        }

        SelectArg preparedUsername = new SelectArg(username);

        // Search player from database
        MaSuitePlayer player = playerDao.queryForEq("username", preparedUsername).stream().findFirst().orElse(null);

        // Add player into cache if not null
        if (player != null) {
            players.put(player.getUniqueId(), player);
        }
        return player;
    }

    /**
     * Creates a new {@link MaSuitePlayer}
     *
     * @param player player to create
     * @return returns created player
     */
    @SneakyThrows
    public MaSuitePlayer createPlayer(MaSuitePlayer player) {
        playerDao.create(player);

        players.put(player.getUniqueId(), player);

        plugin.getProxy().getPluginManager().callEvent(new MaSuitePlayerCreationEvent(player));
        return player;
    }

    /**
     * Updates {@link MaSuitePlayer}
     *
     * @param player player to update
     * @return returns updated player
     */
    @SneakyThrows
    public MaSuitePlayer updatePlayer(MaSuitePlayer player) {
        playerDao.update(player);
        players.put(player.getUniqueId(), player);

        plugin.getProxy().getPluginManager().callEvent(new MaSuitePlayerUpdateEvent(player));
        return player;
    }

    /**
     * Update player location into cache but not into database
     *
     * @param player   player to use
     * @param location the new location of player
     * @return returns updated player
     */
    public MaSuitePlayer updatePlayerLocation(MaSuitePlayer player, Location location) {
        player.setLocation(location);
        players.put(player.getUniqueId(), player);
        return player;
    }
}
