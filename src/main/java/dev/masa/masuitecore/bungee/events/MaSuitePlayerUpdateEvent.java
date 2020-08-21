package dev.masa.masuitecore.bungee.events;

import dev.masa.masuitecore.common.models.MaSuitePlayer;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;

public class MaSuitePlayerUpdateEvent extends Event {

    @Getter
    private MaSuitePlayer player;

    public MaSuitePlayerUpdateEvent(MaSuitePlayer player) {
        this.player = player;
    }

}
