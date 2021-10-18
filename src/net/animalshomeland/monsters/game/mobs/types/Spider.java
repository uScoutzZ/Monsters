package net.animalshomeland.monsters.game.mobs.types;

import net.animalshomeland.monsters.game.mobs.Monster;
import org.bukkit.entity.EntityType;

public class Spider extends Monster {

    @Override
    public Monster init() {
        setName("spider");
        setType(EntityType.SPIDER);
        setDamagePerHit(4);
        setMaxHealth(35);
        setHealth(getMaxHealth());
        setBaby(false);

        return this;
    }
}
