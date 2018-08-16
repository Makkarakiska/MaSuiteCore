package fi.matiaspaavilainen.masuitecore.chat;

import net.md_5.bungee.api.ChatColor;

public class Colorize {

    // String version
    public static String colorize(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
