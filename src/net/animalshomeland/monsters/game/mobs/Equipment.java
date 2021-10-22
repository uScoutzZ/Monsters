package net.animalshomeland.monsters.game.mobs;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Equipment {

    @Getter
    private Map<EquipmentSlot, ItemStack> equipment;

    public Equipment(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        equipment = new HashMap<>();
        equipment.put(EquipmentSlot.HEAD, helmet);
        equipment.put(EquipmentSlot.CHEST, chestplate);
        equipment.put(EquipmentSlot.LEGS, leggings);
        equipment.put(EquipmentSlot.FEET, boots);
    }

    public void equipMonster(Entity entity) {
        LivingEntity livingEntity = (LivingEntity) entity;
        if(livingEntity.getEquipment() != null) {
            livingEntity.getEquipment().setHelmet(equipment.get(EquipmentSlot.HEAD));
            livingEntity.getEquipment().setChestplate(equipment.get(EquipmentSlot.CHEST));
            livingEntity.getEquipment().setLeggings(equipment.get(EquipmentSlot.LEGS));
            livingEntity.getEquipment().setBoots(equipment.get(EquipmentSlot.FEET));
        }
    }
}
