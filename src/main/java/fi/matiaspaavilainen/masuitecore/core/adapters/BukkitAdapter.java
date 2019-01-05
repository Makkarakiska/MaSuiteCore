package fi.matiaspaavilainen.masuitecore.core.adapters;


import fi.matiaspaavilainen.masuitecore.core.objects.Location;
import org.bukkit.Bukkit;

public class BukkitAdapter {

    public static org.bukkit.Location adapt(Location loc) {
        org.bukkit.Location location;
        if (loc.getPitch() != null) {
            location = new org.bukkit.Location(Bukkit.getWorld(loc.getWorld()), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        } else {
            location = new org.bukkit.Location(Bukkit.getWorld(loc.getWorld()), loc.getX(), loc.getY(), loc.getZ());
        }
        return location;
    }

    public static Location adapt(org.bukkit.Location loc) {
        return new Location(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }
}
