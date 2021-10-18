package net.animalshomeland.monsters.game.mobs.types;

import net.animalshomeland.monsters.game.mobs.Monster;
import org.bukkit.entity.EntityType;

public class Skeleton extends Monster {

    @Override
    public Monster init() {
        setName("skeleton");
        setType(EntityType.SKELETON);
        setDamagePerHit(4);
        setMaxHealth(30);
        setHealth(getMaxHealth());
        setBaby(false);

        return this;
    }
}
