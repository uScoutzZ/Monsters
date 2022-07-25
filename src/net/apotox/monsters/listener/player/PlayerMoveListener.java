package net.apotox.monsters.listener.player;

import net.apotox.monsters.Monsters;
import net.apotox.monsters.game.GameState;
import net.apotox.monsters.game.MonstersPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if(Monsters.getInstance().getGame().getGameState() == GameState.WAVE_RUNNING) {
            MonstersPlayer monstersPlayer = Monsters.getInstance().getGame().getMonstersPlayer();
            Entity nearest = monstersPlayer.getNearestMonster();
            Location compassTarget = Monsters.getInstance().getGame().getGameMap().getSpawn();
            if(nearest != null) {
                compassTarget = nearest.getLocation();
            }

            monstersPlayer.getPlayer().setCompassTarget(compassTarget);
        }
    }
}
