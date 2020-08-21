package dev.masa.masuitecore.core.api;

import dev.masa.masuitecore.bukkit.MaSuiteCore;
import dev.masa.masuitecore.core.services.CooldownService;
import dev.masa.masuitecore.bukkit.services.WarmupService;

@Deprecated
public class MaSuiteCoreBukkitAPI {

    /**
     * Get the MaSuiteCore's Bukkit instance
     * @return returns MaSuiteCore Bukkit instance
     */
    public static MaSuiteCore getCore() {
        return MaSuiteCore.getInstance();
    }

    /**
     * Get the warmup service
     * @return returns warmup service
     */
    public WarmupService getWarmupService() {
        return MaSuiteCore.warmupService;
    }


    /**
     * Get the cooldown service
     * @return returns cooldown service
     */
    public CooldownService getCooldownService() {
        return MaSuiteCore.cooldownService;
    }
}
