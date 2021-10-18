package net.animalshomeland.monsters.listener.player;

import net.animalshomeland.monsters.Monsters;
import net.animalshomeland.monsters.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(Monsters.getInstance().getGame().getGameState() == GameState.LOBBY) {
            Monsters.getInstance().getGame().getGameCountdown().startLobbyCounter(player);
        } else {
            Monsters.getInstance().getGame().getPlayer().hidePlayer(Monsters.getInstance(), player);
        }
    }
}
