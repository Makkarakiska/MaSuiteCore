package fi.matiaspaavilainen.masuitecore.listeners;

import fi.matiaspaavilainen.masuitecore.Debugger;
import fi.matiaspaavilainen.masuitecore.managers.Group;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class MaSuitePlayerGroup implements Listener {

    public static HashMap<UUID, Group> groups = new HashMap<>();
    private Debugger debugger = new Debugger();
    @EventHandler
    public void getGroup(PluginMessageEvent e) throws IOException {
        if(e.getTag().equals("BungeeCord")){
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
            String subchannel = in.readUTF();
            if(subchannel.equals("MaSuitePlayerGroup")){
                debugger.sendMessage("[MaSuiteCore] [MaSuitePlayerGroup] group received");
                groups.put(UUID.fromString(in.readUTF()), new Group(in.readUTF(), in.readUTF()));
                debugger.sendMessage("[MaSuiteCore] [MaSuitePlayerGroup] group saved to cache");
            }
        }
    }
}

