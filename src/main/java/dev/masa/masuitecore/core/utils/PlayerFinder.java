package dev.masa.masuitecore.core.utils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerFinder {

    public ProxiedPlayer get(String name) {
        return ProxyServer.getInstance().matchPlayer(name).stream().findFirst().orElse(null);
    }
}

