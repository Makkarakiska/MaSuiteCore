package fi.matiaspaavilainen.masuitecore.bukkit.commands;

import fi.matiaspaavilainen.masuitecore.bukkit.MaSuiteCore;
import fi.matiaspaavilainen.masuitecore.bukkit.chat.Formator;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;


public class MaSuiteCommand implements CommandExecutor {

    private MaSuiteCore plugin;
    private Formator formator = new Formator();

    public MaSuiteCommand(MaSuiteCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        TextComponent textComponent = new TextComponent();
        textComponent.addExtra("&9MaSuiteCore &8v" + plugin.getDescription().getVersion());
        PluginManager pm = plugin.getServer().getPluginManager();

        formator.sendMessage(sender, " &8&lMaSuiteCore &9v" + plugin.getDescription().getVersion());
        if (pm.getPlugin("MaSuiteHomes").isEnabled()) {
            formator.sendMessage(sender, " &8&lMaSuiteHomes &9v" + pm.getPlugin("MaSuiteHomes").getDescription().getVersion());
        }
        if (pm.getPlugin("MaSuiteChat").isEnabled()) {
            formator.sendMessage(sender, " &8&lMaSuiteChat &9v" + pm.getPlugin("MaSuiteChat").getDescription().getVersion());
        }
        if (pm.getPlugin("MaSuiteWarps").isEnabled()) {
            formator.sendMessage(sender, " &8&lMaSuiteWarps &9v" + pm.getPlugin("MaSuiteWarps").getDescription().getVersion());
        }
        if (pm.getPlugin("MaSuiteTeleports").isEnabled()) {
            formator.sendMessage(sender, " &8&lMaSuiteTeleports &9v" + pm.getPlugin("MaSuiteTeleports").getDescription().getVersion());
        }
        if (pm.getPlugin("MaSuitePortals").isEnabled()) {
            formator.sendMessage(sender, " &8&lMaSuitePortals &9v" + pm.getPlugin("MaSuitePortals").getDescription().getVersion());
        }
        formator.sendMessage(sender, " &8&lSupport link &9https://discord.gg/sZZG6Jq");
        return true;
    }
}
