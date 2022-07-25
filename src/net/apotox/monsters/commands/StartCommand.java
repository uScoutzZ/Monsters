package net.apotox.monsters.commands;

import net.apotox.gameapi.command.Command;
import net.apotox.gameapi.command.RegisterCommand;
import net.apotox.monsters.Monsters;
import org.bukkit.entity.Player;

@RegisterCommand(name = "start", permission = "monsters.command.start")
public class StartCommand extends Command {

    @Override
    public void onPlayerExecute(Player player, String command, String[] args) {
        Monsters.getInstance().getGame().getGameCountdown().setTime(3);
    }
}
