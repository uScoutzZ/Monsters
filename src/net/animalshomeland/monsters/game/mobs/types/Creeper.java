package net.animalshomeland.monsters.game.mobs.types;

import net.animalshomeland.monsters.game.mobs.Monster;
import org.bukkit.entity.EntityType;

public class Creeper extends Monster {

    @Override
    public Monster init() {
        setName("creeper");
        setType(EntityType.CREEPER);
        setDamagePerHit(4);
        setMaxHealth(40);
        setHealth(getMaxHealth());
        setBaby(false);

        return this;
    }
}
