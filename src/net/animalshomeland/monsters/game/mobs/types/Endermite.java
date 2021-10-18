package net.animalshomeland.monsters.game.mobs.types;

import net.animalshomeland.gameapi.item.ItemBuilder;
import net.animalshomeland.monsters.game.mobs.Equipment;
import net.animalshomeland.monsters.game.mobs.Monster;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;

public class Endermite extends Monster {

    @Override
    public Monster init() {
        setName("endermite");
        setType(EntityType.ENDERMITE);
        setDamagePerHit(4);
        setMaxHealth(30);
        setHealth(getMaxHealth());
        setBaby(false);

        return this;
    }
}
