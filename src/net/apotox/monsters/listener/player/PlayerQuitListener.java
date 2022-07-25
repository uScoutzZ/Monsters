package net.apotox.monsters.listener.player;

import net.apotox.monsters.Monsters;
import net.apotox.monsters.game.Game;
import net.apotox.monsters.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(null);

        Game game = Monsters.getInstance().getGame();
        if(game.getGameState() == GameState.LOBBY) {
            game.getGameCountdown().stop();
        } else {
            if(game.getGameState() != GameState.END) {
                if(player == game.getPlayer()) {
                    game.end(true);
                }
            }
        }
    }
}
