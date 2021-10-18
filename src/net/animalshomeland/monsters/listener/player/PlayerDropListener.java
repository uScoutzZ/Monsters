package net.animalshomeland.monsters.listener.player;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropListener implements Listener {

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        if(event.getItemDrop().getItemStack().getType() == Material.EMERALD) {
            event.setCancelled(true);
        }
    }
}
