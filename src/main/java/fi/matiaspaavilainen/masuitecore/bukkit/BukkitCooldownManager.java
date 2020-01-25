package fi.matiaspaavilainen.masuitecore.bukkit;

import java.time.Instant;
import java.util.HashMap;
import java.util.UUID;

public class BukkitCooldownManager {

    public HashMap<String, HashMap<UUID, Long>> cooldowns = new HashMap<>();
    public HashMap<String, Integer> cooldownLengths = new HashMap<>();


    /**
     * Apply cooldown for a user
     *
     * @param type type of the cooldown
     * @param uuid uuid of the user
     */
    public void applyCooldown(String type, UUID uuid) {
        cooldowns.computeIfAbsent(type, k -> new HashMap<>());

        HashMap<UUID, Long> cooldown = new HashMap<>();
        cooldown.put(uuid, Instant.now().getEpochSecond());
        cooldowns.put(type, cooldown);
    }

    /**
     * Remove cooldown from a user
     *
     * @param type type of the cooldown
     * @param uuid uuid of the user
     */
    public void removeCooldown(String type, UUID uuid) {
        cooldowns.computeIfAbsent(type, k -> new HashMap<>());
        cooldowns.get(type).remove(uuid);
    }

    /**
     * Check if player has cooldown
     *
     * @param type type of the cooldown
     * @param uuid uuid of the user
     * @return returns boolean
     */
    public boolean hasCooldown(String type, UUID uuid) {
        if (cooldowns.get(type) == null) return true;

        Long timeLeft = cooldowns.get(type).get(uuid);
        if (timeLeft == null) return true;

        if (Instant.now().getEpochSecond() - timeLeft > cooldownLengths.get(type)) {
            cooldowns.get(type).remove(uuid);
            return false;
        }

        return true;
    }

    /**
     * Get cooldown of the player
     *
     * @param type type of the cooldown
     * @param uuid uuid of the user
     * @return returns cooldown
     */
    public Long getCooldown(String type, UUID uuid) {
        cooldowns.computeIfAbsent(type, k -> new HashMap<>());
        return cooldowns.get(type).get(uuid);
    }

    /**
     * Add cooldown length for specific type
     *
     * @param type   type of the cooldown
     * @param length length of the cooldown
     */
    public void addCooldownLength(String type, int length) {
        cooldownLengths.put(type, length);
    }

}
