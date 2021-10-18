package net.animalshomeland.monsters.game.mobs.types;


import net.animalshomeland.monsters.game.mobs.Monster;
import org.bukkit.entity.EntityType;

public class Zombie extends Monster {

    @Override
    public Monster init() {
        setName("zombie");
        setType(EntityType.ZOMBIE);
        setDamagePerHit(1);
        setMaxHealth(20);
        setHealth(getMaxHealth());
        setBaby(false);

        return this;
    }
}
