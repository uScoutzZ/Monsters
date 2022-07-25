package net.apotox.monsters.game;

import lombok.Getter;
import lombok.Setter;
import net.apotox.gameapi.item.ItemBuilder;
import net.apotox.gameapi.minigame.GameCountdown;
import net.apotox.gameapi.user.User;
import net.apotox.gameapi.util.ServerUtilities;
import net.apotox.monsters.Monsters;
import net.apotox.monsters.game.mobs.MonsterType;
import net.apotox.monsters.game.shop.Shop;
import net.apotox.monsters.utilities.Locale;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class Game {

    @Getter @Setter
    private GameState gameState;
    @Getter @Setter
    private Player player;
    @Getter @Setter
    private MonstersPlayer monstersPlayer;
    @Getter @Setter
    private Level level;
    @Getter @Setter
    private GameCountdown gameCountdown;
    @Getter
    private GameMap gameMap;
    @Getter @Setter
    private Wave wave;
    @Getter
    private Shop shop;
    @Getter @Setter
    private long beginningTime, maxWaves;

    public Game() {
        gameState = GameState.LOBBY;
        gameMap = new GameMap();
        level = new Level();
        maxWaves = gameMap.getMapConfig().getInt("max-waves");

        MonsterType.initMonsters();
    }

    public void start() {
        player.teleport(new Location(player.getWorld(), 0, 10, 0));
        player.sendMessage(Locale.get(player, "gamestart_start"));
        ServerUtilities.setServerInvisible();
        beginningTime = System.currentTimeMillis();

        wave = new Wave(1);
        gameState = GameState.WAVE_RUNNING;
        shop = new Shop(player);

        User user = User.getFromPlayer(player);
        Scoreboard scoreboard = new Scoreboard();
        new BukkitRunnable() {
            @Override
            public void run() {
                user.sendActionbar(Locale.get(player, "ingame_actionbar", monstersPlayer.getLifesBar(), monstersPlayer.getProgressbar()), false);
            }
        }.runTaskTimer(Monsters.getInstance(), 0, 20);
        scoreboard.setup(player);
        Monsters.getInstance().getGame().getGameMap().setSpawn(Monsters.getInstance().getLocationManager().getLocation("spawn"));
        player.teleport(Monsters.getInstance().getGame().getGameMap().getSpawn());

        ItemStack sword = ItemBuilder.create(Material.WOODEN_SWORD).build();
        ItemMeta itemMeta = sword.getItemMeta();
        itemMeta.setUnbreakable(true);
        sword.setItemMeta(itemMeta);
        player.getInventory().setItem(0, sword);
        player.getInventory().setItem(1, ItemBuilder.create(Material.COMPASS).name(Locale.get(player, "shop_item_tracker")).build());
        player.getInventory().setHelmet(ItemBuilder.create(Material.LEATHER_HELMET).build());
        player.getInventory().setChestplate(ItemBuilder.create(Material.LEATHER_CHESTPLATE).build());
        player.getInventory().setLeggings(ItemBuilder.create(Material.LEATHER_LEGGINGS).build());
        player.getInventory().setBoots(ItemBuilder.create(Material.LEATHER_BOOTS).build());
        player.getInventory().setItem(7, ItemBuilder.create(Material.COOKED_BEEF).amount(32).build());
        player.getInventory().setItem(8, ItemBuilder.create(Material.EMERALD).name(Locale.get(player, "shop_item")).build());
        wave.startWave();
    }

    public void end(boolean quitted) {
        if(!quitted) {
            player.sendMessage(Locale.get(player, "gameend_stop"));
        }
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ahstop");
    }
}
