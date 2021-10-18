package net.animalshomeland.monsters.game.mobs.types;

import net.animalshomeland.gameapi.item.ItemBuilder;
import net.animalshomeland.monsters.game.mobs.Equipment;
import net.animalshomeland.monsters.game.mobs.Monster;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class BabyZombie extends Monster{

    @Override
    public Monster init() {
        setName("babyzombie");
        setType(EntityType.ZOMBIE);
        setDamagePerHit(4);
        setMaxHealth(30);
        setHealth(getMaxHealth());
        setBaby(true);

        ItemBuilder helmet = ItemBuilder.create(Material.CHAINMAIL_HELMET);
        ItemBuilder chestplate = ItemBuilder.create(Material.GOLDEN_CHESTPLATE);
        ItemBuilder leggings = ItemBuilder.create(Material.CHAINMAIL_LEGGINGS);
        ItemBuilder boots = ItemBuilder.create(Material.CHAINMAIL_BOOTS);
        setEquipment(new Equipment(helmet.build(), chestplate.build(), leggings.build(), boots.build()));

        return this;
    }
}
