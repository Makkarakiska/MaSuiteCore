package fi.matiaspaavilainen.masuitecore.bungee;

import fi.matiaspaavilainen.masuitecore.bungee.chat.Formator;
import fi.matiaspaavilainen.masuitecore.core.configuration.BungeeConfiguration;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Utils {

    private BungeeConfiguration config = new BungeeConfiguration();
    private Formator formator = new Formator();


    /**
     * Check player's status
     *
     * @param target check's target
     * @return Returns boolean
     */
    public boolean isOnline(ProxiedPlayer target) {
        return target != null;
    }

    /**
     * Check player's status
     *
     * @param target check's target
     * @param sender check's requester
     * @return Returns boolean
     */
    public boolean isOnline(ProxiedPlayer target, ProxiedPlayer sender) {
        if (target == null) {
            formator.sendMessage(sender, config.load(null, "messages.yml").getString("player-not-online"));
            return false;
        }
        return true;
    }

    /**
     * Send debug message to console
     *
     * @param message the message to send
     */
    public void sendMessage(String message) {
        if (config.load(null, "config.yml").getBoolean("debug")) {
            System.out.println(formator.colorize(message));
        }
    }

    /**
     * Send debug message to {@link ProxiedPlayer}
     *
     * @param player  player to send the message
     * @param message the message to send
     */
    public void sendMessage(ProxiedPlayer player, String message) {
        if (config.load(null, "config.yml").getBoolean("debug")) {
            formator.sendMessage(player, message);
        }
    }

    /**
     * Broadcast a message
     * @param message message to broadcast
     */
    public void broadcast(String message){
        ProxyServer.getInstance().broadcast(new TextComponent(formator.colorize(message)));
    }
}
