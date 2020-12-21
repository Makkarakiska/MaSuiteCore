package dev.masa.masuitecore.common.interfaces;

import dev.masa.masuitecore.bungee.services.PlayerService;
import dev.masa.masuitecore.bungee.services.TeleportService;
import dev.masa.masuitecore.common.services.DatabaseService;
import lombok.NonNull;

public interface IMaSuiteCoreProxyAPI<T> {

    /**
     * Get {@link PlayerService}
     *
     * @return {@link PlayerService}
     */
    public static @NonNull PlayerService getPlayerService() {
        return null;
    }

    /**
     * Get {@link DatabaseService}
     *
     * @return {@link DatabaseService}
     */

    public static @NonNull DatabaseService getDatabaseService() {
        return null;
    }

    /**
     * Get {@link TeleportService}
     *
     * @return {@link TeleportService}
     */
    public static @NonNull TeleportService getTeleportService() {
        return null;
    }
}
