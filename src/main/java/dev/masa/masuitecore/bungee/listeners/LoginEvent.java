package dev.masa.masuitecore.bungee.listeners;

import dev.masa.masuitecore.bungee.MaSuiteCore;
import dev.masa.masuitecore.common.models.MaSuitePlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.logging.Level;

public class LoginEvent implements Listener {

    private MaSuiteCore plugin;

    public LoginEvent(MaSuiteCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(PostLoginEvent event) {
        plugin.getPlayerService().get(event.getPlayer().getUniqueId(), playerQuery -> {
            MaSuitePlayer player;

            if (playerQuery.isPresent()) {
                player = playerQuery.get();
                if (player.getNickname() != null) {
                    event.getPlayer().setDisplayName(player.getNickname());
                }
                if (!event.getPlayer().getName().equals(player.getUsername())) {
                    player.setUsername(event.getPlayer().getName());
                }
                plugin.getPlayerService().addPlayerToServers(player);
            }

            if (!playerQuery.isPresent()) {
                player = new MaSuitePlayer();
                player.setUsername(event.getPlayer().getName());
                player.setUniqueId(event.getPlayer().getUniqueId());
                player.setLastLogin(System.currentTimeMillis() / 1000);
                player.setFirstLogin(System.currentTimeMillis() / 1000);

                MaSuitePlayer finalPlayer = player;
                plugin.getPlayerService().create(player, success -> {
                    if (success) {
                        plugin.getPlayerService().addPlayerToServers(finalPlayer);
                        return;
                    }
                    event.getPlayer().disconnect(new TextComponent(ChatColor.RED + "There was an error while generating your MaSuite profile. Please try again later."));
                    this.plugin.getLogger().log(Level.WARNING, "Disconnected " + event.getPlayer().getName() + " because their MaSuite profile could not be loaded!");
                });
            }
        });
    }
}
