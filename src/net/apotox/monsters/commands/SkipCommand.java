package net.apotox.monsters.commands;

import net.apotox.gameapi.command.Command;
import net.apotox.gameapi.command.RegisterCommand;
import net.apotox.monsters.Monsters;
import net.apotox.monsters.game.GameState;
import net.apotox.monsters.utilities.Locale;
import org.bukkit.entity.Player;

@RegisterCommand(name = "skip")
public class SkipCommand extends Command {

    @Override
    public void onPlayerExecute(Player player, String command, String[] args) {
        if(Monsters.getInstance().getGame().getGameState() == GameState.WAVE_WARUMUP) {
            if(Monsters.getInstance().getGame().getWave().getWarmupCountdown().getTime() > 10) {
                Monsters.getInstance().getGame().getWave().getWarmupCountdown().setTime(10);
            }
        } else {
            player.sendMessage(Locale.get(player, "wrong-state"));
        }
    }
}
