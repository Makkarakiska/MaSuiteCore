package fi.matiaspaavilainen.masuitecore.bukkit.chat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
     * Sends message to {@link Player}
     *
     * @param p       target player
     * @param message message to send
     */
    public void sendMessage(Player p, String message) {
        if (Bukkit.getVersion().contains("1.8")) {
            p.sendMessage(new TextComponent(colorize(message)).toLegacyText());
        } else {
            p.spigot().sendMessage(TextComponent.fromLegacyText(colorize(message)));
        }

    }

    /**
     * Sends message to {@link CommandSender}
     *
     * @param cs      target CommandSender
     * @param message message to send
     */
    public void sendMessage(CommandSender cs, String message) {
        if (Bukkit.getVersion().contains("1.8")) {
            cs.sendMessage(new TextComponent(colorize(message)).toLegacyText());
        } else {
            cs.spigot().sendMessage(TextComponent.fromLegacyText(colorize(message)));
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
