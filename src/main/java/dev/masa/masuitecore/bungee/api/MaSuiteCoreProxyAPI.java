package dev.masa.masuitecore.bungee.api;

import dev.masa.masuitecore.bungee.MaSuiteCore;
import dev.masa.masuitecore.bungee.services.PlayerService;
import dev.masa.masuitecore.bungee.services.TeleportService;
import dev.masa.masuitecore.common.interfaces.IMaSuiteCoreProxyAPI;
import dev.masa.masuitecore.common.services.DatabaseService;
import lombok.NonNull;

public class MaSuiteCoreProxyAPI implements IMaSuiteCoreProxyAPI<MaSuiteCore> {

    public MaSuiteCore getCore() {
        return MaSuiteCore.getInstance();
    }

    public static @NonNull PlayerService getPlayerService() {
        return MaSuiteCore.getInstance().getPlayerService();
    }

    public static @NonNull DatabaseService getDatabaseService() {
        return MaSuiteCore.getInstance().getDatabaseService();
    }

    public static @NonNull TeleportService getTeleportService() {
        return MaSuiteCore.getInstance().getTeleportService();
    }
}
