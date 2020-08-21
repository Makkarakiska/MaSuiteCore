package dev.masa.masuitecore.bungee.services;

import dev.masa.masuitecore.bungee.MaSuiteCore;
import dev.masa.masuitecore.bungee.events.MaSuitePlayerCreationEvent;
import dev.masa.masuitecore.bungee.events.MaSuitePlayerUpdateEvent;
import dev.masa.masuitecore.common.models.MaSuitePlayer;
import dev.masa.masuitecore.common.services.AbstractDataService;
import dev.masa.masuitecore.core.channels.BungeePluginChannel;
import net.md_5.bungee.api.config.ServerInfo;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

public class PlayerService extends AbstractDataService<UUID, MaSuitePlayer, MaSuiteCore> {

    public PlayerService(MaSuiteCore plugin) {
        super(plugin, MaSuitePlayer.class);
    }

    @Override
    public void create(MaSuitePlayer player, Consumer<Boolean> callback) {
        this.getPlugin().getProxy().getScheduler().runAsync(this.getPlugin(), () -> {
            try {
                this.getDao().create(player);
                this.addToCache(player.getUniqueId(), player);
                callback.accept(true);
                this.getPlugin().getProxy().getPluginManager().callEvent(new MaSuitePlayerCreationEvent(player));
            } catch (SQLException e) {
                e.printStackTrace();
                callback.accept(false);
            }
        });
    }

    @Override
    public void update(MaSuitePlayer player, Consumer<Boolean> callback) {
        this.getPlugin().getProxy().getScheduler().runAsync(this.getPlugin(), () -> {
            try {
                this.getDao().update(player);
                this.addToCache(player.getUniqueId(), player);
                callback.accept(true);
                this.getPlugin().getProxy().getPluginManager().callEvent(new MaSuitePlayerUpdateEvent(player));
            } catch (SQLException e) {
                e.printStackTrace();
                callback.accept(false);
            }
        });
    }

    @Override
    public void delete(MaSuitePlayer player, Consumer<Boolean> callback) {
        this.getPlugin().getProxy().getScheduler().runAsync(this.getPlugin(), () -> {
            try {
                this.getDao().delete(player);
                this.removeFromCache(player.getUniqueId());
                callback.accept(true);
            } catch (SQLException e) {
                e.printStackTrace();
                callback.accept(false);
            }
        });
    }

    @Override
    public void getFromDatabase(UUID uuid, Consumer<Optional<MaSuitePlayer>> callback) {
        this.getPlugin().getProxy().getScheduler().runAsync(this.getPlugin(), () -> {
            try {
                callback.accept(Optional.ofNullable(this.getDao().queryForId(uuid)));
            } catch (SQLException e) {
                e.printStackTrace();
                callback.accept(Optional.empty());
            }
        });
    }


    /**
     * Remove {@link MaSuitePlayer} from servers.
     * @param player player to remove
     */
    public void removePlayerFromServers(MaSuitePlayer player) {
        this.getPlugin().getProxy().getScheduler().runAsync(this.getPlugin(), () -> {
            for (Map.Entry<String, ServerInfo> entry : this.getPlugin().getProxy().getServers().entrySet()) {
                ServerInfo serverInfo = entry.getValue();
                serverInfo.ping((result, error) -> {
                    if (error == null) {
                        new BungeePluginChannel(this.getPlugin(),
                                serverInfo,
                                "MaSuiteCore",
                                "RemovePlayer",
                                player.getUsername()
                        ).send();
                    }
                });
            }
        });
    }

    public void addPlayerToServers(MaSuitePlayer player) {
        this.getPlugin().getProxy().getScheduler().runAsync(this.getPlugin(), () -> {
            for (Map.Entry<String, ServerInfo> entry : this.getPlugin().getProxy().getServers().entrySet()) {
                ServerInfo serverInfo = entry.getValue();
                serverInfo.ping((result, error) -> {
                    if (error == null) {
                        new BungeePluginChannel(this.getPlugin(),
                                serverInfo,
                                "MaSuiteCore",
                                "AddPlayer",
                                player.getUsername()
                        ).send();
                    }
                });
            }
        });
    }
}
