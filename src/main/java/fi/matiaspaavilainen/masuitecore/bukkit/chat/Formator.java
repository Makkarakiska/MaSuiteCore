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
     * @param player  target player
     * @param message message to send
     */
    public void sendMessage(Player player, String message) {
        if (player != null) {
            if (Bukkit.getVersion().contains("1.8")) {
                player.sendMessage(new TextComponent(colorize(message)).toLegacyText());
            } else {
                player.spigot().sendMessage(TextComponent.fromLegacyText(colorize(message)));
            }
        }
    }

    /**
     * Sends message to {@link CommandSender}
     *
     * @param cs      target CommandSender
     * @param message message to send
     */
    public void sendMessage(CommandSender cs, String message) {
        if (cs != null) {
            if (Bukkit.getVersion().contains("1.8") || Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11")) {
                cs.sendMessage(new TextComponent(colorize(message)).toLegacyText());
            } else {
                cs.spigot().sendMessage(TextComponent.fromLegacyText(colorize(message)));
            }
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
