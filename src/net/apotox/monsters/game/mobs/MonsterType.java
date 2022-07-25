package net.apotox.monsters.game.mobs;

import lombok.Getter;
import lombok.Setter;
import net.apotox.gameapi.item.ItemBuilder;
import net.apotox.monsters.Monsters;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;

public class MonsterType {

    @Getter @Setter
    private int health, maxHealth, minMoneyDrop = 7, maxMoneyDrop = 13;
    @Getter @Setter
    private EntityType type;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private Equipment equipment;
    @Getter @Setter
    private boolean baby;

    public static HashMap<String, MonsterType> monstersByType = new HashMap<>();

    public MonsterType(String name, EntityType entityType, int maxHealth, boolean baby, Equipment equipment) {
        this.name = name;
        type = entityType;
        this.maxHealth = maxHealth;
        this.baby = baby;
        this.equipment = equipment;
    }

    public static void initMonsters() {
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(new File(Monsters.getInstance().getPath() + "/monsters.yml"));
        for (String key : fileConfiguration.getKeys(false)) {
            String name = key;
            System.out.println("Loading monster " + key);
            EntityType entityType = EntityType.valueOf(fileConfiguration.getString(key + ".type"));
            int maxHealth = fileConfiguration.getInt(key + ".maxHealth");
            boolean baby = fileConfiguration.getBoolean(key + ".baby");
            String[] armorContents = {"helmet", "chestplate", "leggings", "boots"};
            ItemStack[] armor = new ItemStack[4];
            int i = 0;
            for(String armorContent : armorContents) {
                if(fileConfiguration.getString(key + "." + armorContent) != null) {
                    ItemBuilder itemBuilder = ItemBuilder.create(Material.valueOf(fileConfiguration.getString(key + "." + armorContent + ".material")));
                    String enchants =  fileConfiguration.getString(key + "." + armorContent + ".enchants");
                    if(enchants != null) {
                        for(String enchantments : enchants.split(",")) {
                            Enchantment enchantment = Enchantment.getByName(enchantments.split(":")[0]);
                            int level = Integer.parseInt(enchantments.split(":")[1]);
                            itemBuilder.enchant(enchantment, level);
                        }
                    }
                    armor[i] = itemBuilder.build();
                }
                i++;
            }

            Equipment equipment = new Equipment(armor[0], armor[1], armor[2], armor[3]);

            monstersByType.put(name, new MonsterType(name, entityType, maxHealth, baby, equipment));
        }
    }
}
