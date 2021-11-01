package net.animalshomeland.monsters.commands;

import net.animalshomeland.gameapi.command.Command;
import net.animalshomeland.gameapi.command.RegisterCommand;
import net.animalshomeland.monsters.Monsters;
import org.bukkit.entity.Player;

@RegisterCommand(name = "start", permission = "monsters.command.start")
public class StartCommand extends Command {

    @Override
    public void onPlayerExecute(Player player, String command, String[] args) {
        Monsters.getInstance().getGame().getGameCountdown().setTime(3);
    }
}
