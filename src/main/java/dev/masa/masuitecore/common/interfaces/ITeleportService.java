package dev.masa.masuitecore.common.interfaces;

import dev.masa.masuitecore.common.objects.Location;

import java.util.function.Consumer;

public interface ITeleportService<P> {

    void teleportPlayerToLocation(P player, Location location, Consumer<Boolean> callback);

    void teleportPlayerToPlayer(P player, P target, Consumer<Boolean> callback);
}
