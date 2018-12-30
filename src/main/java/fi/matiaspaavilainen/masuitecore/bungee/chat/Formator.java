package fi.matiaspaavilainen.masuitecore.bungee.chat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Formator {

    /**
     * Colorize String
     * @param string to color
     * @return colorized string
     */
    public String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * Sends message to {@link ProxiedPlayer}
     * @param p target player
     * @param message message to send
     */
    public void sendMessage(ProxiedPlayer p, String message) {
        p.sendMessage(MDChat.getMessageFromString(colorize(message)));
    }
}
