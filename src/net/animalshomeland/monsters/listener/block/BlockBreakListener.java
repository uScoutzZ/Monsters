package net.animalshomeland.monsters.listener.block;

import net.animalshomeland.monsters.Monsters;
import org.bukkit.GameMode;
import org.bukkit.GrassSpecies;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(Monsters.getInstance().getGame().getGameMap().getPlaced().remove(event.getBlock()) ||
                event.getBlock().getType() == Material.GRASS || event.getBlock().getType() == Material.TALL_GRASS) {
            event.setCancelled(false);
            event.getBlock().getDrops().clear();
        } else {
            event.setCancelled(true);
        }
    }
}
