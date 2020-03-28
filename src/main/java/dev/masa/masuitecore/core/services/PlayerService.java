package dev.masa.masuitecore.core.services;

import dev.masa.masuitecore.bungee.MaSuiteCore;
import dev.masa.masuitecore.bungee.events.MaSuitePlayerCreationEvent;
import dev.masa.masuitecore.bungee.events.MaSuitePlayerUpdateEvent;
import dev.masa.masuitecore.core.models.MaSuitePlayer;
import dev.masa.masuitecore.core.objects.Location;
import dev.masa.masuitecore.core.utils.HibernateUtil;

import javax.persistence.EntityManager;
import java.util.*;

public class PlayerService {

    private EntityManager entityManager = HibernateUtil.addClasses(MaSuitePlayer.class).getEntityManager();
    public HashMap<UUID, MaSuitePlayer> players = new HashMap<>();

    private MaSuiteCore plugin;

    public PlayerService(MaSuiteCore plugin) {
        this.plugin = plugin;
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
    public List<MaSuitePlayer> getAllPlayers(boolean cachedData) {
        if (cachedData) {
            return new ArrayList<>(players.values());
        }
        return entityManager.createQuery("SELECT p FROM MaSuitePlayer p", MaSuitePlayer.class).getResultList();
    }

    /**
     * Loads {@link MaSuitePlayer} from cache or database
     *
     * @param uuid uuid of the player
     * @return returns {@link MaSuitePlayer} or null
     */
    private MaSuitePlayer loadPlayer(UUID uuid) {
        // Check cache
        MaSuitePlayer cachedPlayer = players.get(uuid);
        if (cachedPlayer != null) {
            return cachedPlayer;
        }

        // Search player from database
        MaSuitePlayer player = entityManager.find(MaSuitePlayer.class, uuid);

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
    private MaSuitePlayer loadPlayer(String username) {
        // Check cache
        Optional<MaSuitePlayer> cachedHome = players.values().stream().filter(player -> player.getUsername().equalsIgnoreCase(username)).findFirst();
        if (cachedHome.isPresent()) {
            return cachedHome.get();
        }

        // Search player from database
        MaSuitePlayer player = entityManager.createNamedQuery("findPlayerByName", MaSuitePlayer.class)
                .setParameter("username", username)
                .getResultList().stream().findFirst().orElse(null);

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
    public MaSuitePlayer createPlayer(MaSuitePlayer player) {
        entityManager.getTransaction().begin();
        entityManager.persist(player);
        entityManager.getTransaction().commit();

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
    public MaSuitePlayer updatePlayer(MaSuitePlayer player) {
        entityManager.getTransaction().begin();
        entityManager.merge(player);
        entityManager.getTransaction().commit();
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
