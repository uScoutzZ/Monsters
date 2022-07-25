package net.apotox.monsters.listener.entity;

import net.apotox.monsters.Monsters;
import net.apotox.monsters.game.GameState;
import net.apotox.monsters.game.Wave;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;


public class EntityDeathListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if(Monsters.getInstance().getGame().getGameState() == GameState.WAVE_RUNNING) {
            Wave wave = Monsters.getInstance().getGame().getWave();
            if(wave.getLiving().containsKey(event.getEntity())) {
                event.getDrops().clear();
                wave.getLiving().get(event.getEntity()).die();
            }
        }
    }
}
