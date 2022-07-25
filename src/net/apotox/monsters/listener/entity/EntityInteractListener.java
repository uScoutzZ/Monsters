package net.apotox.monsters.listener.entity;

import net.apotox.monsters.Monsters;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class EntityInteractListener implements Listener {

    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {
        Entity entity = event.getEntity();

        if(!(entity instanceof Player)) {
            if(Monsters.getInstance().getGame().getGameMap().getMinefields().remove(event.getBlock())) {
                Block block = event.getBlock();
                block.getWorld().createExplosion(block.getLocation(), 2, false, false);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        block.setType(Material.AIR);
                    }
                }.runTaskLater(Monsters.getInstance(), 10);
            }
        }
    }
}
