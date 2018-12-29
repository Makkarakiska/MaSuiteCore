package fi.matiaspaavilainen.masuitecore;

import fi.matiaspaavilainen.masuitecore.chat.Formator;
import fi.matiaspaavilainen.masuitecore.core.configuration.BungeeConfiguration;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Debugger {

    private BungeeConfiguration configuration = new BungeeConfiguration();
    private Formator formator = new Formator();
    public void sendMessage(String message){
        if(configuration.load(null,"config.yml").getBoolean("debug")){
            System.out.println(formator.colorize(message));
        }
    }

    public void sendMessage(ProxiedPlayer player, String message){
        if(configuration.load(null,"config.yml").getBoolean("debug")){
            formator.sendMessage(player, message);
        }
    }
}
