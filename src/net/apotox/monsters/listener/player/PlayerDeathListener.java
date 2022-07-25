package net.apotox.monsters.listener.player;

import net.apotox.monsters.Monsters;
import net.apotox.monsters.utilities.Locale;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        Player player = event.getEntity();
        if(Monsters.getInstance().getGame().getPlayer() == player) {
            Monsters.getInstance().getGame().getMonstersPlayer().removeLife();
            if(Monsters.getInstance().getGame().getMonstersPlayer().getLifes() > 0) {
                player.sendMessage(Locale.get(player, "player_dead_life-remaing", Monsters.getInstance().getGame().getMonstersPlayer().getLifes()));
            } else {
                player.sendMessage(Locale.get(player, "player_dead_game-over"));
                Monsters.getInstance().getGame().getWave().end();
            }
        }
    }
}
