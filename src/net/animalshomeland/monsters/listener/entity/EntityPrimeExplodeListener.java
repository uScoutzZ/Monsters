package net.animalshomeland.monsters.listener.entity;

import net.animalshomeland.monsters.Monsters;
import net.animalshomeland.monsters.game.Wave;
import net.animalshomeland.monsters.game.mobs.Monster;
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
