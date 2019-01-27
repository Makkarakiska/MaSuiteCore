package fi.matiaspaavilainen.masuitecore.bungee.chat;

import fi.matiaspaavilainen.masuitecore.bungee.Utils;
import net.md_5.bungee.api.ChatColor;
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
     * @param p       target player
     * @param message message to send
     */
    public void sendMessage(ProxiedPlayer p, String message) {
        if (new Utils().isOnline(p)) {
            p.sendMessage(MDChat.getMessageFromString(colorize(message)));
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
