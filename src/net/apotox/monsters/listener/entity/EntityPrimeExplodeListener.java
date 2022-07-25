package net.apotox.monsters.listener.entity;

import net.apotox.monsters.Monsters;
import net.apotox.monsters.game.Wave;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class EntityPrimeExplodeListener implements Listener {

    @EventHandler
    public void onEntityPrimeExplode(ExplosionPrimeEvent event) {
        Wave wave = Monsters.getInstance().getGame().getWave();
        if(wave.getLiving().containsKey(event.getEntity())) {
            wave.getLiving().get(event.getEntity()).die();
        }
    }
}
