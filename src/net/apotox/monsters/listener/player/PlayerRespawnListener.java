package net.apotox.monsters.listener.player;

import net.apotox.monsters.Monsters;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(Monsters.getInstance().getGame().getGameMap().getSpawn());
    }
}
