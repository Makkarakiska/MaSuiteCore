package fi.matiaspaavilainen.masuitecore.bukkit.commands;

import fi.matiaspaavilainen.masuitecore.bukkit.MaSuiteCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class PlayerTabCompleter implements TabCompleter {


    private int position;

    public PlayerTabCompleter(int position) {
        this.position = position;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return new ArrayList<>();
        }

        if (args.length == position) {
            return StringUtil.copyPartialMatches(args[0], MaSuiteCore.onlinePlayers, new ArrayList<>());
        }
        return new ArrayList<>(MaSuiteCore.onlinePlayers);
    }
}
