package dev.masa.masuitecore.bungee.listeners;

import dev.masa.masuitecore.bungee.MaSuiteCore;
import dev.masa.masuitecore.common.models.MaSuitePlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.logging.Level;

public class PlayerLoginEvent implements Listener {

    private MaSuiteCore plugin;

    public PlayerLoginEvent(MaSuiteCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(LoginEvent event) {
        event.registerIntent(plugin);
        plugin.getPlayerService().getPlayer(event.getConnection().getUniqueId(), playerQuery -> {
            MaSuitePlayer player;

            if (playerQuery.isPresent()) {
                player = playerQuery.get();
                if (!event.getConnection().getName().equals(player.getUsername())) {
                    player.setUsername(event.getConnection().getName());
                }

            }

            if (!playerQuery.isPresent()) {
                player = new MaSuitePlayer();
                player.setUsername(event.getConnection().getName());
                player.setUniqueId(event.getConnection().getUniqueId());
                player.setLastLogin(System.currentTimeMillis() / 1000);
                player.setFirstLogin(System.currentTimeMillis() / 1000);

                MaSuitePlayer finalPlayer = player;
                plugin.getPlayerService().create(player, success -> {
                    if (success) {
                        plugin.getPlayerService().addPlayerToServers(finalPlayer);
                        return;
                    }
                    event.getConnection().disconnect(new TextComponent(ChatColor.RED + "There was an error while generating your MaSuite profile. Please try again later."));
                    this.plugin.getLogger().log(Level.WARNING, "Disconnected " + event.getConnection().getName() + " because their MaSuite profile could not be created!");
                });
            }
            event.completeIntent(plugin);
        });
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        plugin.getPlayerService().getPlayer(event.getPlayer().getUniqueId(), playerQuery -> {
            MaSuitePlayer player;

            if (!playerQuery.isPresent()) {
                event.getPlayer().disconnect(new TextComponent(ChatColor.RED + "There was an error while loading your MaSuite profile. Please try again later."));
                this.plugin.getLogger().log(Level.WARNING, "Disconnected " + event.getPlayer().getName() + " because their MaSuite profile could not be loaded!");
                return;
            }
            player = playerQuery.get();
            if (player.getNickname() != null) {
                event.getPlayer().setDisplayName(player.getNickname());
            }
            plugin.getPlayerService().addPlayerToServers(player);
        });
    }
}
