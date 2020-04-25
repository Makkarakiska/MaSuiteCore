package dev.masa.masuitecore.core.adapters;


import dev.masa.masuitecore.core.objects.Location;
import org.bukkit.Bukkit;

public class BukkitAdapter {

    /**
     * Get {@link org.bukkit.Location} from @{@link Location}
     *
     * @param loc location to use
     * @return new {@link org.bukkit.Location}
     */
    public static org.bukkit.Location adapt(Location loc) {
        org.bukkit.Location location;
        if (loc.getPitch() != null) {
            location = new org.bukkit.Location(Bukkit.getWorld(loc.getWorld()), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        } else {
            location = new org.bukkit.Location(Bukkit.getWorld(loc.getWorld()), loc.getX(), loc.getY(), loc.getZ());
        }
        return location;
    }

    /**
     * Get @{@link Location} from {@link org.bukkit.Location}
     *
     * @param loc location to use
     * @return new @{@link Location}
     */
    public static Location adapt(org.bukkit.Location loc) {
        return new Location(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }
}
