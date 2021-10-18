package net.animalshomeland.monsters.listener.entity;

import net.animalshomeland.monsters.Monsters;
import net.animalshomeland.monsters.game.GameState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if(entity instanceof Player && Monsters.getInstance().getGame().getGameState() != GameState.WAVE_RUNNING) {
            event.setCancelled(true);
        } else if(Monsters.getInstance().getGame().getGameState() == GameState.WAVE_RUNNING) {
            if(event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                if(entity instanceof Player) {
                    event.setCancelled(true);
                }
            }
        }

        if(entity instanceof Player) {
            Player player = (Player) event.getEntity();
            if(player != Monsters.getInstance().getGame().getPlayer()) {
                event.setCancelled(true);
            }
        }
    }
}
