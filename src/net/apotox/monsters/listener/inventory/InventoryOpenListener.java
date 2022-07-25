package net.apotox.monsters.listener.inventory;

import net.apotox.gameapi.item.ItemBuilder;
import net.apotox.monsters.Monsters;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.Random;

public class InventoryOpenListener implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getInventory().getHolder() instanceof Chest){
            Block chest = event.getPlayer().getTargetBlock(5);

            if(Monsters.getInstance().getGame().getGameMap().getXpBottleChest().remove(chest)) {
                event.getInventory().setItem(12, ItemBuilder.create(Material.EXPERIENCE_BOTTLE)
                        .amount(new Random().nextInt(19)+6)
                        .build());
                event.getInventory().setItem(14, ItemBuilder.create(Material.LAPIS_LAZULI)
                        .amount(new Random().nextInt(19)+6)
                        .build());
            }
        }
    }
}
