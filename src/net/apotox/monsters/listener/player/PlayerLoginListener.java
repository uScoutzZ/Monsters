package net.apotox.monsters.listener.player;

import net.apotox.monsters.Monsters;
import net.apotox.monsters.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if(Monsters.getInstance().getGame().getGameState() == GameState.LOBBY) {
            if(Bukkit.getServer().getOnlinePlayers().size() != 0) {
                event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
                event.setKickMessage("§cThis game is starting soon");
            }
        }
    }
}
