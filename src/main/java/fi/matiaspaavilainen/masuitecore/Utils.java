package fi.matiaspaavilainen.masuitecore;

import fi.matiaspaavilainen.masuitecore.chat.Formator;
import fi.matiaspaavilainen.masuitecore.config.Configuration;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Utils {

    private Configuration config = new Configuration();
    private Formator formator = new Formator();


    /**
     * Check player's status
     * @param target check's target
     * @return Returns boolean
     */
    public boolean isOnline(ProxiedPlayer target) {
        return target != null;
    }

    /**
     * Check player's status
     * @param target check's target
     * @param sender check's requester
     * @return Returns boolean
     */
    public boolean isOnline(ProxiedPlayer target, ProxiedPlayer sender) {
        if (target == null) {
            formator.sendMessage(sender, config.load(null, "messages.yml").getString("player-not-online"));
            return false;
        }
        return true;
    }
}
