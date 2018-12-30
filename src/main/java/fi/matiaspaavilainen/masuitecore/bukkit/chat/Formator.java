package fi.matiaspaavilainen.masuitecore.bukkit.chat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

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
     * Sends message to {@link Player}
     * @param p target player
     * @param message message to send
     */
    public void sendMessage(Player p, String message) {
        p.spigot().sendMessage(TextComponent.fromLegacyText(colorize(message)));
    }
}
