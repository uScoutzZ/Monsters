package net.animalshomeland.monsters.listener.entity;

import net.animalshomeland.gameapi.util.Debug;
import net.animalshomeland.monsters.Monsters;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            if(event.getEntity() instanceof ArmorStand) {
                event.setCancelled(true);
            }

            if(player != Monsters.getInstance().getGame().getPlayer()) {
                event.setCancelled(true);
            }
        } else if(event.getDamager().getType() == EntityType.CREEPER) {
            if(!(event.getEntity() instanceof Player)) {
                event.setCancelled(true);
            }
        } else if(event.getDamager().getType() == EntityType.PRIMED_TNT) {
            if(event.getEntity() instanceof Player) {
                event.setCancelled(true);
            }
        }
    }
}
