package fi.matiaspaavilainen.masuitecore.core.utils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerFinder {

    public ProxiedPlayer get(String name) {
        try {
            if (ProxyServer.getInstance().getPlayers().stream().anyMatch(proxiedPlayer -> proxiedPlayer.getName().toLowerCase().startsWith(name.toLowerCase()))) {
                return ProxyServer.getInstance().getPlayers().stream().filter(proxiedPlayer ->
                        proxiedPlayer.getName().toLowerCase().startsWith(name.toLowerCase()) ||
                                proxiedPlayer.getName().equalsIgnoreCase(name))
                        .findFirst().get();
            } else {
                return null;
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}

