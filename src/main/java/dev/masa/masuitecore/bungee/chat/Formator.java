package dev.masa.masuitecore.bungee.chat;

import dev.masa.masuitecore.bungee.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Formator {

    /**
     * Colorize String
     *
     * @param string to color
     * @return colorized string
     */
    public String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    /**
     * Sends message to {@link ProxiedPlayer}
     *
     * @param player  target player
     * @param message message to send
     */
    public void sendMessage(ProxiedPlayer player, String message) {
        if(message.isEmpty()) return;
        if (new Utils().isOnline(player)) {
            player.sendMessage(MDChat.getMessageFromString(colorize(message)));
        }
    }

    /**
     * Send {@link BaseComponent} to player
     *
     * @param player    player who will receive the message
     * @param component component to send
     */
    public void sendMessage(ProxiedPlayer player, BaseComponent component) {
        if (new Utils().isOnline(player)) {
            player.sendMessage(component);
        }
    }

    /**
     * Create formatted timestamp
     *
     * @param date to format
     * @return formatted date
     */
    public String getDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }
}
