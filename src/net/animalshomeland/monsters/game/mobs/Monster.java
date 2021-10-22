package net.animalshomeland.monsters.game.mobs;

import com.google.common.reflect.ClassPath;
import lombok.Getter;
import lombok.Setter;
import net.animalshomeland.gameapi.item.ItemBuilder;
import net.animalshomeland.monsters.Monsters;
import net.animalshomeland.monsters.game.MonstersPlayer;
import net.animalshomeland.monsters.game.Scoreboard;
import net.animalshomeland.monsters.game.Wave;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Monster {

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

    public static HashMap<String, Monster> monstersByType = new HashMap<>();

    public Monster(String name, EntityType entityType, int maxHealth, boolean baby, Equipment equipment) {
        this.name = name;
        type = entityType;
        this.maxHealth = maxHealth;
        this.baby = baby;
        this.equipment = equipment;
    }

    public static void initMonsters() {
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(new File(Monsters.getInstance().getPath() + "/monsters.yml"));
        for (String key : fileConfiguration.getKeys(false)) {
            if(key.startsWith("monsters.")) {
                String name = fileConfiguration.getString(key.replace("monsters.", ""));
                System.out.println(key);
                EntityType entityType = EntityType.valueOf(fileConfiguration.getString(key + ".type"));
                int maxHealth = fileConfiguration.getInt(key + ".maxHealth");
                boolean baby = fileConfiguration.getBoolean(key + ".baby");
                String[] armorContents = {"helmet", "chestplate", "leggings", "boots"};
                ItemStack[] armor = new ItemStack[4];
                int i = 0;
                for(String armorContent : armorContents) {
                    if(fileConfiguration.getString(key + "." + armorContent) != null) {
                        ItemBuilder itemBuilder = ItemBuilder.create(Material.valueOf(fileConfiguration.getString(key + "." + armorContent + ".material")));
                        String enchants =  fileConfiguration.getString("enchants");
                        if(enchants != null) {
                            for(String enchantments : enchants.split(",")) {
                                Enchantment enchantment = Enchantment.getByName(enchantments.split(":")[0]);
                                int level = Integer.parseInt(enchantments.split(":")[1]);
                                itemBuilder.enchant(enchantment, level);
                            }
                        }
                        armor[i] = itemBuilder.build();
                        i++;
                    }
                }

                Equipment equipment = new Equipment(armor[0], armor[1], armor[2], armor[3]);

                monstersByType.put(name, new Monster(name, entityType, maxHealth, baby, equipment));
            }
        }
    }

    public void die() {
        Wave wave = Monsters.getInstance().getGame().getWave();
        MonstersPlayer player = Monsters.getInstance().getGame().getMonstersPlayer();
        //Monster monster = wave.getLiving().remove(entity);
        wave.setRemaining(wave.getRemaining()-1);
        player.setKills(player.getKills()+1);
        int moneyDrop = new Random().nextInt(getMaxMoneyDrop()-getMinMoneyDrop())+getMinMoneyDrop();
        player.addMoney(moneyDrop);
        Scoreboard.update("kills");
        if(wave.getMonsters().size() == 0 && wave.getLiving().size() == 0) {
            wave.end();
        }

        for (Iterator<Entity> iterator = wave.getLiving().keySet().iterator(); iterator.hasNext();) {
            Entity entity = iterator.next();
            if(entity.isDead() || !entity.isTicking()) {
                wave.getLiving().remove(entity).die();
            }
        }
    }
}
