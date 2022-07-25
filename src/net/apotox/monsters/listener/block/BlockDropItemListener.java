package net.apotox.monsters.listener.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;

public class BlockDropItemListener implements Listener {

    @EventHandler
    public void onBlockDrop(BlockDropItemEvent event) {
        event.setCancelled(true);
    }
}
