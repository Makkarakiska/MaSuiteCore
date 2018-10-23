package fi.matiaspaavilainen.masuitecore.chat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.chat.ComponentSerializer;

public class Formator {

    public String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public void sendMessage(ProxiedPlayer p, String message) {
        p.sendMessage(ComponentSerializer.parse(JSONUtils.toJSON(message)));
    }
}
