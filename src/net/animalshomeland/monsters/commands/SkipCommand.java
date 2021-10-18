package net.animalshomeland.monsters.commands;

import net.animalshomeland.gameapi.command.Command;
import net.animalshomeland.gameapi.command.RegisterCommand;
import net.animalshomeland.monsters.Monsters;
import net.animalshomeland.monsters.game.GameState;
import net.animalshomeland.monsters.utilities.Locale;
import org.bukkit.entity.Player;

@RegisterCommand(name = "skip")
public class SkipCommand extends Command {

    @Override
    public void onPlayerExecute(Player player, String command, String[] args) {
        if(Monsters.getInstance().getGame().getGameState() == GameState.WAVE_WARUMUP) {
            if(Monsters.getInstance().getGame().getWave().getCounter() > 10) {
                Monsters.getInstance().getGame().getWave().setCounter(10);
            }
        } else {
            player.sendMessage(Locale.get(player, "wrong-state"));
        }
    }
}
