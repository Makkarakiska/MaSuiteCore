package fi.matiaspaavilainen.masuitecore.chat;

import net.md_5.bungee.api.ChatColor;

public class Formator {

    public String colorize(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
