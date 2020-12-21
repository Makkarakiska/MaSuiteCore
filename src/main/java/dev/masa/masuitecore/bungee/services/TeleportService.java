package dev.masa.masuitecore.bungee.services;

import dev.masa.masuitecore.bungee.MaSuiteCore;
import dev.masa.masuitecore.common.interfaces.ITeleportService;
import dev.masa.masuitecore.common.objects.Location;
import dev.masa.masuitecore.common.channels.BungeePluginChannel;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@AllArgsConstructor
public class TeleportService implements ITeleportService<ProxiedPlayer> {

    private MaSuiteCore plugin;

    @Override
    public void teleportPlayerToLocation(ProxiedPlayer player, Location location, Consumer<Boolean> callback) {
        ServerInfo destinationServer = this.plugin.getProxy().getServerInfo(location.getServer());
        BungeePluginChannel bpc = new BungeePluginChannel(plugin, destinationServer, "MaSuiteCore", "PlayerToLocation", player.getName(), location.serialize());

        if (!player.getServer().getInfo().equals(destinationServer)) {
            plugin.getProxy().getScheduler().runAsync(plugin, () -> player.connect(destinationServer, (connected, throwable) -> {
                if (connected) {
                    plugin.getProxy().getScheduler().schedule(plugin, () -> {
                        bpc.send();
                        callback.accept(true);
                    }, plugin.getConfig().getTeleportationDelay(), TimeUnit.MILLISECONDS);
                } else {
                    callback.accept(false);
                }
            }));
        } else {
            bpc.send();
            callback.accept(true);
        }
    }

    @Override
    public void teleportPlayerToPlayer(ProxiedPlayer player, ProxiedPlayer target, Consumer<Boolean> callback) {
        BungeePluginChannel bpc = new BungeePluginChannel(plugin,
                target.getServer().getInfo(),
                "MaSuiteTeleports",
                "PlayerToPlayer",
                player.getName(),
                target.getName()
        );
        if (!player.getServer().getInfo().getName().equals(target.getServer().getInfo().getName())) {
            plugin.getProxy().getScheduler().runAsync(plugin, () -> player.connect(target.getServer().getInfo(), (connected, throwable) -> {
                if (connected) {
                    plugin.getProxy().getScheduler().schedule(plugin, () -> {
                        bpc.send();
                        callback.accept(true);
                    }, plugin.getConfig().getTeleportationDelay(), TimeUnit.MILLISECONDS);
                } else {
                    callback.accept(false);
                }
            }));
        } else {
            bpc.send();
            callback.accept(true);
        }
    }
}
