package fi.matiaspaavilainen.masuitecore.chat;

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
     * Sends message to player
     * @param p Target player
     * @param message message to send
     */
    public void sendMessage(ProxiedPlayer p, String message) {
        p.sendMessage(MDChat.getMessageFromString(colorize(message)));
    }
}
