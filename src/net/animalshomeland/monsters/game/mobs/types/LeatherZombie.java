package net.animalshomeland.monsters.game.mobs.types;

import net.animalshomeland.gameapi.item.ItemBuilder;
import net.animalshomeland.monsters.game.mobs.Equipment;
import net.animalshomeland.monsters.game.mobs.Monster;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class LeatherZombie extends Monster {

    @Override
    public Monster init() {
        setName("leatherzombie");
        setType(EntityType.ZOMBIE);
        setDamagePerHit(3);
        setMaxHealth(25);
        setHealth(getMaxHealth());
        setBaby(false);

        ItemBuilder helmet = ItemBuilder.create(Material.LEATHER_HELMET);
        ItemBuilder chestplate = ItemBuilder.create(Material.LEATHER_CHESTPLATE);
        ItemBuilder leggings = ItemBuilder.create(Material.LEATHER_LEGGINGS);
        ItemBuilder boots = ItemBuilder.create(Material.LEATHER_BOOTS);
        setEquipment(new Equipment(helmet.build(), chestplate.build(), leggings.build(), boots.build()));

        return this;
    }
}
