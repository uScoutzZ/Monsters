package net.apotox.monsters.game.shop;

import lombok.Getter;
import net.apotox.gameapi.inventory.InventoryBuilder;
import net.apotox.gameapi.inventory.SimpleInventory;
import net.apotox.gameapi.item.ItemBuilder;
import net.apotox.monsters.Monsters;
import net.apotox.monsters.game.MonstersPlayer;
import net.apotox.monsters.game.Scoreboard;
import net.apotox.monsters.utilities.Locale;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Shop {

    @Getter
    private Player player;
    @Getter
    private Map<ShopCategory, SimpleInventory> inventories;

    private FileConfiguration shopConfig;

    public Shop(Player player) {
        this.player = player;
        inventories = new HashMap<>();
        File file = new File(Monsters.getInstance().getPath() + "shop.yml");
        shopConfig = YamlConfiguration.loadConfiguration(file);
        initialize();
    }

    public void open(ShopCategory category) {
        if(inventories.containsKey(category)) {
            SimpleInventory inventory = inventories.get(category);
            inventory.open(player);
        } else {
            player.sendMessage("§cShop not set up");
        }
    }

    public void initialize() {
        for(ShopCategory shopCategory : ShopCategory.values()) {
            if(shopCategory != ShopCategory.MAIN) {
                String name = shopCategory.toString().toLowerCase();
                if(shopConfig.getInt(name + ".size") != 0) {
                    int size = shopConfig.getInt(shopCategory.toString().toLowerCase() + ".size");
                    String title = Locale.get(player, "shop_title", Locale.get(player, "shop_" + name + "_title"));
                    SimpleInventory inventory = InventoryBuilder.create(size, title);
                    inventory.fill(0, 9, ItemBuilder.create(Material.GRAY_STAINED_GLASS_PANE).name(" "));
                    inventory.fill(size-9, size, ItemBuilder.create(Material.GRAY_STAINED_GLASS_PANE).name(" "));
                    for (String key : shopConfig.getConfigurationSection(name + ".items").getKeys(false)) {
                        int slot = Integer.valueOf(key);
                        String path = name + ".items." + key + ".";
                        Material material = Material.getMaterial(shopConfig.getString(path + "type"));
                        int price = shopConfig.getInt(path + "price");
                        int xp = shopConfig.getInt(path + "xp");
                        String priceLocale = "shop_price";
                        if(Monsters.getInstance().getGame().getWave().getWave() > 1) {
                            price += 50;
                        }
                        ItemBuilder itemBuilder = ItemBuilder.create(material)
                                .lore(Locale.get(player, priceLocale, price)).lore(Locale.get(player, "shop_xp", xp));
                        MonstersPlayer monstersPlayer = Monsters.getInstance().getGame().getMonstersPlayer();
                        String enchants = shopConfig.getString(path + "enchants");
                        if(enchants != null) {
                            if(enchants.contains(", ")) {
                                for(String enchantments : enchants.split(", ")) {
                                    Enchantment enchantment = Enchantment.getByName(enchantments.split(":")[0]);
                                    int level = Integer.parseInt(enchantments.split(":")[1]);
                                    itemBuilder.enchant(enchantment, level);
                                }
                            } else {
                                Enchantment enchantment = Enchantment.getByName(enchants.split(":")[0]);
                                int level = Integer.parseInt(enchants.split(":")[1]);
                                itemBuilder.enchant(enchantment, level);
                            }
                        }

                        String itemname = shopConfig.getString(path + "name");
                        if(itemname != null) {
                            itemBuilder.name(Locale.get(player, itemname));
                        }

                        int amount = shopConfig.getInt(path + "amount");
                        if(amount != 0) {
                            itemBuilder.amount(amount);
                        }

                        ItemStack stack = itemBuilder.build();
                        String potionEffect = shopConfig.getString(path + "potioneffect");
                        if(potionEffect != null) {
                            PotionMeta meta = (PotionMeta) stack.getItemMeta();
                            String[] potionData = potionEffect.split(":");
                            meta.setBasePotionData(new PotionData(PotionType.valueOf(potionData[0]),
                                    Boolean.parseBoolean(potionData[1]), Boolean.parseBoolean(potionData[2])));
                            stack.setItemMeta(meta);
                        }

                        int finalPrice = price;
                        inventory.setItem(slot, stack, event -> {
                            if(monstersPlayer.getMoney() >= finalPrice) {
                                ItemStack itemStack = event.getCurrentItem().clone();
                                itemStack.setLore(null);
                                player.getInventory().addItem(itemStack);
                                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
                                monstersPlayer.addMoney(-finalPrice);
                                monstersPlayer.addXp(xp);
                                Scoreboard.update("money");
                            } else {
                                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0F, 1.0F);
                                player.sendMessage(Locale.get(player, "shop_not-enough-money"));
                            }
                        });
                        inventory.setItem(8, ItemBuilder.create(Material.RED_STAINED_GLASS_PANE).name(Locale.get(player, "shop_back")), event -> {
                           inventories.get(ShopCategory.MAIN).open(player);
                        });
                    }
                    inventory.setDeleteOnClose(false);
                    inventories.put(shopCategory, inventory);
                }
            } else {
                SimpleInventory inventory = InventoryBuilder.create(3*9, Locale.get(player, "shop_title", Locale.get(player, "shop_main_title")));
                inventory.fill(0, 9, ItemBuilder.create(Material.GRAY_STAINED_GLASS_PANE).name(" "));

                inventory.setItem(9, ItemBuilder.create(Material.OAK_LEAVES)
                        .name("§9" + ShopCategory.BLOCKS.getName()), event -> open(ShopCategory.BLOCKS));
                inventory.setItem(10, ItemBuilder.create(Material.TNT)
                        .name("§9" + ShopCategory.UTILITIES.getName()), event -> open(ShopCategory.UTILITIES));
                inventory.setItem(11, ItemBuilder.create(Material.COOKED_BEEF)
                        .name("§9" + ShopCategory.FOOD.getName()), event -> open(ShopCategory.FOOD));
                inventory.setItem(12, ItemBuilder.create(Material.POTION)
                        .name("§9" + ShopCategory.POTIONS.getName()), event -> open(ShopCategory.POTIONS));

                inventory.setItem(14, ItemBuilder.create(Material.DIAMOND_SWORD)
                        .name("§9" + ShopCategory.WEAPONS.getName()), event -> open(ShopCategory.WEAPONS));
                inventory.setItem(15, ItemBuilder.create(Material.DIAMOND_SWORD)
                        .name("§9" + ShopCategory.ENCH_WEAPONS.getName())
                        .enchant(Enchantment.KNOCKBACK, 1).flag(ItemFlag.HIDE_ENCHANTS), event -> open(ShopCategory.ENCH_WEAPONS));
                inventory.setItem(16, ItemBuilder.create(Material.DIAMOND_CHESTPLATE)
                        .name("§9" + ShopCategory.ARMOR.getName()), event -> open(ShopCategory.ARMOR));
                inventory.setItem(17, ItemBuilder.create(Material.NETHERITE_CHESTPLATE)
                        .name("§9" + ShopCategory.ENCH_ARMOR.getName())
                        .enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).flag(ItemFlag.HIDE_ENCHANTS), event -> open(ShopCategory.ENCH_ARMOR));

                inventory.fill(18, 27, ItemBuilder.create(Material.GRAY_STAINED_GLASS_PANE).name(" "));
                inventory.setDeleteOnClose(false);
                inventories.put(ShopCategory.MAIN, inventory);
            }
        }
    }
}
