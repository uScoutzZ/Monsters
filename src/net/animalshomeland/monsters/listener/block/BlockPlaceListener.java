package net.animalshomeland.monsters.listener.block;

import net.animalshomeland.monsters.Monsters;
import net.animalshomeland.monsters.game.GameState;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        event.setCancelled(false);
        if(Monsters.getInstance().getGame().getGameState() == GameState.WAVE_RUNNING ||
                Monsters.getInstance().getGame().getGameState() == GameState.WAVE_WARUMUP) {
            if(event.getBlock().getType() == Material.STONE_PRESSURE_PLATE) {
                Monsters.getInstance().getGame().getGameMap().getMinefields().add(event.getBlock());
            } else {
                if(event.getBlock().getType() == Material.OAK_LEAVES || event.getBlock().getType() == Material.CUT_SANDSTONE
                        || event.getBlock().getType() == Material.TNT || event.getBlock().getType() == Material.FIRE
                        || event.getBlock().getType() == Material.COBWEB) {
                    Monsters.getInstance().getGame().getGameMap().getPlaced().add(event.getBlock());
                    event.setCancelled(false);
                } else {
                    event.setCancelled(true);
                }
            }
        } else {
            event.setCancelled(true);
        }
    }
}
