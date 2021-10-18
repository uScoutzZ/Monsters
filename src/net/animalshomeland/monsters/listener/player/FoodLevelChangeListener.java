package net.animalshomeland.monsters.listener.player;

import net.animalshomeland.monsters.Monsters;
import net.animalshomeland.monsters.game.GameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if(Monsters.getInstance().getGame().getGameState() != GameState.WAVE_RUNNING) {
            if(event.getItem() == null) {
                event.setCancelled(true);
            }
        }
    }
}
