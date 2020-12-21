package dev.masa.masuitecore.bungee;

import dev.masa.masuitecore.bungee.chat.Formator;
import dev.masa.masuitecore.common.channels.BungeePluginChannel;
import dev.masa.masuitecore.common.objects.Location;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.UUID;

public class Utils {

    private final Formator formator = new Formator();


    /**
     * Check player's status
     *
     * @param target check's target
     * @return Returns boolean
     */
    public boolean isOnline(ProxiedPlayer target) {
        return target != null;
    }

    /**
     * Check player's status
     *
     * @param target check's target
     * @param sender check's requester
     * @return Returns boolean
     */
    public boolean isOnline(ProxiedPlayer target, ProxiedPlayer sender) {
        if (target == null) {

            formator.sendMessage(sender, MaSuiteCore.getInstance().getMessages().getPlayerNotOnline());
            return false;
        }
        return true;
    }

    /**
     * Broadcast a message
     *
     * @param message message to broadcast
     */
    public void broadcast(String message) {
        ProxyServer.getInstance().broadcast(new TextComponent(formator.colorize(message)));
    }

    /**
     * Apply cooldown for player
     * @param plugin plugin to use
     * @param uuid uuid of the player
     * @param type type of the cooldown
     */
    public void applyCooldown(Plugin plugin, UUID uuid, String type) {
        new BungeePluginChannel(plugin, plugin.getProxy().getPlayer(uuid).getServer().getInfo(), "MaSuiteCore", "ApplyCooldown", type, uuid.toString()).send();
    }

    /**
     * Play sound to user
     *
     * @param plugin   {@link Plugin} to use
     * @param location {@link Location} where to play the sound
     * @param player   {@link ProxiedPlayer} who will hear the sound
     * @param sound    sound to use
     * @param volume   volume of the sound
     * @param pitch    pitch of the sound
     */
    public void playSound(Plugin plugin, Location location, ProxiedPlayer player, String sound, float volume, float pitch) {
        new BungeePluginChannel(plugin, player.getServer().getInfo(), "MaSuiteCore", "PlaySound",
                player.getUniqueId().toString(), location.serialize(), sound, volume, pitch).send();
    }
}
