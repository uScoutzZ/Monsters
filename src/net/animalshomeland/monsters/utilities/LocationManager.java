package net.animalshomeland.monsters.utilities;

import net.animalshomeland.monsters.Monsters;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class LocationManager {

    public Location getLocation(String path) {
        Location location;

        FileConfiguration cfg = Monsters.getInstance().getGame().getGameMap().getMapConfig();

        String w = cfg.getString(path + ".world");
        double x = cfg.getDouble(path + ".x");
        double y = cfg.getDouble(path + ".y");
        double z = cfg.getDouble(path + ".z");
        float yaw = (float) cfg.getDouble(path + ".yaw");
        float pitch = (float) cfg.getDouble(path + ".pitch");

        System.out.println("Getting location " + path);
        location = new Location(Bukkit.getWorld(w), x, y, z, yaw, pitch);

        return location;
    }
}
