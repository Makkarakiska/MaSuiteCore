package fi.matiaspaavilainen.masuitecore.core.utils;

import co.aikar.commands.PaperCommandManager;
import fi.matiaspaavilainen.masuitecore.bukkit.MaSuiteCore;

public class CommandManagerUtil {

    /**
     * Registers command completion for {@link PaperCommandManager}
     * @param manager manager to use
     */
    public static void registerMaSuitePlayerCommandCompletion(PaperCommandManager manager) {
        manager.getCommandCompletions().registerCompletion("masuite_players", c -> MaSuiteCore.onlinePlayers);
    }
}
