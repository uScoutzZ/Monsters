package net.animalshomeland.monsters.game;

import lombok.Getter;
import lombok.Setter;
import net.animalshomeland.gameapi.util.FileUtilities;
import net.animalshomeland.monsters.Monsters;
import net.animalshomeland.monsters.utilities.LocationManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMap {

    @Getter
    private String mapName;
    @Getter
    private FileConfiguration mapConfig;
    @Getter
    private File configFile;
    @Getter
    private World map;
    @Getter
    private List<Location> monsterSpawns;
    @Getter
    private List<Block> minefields, xpBottleChest, placed;
    @Getter @Setter
    private Location spawn;

    public GameMap() {
        File maps = new File(Monsters.getInstance().getPath() + "/maps/");
        File world = maps.listFiles()[new Random().nextInt(maps.listFiles().length)];
        mapName = world.getName();
        FileUtilities.copyFolder(world, new File(Bukkit.getWorlds().get(0).getWorldFolder().getParentFile().getAbsolutePath() + "/" + mapName));

        File configFile = new File(world.getAbsolutePath() + "/config.yml");
        if(!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.configFile = configFile;
        mapConfig = YamlConfiguration.loadConfiguration(configFile);
        map = new WorldCreator(mapName).createWorld();
        map.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        map.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        map.setGameRule(GameRule.MOB_GRIEFING, false);
        map.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        map.setGameRule(GameRule.DO_FIRE_TICK, false);
        map.setGameRule(GameRule.FIRE_DAMAGE, false);
        map.setGameRule(GameRule.KEEP_INVENTORY, true);
        Bukkit.getWorlds().get(0).setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        monsterSpawns = new ArrayList<>();
        minefields = new ArrayList<>();
        xpBottleChest = new ArrayList<>();
        placed = new ArrayList<>();
    }

    public void init() {
        for (String key : mapConfig.getKeys(true)) {
            if (key.startsWith("mobspawn_") && !key.contains(".")) {
                Location location = Monsters.getInstance().getLocationManager().getLocation(key);
                monsterSpawns.add(location);
            } else if(key.startsWith("enchanter_") && !key.contains(".")){
                List<Location> bookshelfs = new ArrayList<>();
                Location location = Monsters.getInstance().getLocationManager().getLocation(key);

                bookshelfs.add(location.clone().add(-2, 0, -2));
                bookshelfs.add(location.clone().add(0, 0, -2));
                bookshelfs.add(location.clone().add(2, 0, -2));
                bookshelfs.add(location.clone().add(-2, 0, 1));
                bookshelfs.add(location.clone().add(2, 0, 0));
                bookshelfs.add(location.clone().add(-2, 0, 0));
                bookshelfs.add(location.clone().add(2, 0, 1));
                bookshelfs.add(location.clone().add(-1, 0, -2));
                bookshelfs.add(location.clone().add(-2, 0, 1));
                bookshelfs.add(location.clone().add(1, 0, -2));
                bookshelfs.add(location.clone().add(-2, 0, -1));
                bookshelfs.add(location.clone().add(2, 0, -1));
                bookshelfs.add(location.clone().add(-2, 0, 1));
                location.clone().add(2, 0, 2).getBlock().setType(Material.CHEST);
                xpBottleChest.add(location.clone().add(2, 0, 2).getBlock());
                location.getBlock().setType(Material.ENCHANTING_TABLE);
                for(Location bookshelfLocation : bookshelfs) {
                    bookshelfLocation.getBlock().setType(Material.BOOKSHELF);
                    double random = new Random().nextDouble();
                    if(random > 0.4) {
                        bookshelfLocation.add(0, 1, 0).getBlock().setType(Material.BOOKSHELF);
                        if(random > 0.75) {
                            bookshelfLocation.add(0, 1, 0).getBlock().setType(Material.BOOKSHELF);
                        }
                    }
                }
            }
        }
    }

    public Location getRandomSpawn() {
        return monsterSpawns.get(new Random().nextInt(monsterSpawns.size()));
    }

    public void changeTime() {
        int time;
        if(Monsters.getInstance().getGame().getGameState() == GameState.WAVE_RUNNING) {
            time = 13000;
        } else {
            time = 6000;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if((time == 13000 && time <= map.getTime()) || (time == 6000 && time >= map.getTime())) {
                    map.setTime(time);
                    cancel();
                    return;
                }
                if(time > map.getTime()) {
                    map.setTime(map.getTime()+100);
                } else {
                    map.setTime(map.getTime()-100);
                }
            }
        }.runTaskTimer(Monsters.getInstance(), 0, 1);
    }
}
