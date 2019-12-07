package fi.matiaspaavilainen.masuitecore.core.services;

import fi.matiaspaavilainen.masuitecore.core.models.MaSuitePlayer;
import fi.matiaspaavilainen.masuitecore.core.utils.HibernateUtil;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.UUID;

public class PlayerService {

    private EntityManager entityManager = HibernateUtil.getEntityManager();
    public HashMap<UUID, MaSuitePlayer> players = new HashMap<>();

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
        return player;
    }
}
