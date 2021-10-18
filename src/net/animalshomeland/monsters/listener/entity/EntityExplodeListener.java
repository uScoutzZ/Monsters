package net.animalshomeland.monsters.listener.entity;

import net.animalshomeland.gameapi.util.Debug;
import net.animalshomeland.monsters.Monsters;
import net.animalshomeland.monsters.game.Wave;
import net.animalshomeland.monsters.game.mobs.Monster;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import java.util.ArrayList;

public class EntityExplodeListener implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if(event.getEntity() instanceof TNTPrimed) {
            event.blockList().clear();
        }
    }
}
