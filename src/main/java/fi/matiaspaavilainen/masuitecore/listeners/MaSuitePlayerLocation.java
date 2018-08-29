package fi.matiaspaavilainen.masuitecore.listeners;

import fi.matiaspaavilainen.masuitecore.managers.Location;
import fi.matiaspaavilainen.masuitecore.managers.MaSuitePlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class MaSuitePlayerLocation implements Listener {

    public static HashMap<UUID, Location> locations = new HashMap<>();
    @EventHandler
    public void getLocation(PluginMessageEvent e) throws IOException{
        if(e.getTag().equals("BungeeCord")){
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
            String subchannel = in.readUTF();
            if(subchannel.equals("MaSuitePlayerLocation")){
                locations.put(UUID.fromString(in.readUTF()),  new Location(in.readUTF(), in.readDouble(), in.readDouble(), in.readDouble(), in.readFloat(), in.readFloat()));
            }
        }
    }
}
