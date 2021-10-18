package net.animalshomeland.monsters.game.mobs.types;

import net.animalshomeland.gameapi.item.ItemBuilder;
import net.animalshomeland.monsters.game.mobs.Equipment;
import net.animalshomeland.monsters.game.mobs.Monster;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;

public class ArmoredSkeleton extends Monster {

    @Override
    public Monster init() {
        setName("armoredskeleton");
        setType(EntityType.SKELETON);
        setDamagePerHit(4);
        setMaxHealth(30);
        setHealth(getMaxHealth());
        setBaby(false);

        ItemBuilder helmet = ItemBuilder.create(Material.IRON_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        setEquipment(new Equipment(helmet.build(), null, null, null));

        return this;
    }
}
