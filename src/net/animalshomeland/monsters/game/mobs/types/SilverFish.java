package net.animalshomeland.monsters.game.mobs.types;

import net.animalshomeland.monsters.game.mobs.Monster;
import org.bukkit.entity.EntityType;

public class SilverFish extends Monster {

    @Override
    public Monster init() {
        setName("silverfish");
        setType(EntityType.SILVERFISH);
        setDamagePerHit(4);
        setMaxHealth(35);
        setHealth(getMaxHealth());
        setBaby(false);

        return this;
    }
}
