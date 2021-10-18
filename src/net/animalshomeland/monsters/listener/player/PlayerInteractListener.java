package net.animalshomeland.monsters.listener.player;

import net.animalshomeland.monsters.Monsters;
import net.animalshomeland.monsters.game.Game;
import net.animalshomeland.monsters.game.GameState;
import net.animalshomeland.monsters.game.shop.ShopCategory;
import net.animalshomeland.monsters.utilities.Locale;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.CompassMeta;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Game game = Monsters.getInstance().getGame();

        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getItem() != null) {
                if(event.getItem().hasItemMeta()) {
                    if (event.getItem().getItemMeta().getDisplayName().equals(Locale.get(player, "shop_item"))) {
                        if (game.getGameState() == GameState.WAVE_WARUMUP || game.getWave().getWave() == game.getMaxWaves()) {
                            game.getShop().open(ShopCategory.MAIN);
                        } else {
                            player.sendMessage(Locale.get(player, "shop_not-usable"));
                        }
                    } else if (event.getItem().getItemMeta().getDisplayName().equals(Locale.get(player, "shop_item_tracker"))) {
                        if(game.getWave().getLiving().size() == 0) {
                            player.sendMessage(Locale.get(player, "tracker_no-monsters"));
                        } else {
                            Entity nearestEntity = null;
                            double distance = 1337;
                            for(Entity entity : game.getWave().getLiving().keySet()) {
                                if(nearestEntity == null) {
                                    nearestEntity = entity;
                                    distance = player.getLocation().distance(entity.getLocation());
                                }

                                double newDistance = entity.getLocation().distance(player.getLocation());
                                if(distance > newDistance) {
                                    distance = newDistance;
                                    nearestEntity = entity;
                                }
                            }
                            player.sendMessage(Locale.get(player, "tracker_monster", (int) distance));
                            player.setCompassTarget(nearestEntity.getLocation());
                        }
                    }
                }
            }
        }
    }
}
