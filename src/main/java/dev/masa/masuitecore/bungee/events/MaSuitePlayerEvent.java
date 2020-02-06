package dev.masa.masuitecore.bungee.events;

import dev.masa.masuitecore.core.models.MaSuitePlayer;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

public class MaSuitePlayerEvent extends Event {

    static class Create extends MaSuitePlayerEvent implements Cancellable {

        private boolean isCancelled;
        private MaSuitePlayer player;

        public Create(MaSuitePlayer player) {
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

    static class Update extends MaSuitePlayerEvent implements Cancellable {

        private boolean isCancelled;
        private MaSuitePlayer player;

        public Update(MaSuitePlayer player) {
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

    static class Delete extends MaSuitePlayerEvent implements Cancellable {

        private boolean isCancelled;
        private MaSuitePlayer player;

        public Delete(MaSuitePlayer player) {
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


}
