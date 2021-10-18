package net.animalshomeland.monsters.commands;

import net.animalshomeland.gameapi.command.Command;
import net.animalshomeland.gameapi.command.RegisterCommand;
import net.animalshomeland.monsters.Monsters;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@RegisterCommand(name = "search", permission = "monsters.command.search")
public class SearchCommand extends Command {

    @Override
    public void onPlayerExecute(Player player, String command, String[] args) {
        for(Entity entity : Monsters.getInstance().getGame().getWave().getLiving().keySet()) {
            player.sendMessage(entity.getType() +" at " + entity.getLocation().getX() + ", " + entity.getLocation().getY() + ", " + entity.getLocation().getZ());
        }
    }
}
