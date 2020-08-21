package dev.masa.masuitecore.core.api;

import dev.masa.masuitecore.common.services.DatabaseService;
import dev.masa.masuitecore.bungee.services.PlayerService;
import dev.masa.masuitecore.bungee.MaSuiteCore;

@Deprecated
public class MaSuiteCoreAPI {

    public static MaSuiteCore getCore() {
        return MaSuiteCore.getInstance();
    }

    public PlayerService getPlayerService() {
        return MaSuiteCore.getInstance().getPlayerService();
    }

    public DatabaseService getDatabaseService() {
        return MaSuiteCore.getInstance().getDatabaseService();
    }
}
