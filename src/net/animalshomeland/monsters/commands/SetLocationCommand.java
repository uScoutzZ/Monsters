package net.animalshomeland.monsters.commands;

import net.animalshomeland.gameapi.command.Command;
import net.animalshomeland.gameapi.command.RegisterCommand;
import net.animalshomeland.monsters.Monsters;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

@RegisterCommand(name = "setlocation", permission = "monsters.command.setlocation")
public class SetLocationCommand extends Command {

    @Override
    public void onPlayerExecute(Player player, String command, String[] args) {
        if(player.getWorld() != Bukkit.getWorlds().get(0)) {
            File mapConfig = Monsters.getInstance().getGame().getGameMap().getConfigFile();

            if(args.length == 1) {
                FileConfiguration cfg = YamlConfiguration.loadConfiguration(mapConfig);
                cfg.set(args[0] + ".x", player.getLocation().getX());
                cfg.set(args[0] + ".y", player.getLocation().getY());
                cfg.set(args[0] + ".z", player.getLocation().getZ());
                cfg.set(args[0] + ".yaw", player.getLocation().getYaw());
                cfg.set(args[0] + ".pitch", player.getLocation().getPitch());
                cfg.set(args[0] + ".world", player.getLocation().getWorld().getName());

                try {
                    cfg.save(mapConfig);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                player.sendMessage("Saved location " + args[0]);
            }
        }
    }
}
