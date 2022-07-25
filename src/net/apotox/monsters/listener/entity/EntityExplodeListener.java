package net.apotox.monsters.listener.entity;

import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if(event.getEntity() instanceof TNTPrimed) {
            event.blockList().clear();
        }
    }
}
