package net.apotox.monsters.listener.entity;

import net.apotox.monsters.Monsters;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public class EntityTargetListener implements Listener {

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if(event.getTarget() instanceof Player) {
            Player player = (Player) event.getTarget();
            if(player != Monsters.getInstance().getGame().getPlayer()) {
                event.setCancelled(true);
                event.setTarget(Monsters.getInstance().getGame().getPlayer());
            }
        }

        if (event.getTarget() instanceof Mob && event.getEntity() instanceof Mob) {
            event.setCancelled(true);
            event.setTarget(Monsters.getInstance().getGame().getPlayer());
        }
    }
}
