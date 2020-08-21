package dev.masa.masuitecore.bungee.events;

import dev.masa.masuitecore.common.models.MaSuitePlayer;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Event;

public class MaSuitePlayerCreationEvent extends Event {

    @Getter
    private MaSuitePlayer player;

    public MaSuitePlayerCreationEvent(MaSuitePlayer player) {
        this.player = player;
    }

}
