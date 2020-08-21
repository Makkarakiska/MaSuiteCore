package dev.masa.masuitecore.bungee.events;

import dev.masa.masuitecore.common.models.MaSuitePlayer;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

public class MaSuitePlayerUpdateEvent extends Event implements Cancellable {

    private boolean isCancelled;
    @Getter
    private MaSuitePlayer player;

    public MaSuitePlayerUpdateEvent(MaSuitePlayer player) {
        this.isCancelled = false;
        this.player = player;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

}
