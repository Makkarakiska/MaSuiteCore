package fi.matiaspaavilainen.masuitecore.core.api;

import fi.matiaspaavilainen.masuitecore.bungee.MaSuiteCore;
import fi.matiaspaavilainen.masuitecore.core.services.PlayerService;

public class MaSuiteCoreAPI {

    public static MaSuiteCore getCore() {
        return MaSuiteCore.getInstance();
    }

    public static PlayerService getPlayerService() {
        return MaSuiteCore.getInstance().playerService;
    }
}
