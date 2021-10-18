package net.animalshomeland.monsters.game.mobs.types;

import net.animalshomeland.monsters.game.mobs.Monster;
import org.bukkit.entity.EntityType;

public class Enderman extends Monster {

    @Override
    public Monster init() {
        setName("enderman");
        setType(EntityType.ENDERMAN);
        setDamagePerHit(4);
        setMaxHealth(50);
        setHealth(getMaxHealth());
        setBaby(false);

        return this;
    }
}
