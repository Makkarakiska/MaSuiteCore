package dev.masa.masuitecore.common.interfaces;

import dev.masa.masuitecore.bungee.services.PlayerService;
import dev.masa.masuitecore.bungee.services.TeleportService;
import dev.masa.masuitecore.common.services.AbstractWarmupService;
import dev.masa.masuitecore.common.services.DatabaseService;
import dev.masa.masuitecore.common.services.CooldownService;
import lombok.NonNull;

public interface IMaSuiteCoreServerAPI<T> {

    /**
     * Get MaSuiteCore
     *
     * @return {@link PlayerService}
     */
    public @NonNull T getCore();

    /**
     * Get {@link DatabaseService}
     *
     * @return {@link DatabaseService}
     */

    public @NonNull AbstractWarmupService getWarmupService();

    /**
     * Get {@link CooldownService}
     *
     * @return {@link TeleportService}
     */
    public @NonNull CooldownService getCooldownService();
}
