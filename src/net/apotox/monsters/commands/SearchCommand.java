package net.apotox.monsters.commands;

import net.apotox.gameapi.command.Command;
import net.apotox.gameapi.command.RegisterCommand;
import net.apotox.monsters.Monsters;
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
