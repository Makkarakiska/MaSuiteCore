package dev.masa.masuitecore.bukkit.api;

import dev.masa.masuitecore.bukkit.MaSuiteCore;
import dev.masa.masuitecore.bukkit.services.WarmupService;
import dev.masa.masuitecore.common.interfaces.IMaSuiteCoreServerAPI;
import dev.masa.masuitecore.common.services.CooldownService;
import lombok.NonNull;

public class MaSuiteCoreServerAPI implements IMaSuiteCoreServerAPI<MaSuiteCore> {

    public MaSuiteCore getCore() {
        return MaSuiteCore.getInstance();
    }

    @Override
    public @NonNull WarmupService getWarmupService() {
        return MaSuiteCore.getWarmupService();
    }

    @Override
    public @NonNull CooldownService getCooldownService() {
        return null;
    }
}
