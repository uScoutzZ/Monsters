package net.apotox.monsters.game;

import lombok.Getter;
import lombok.Setter;
import net.apotox.gameapi.minigame.GameCountdown;
import net.apotox.gameapi.util.TimeUtilities;
import net.apotox.monsters.Monsters;
import net.apotox.monsters.game.mobs.Monster;
import net.apotox.monsters.game.mobs.MonsterType;
import net.apotox.monsters.utilities.Locale;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Wave {

    @Getter @Setter
    private int remaining, counter, defaultCounter;
    @Getter
    private int wave;
    @Getter
    private List<MonsterType> monsters;
    @Getter
    private Map<Entity, Monster> living;
    @Getter
    private long beginningTime;
    @Getter
    private GameCountdown warmupCountdown;

    public Wave(int waveNr) {
        defaultCounter = Monsters.getInstance().getGameConfig().getConfigFile().getInt("wave-warmup-timer");
        if(waveNr == 1) {
            counter = 0;
        } else {
            counter = defaultCounter;
        }
        wave = waveNr;
        FileConfiguration fileConfiguration = Monsters.getInstance().getGame().getGameMap().getMapConfig();

        monsters = new ArrayList<>();
        living = new HashMap<>();

        for (String key : fileConfiguration.getKeys(true)) {
            if(key.startsWith("wave-" + waveNr + ".")) {
                String monsterName = key.split("\\.")[1];
                if(MonsterType.monstersByType.containsKey(monsterName)) {
                    MonsterType monster = MonsterType.monstersByType.get(monsterName);
                    int amount = fileConfiguration.getInt(key);
                    for(int i = 0; i < amount; i++) {
                        monsters.add(monster);
                    }
                }
            }
        }
        remaining = monsters.size();
    }

    public void spawnRandomMonster() {
        int index = new Random().nextInt(monsters.size());
        MonsterType randomMonsterType = monsters.get(index);
        monsters.remove(index);
        Location spawnLocation = Monsters.getInstance().getGame().getGameMap().getRandomSpawn();
        Entity entity = Monsters.getInstance().getGame().getGameMap().getMap().spawnEntity(spawnLocation, randomMonsterType.getType());
        LivingEntity livingEntity = (LivingEntity) entity;
        MonstersPlayer monstersPlayer = Monsters.getInstance().getGame().getMonstersPlayer();
        if(monstersPlayer.getLevel() > 0) {
            randomMonsterType.setMaxHealth(randomMonsterType.getMaxHealth()+(monstersPlayer.getLevel())*2);
        }
        livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(randomMonsterType.getMaxHealth());
        livingEntity.setHealth(randomMonsterType.getMaxHealth());
        livingEntity.setRemoveWhenFarAway(false);

        if(randomMonsterType.getEquipment() != null) {
            randomMonsterType.getEquipment().equipMonster(entity);
        }
        if(livingEntity instanceof Ageable) {
            Ageable ageable = (Ageable) livingEntity;
            if(randomMonsterType.isBaby()) {
                ageable.setBaby();
            } else {
                ageable.setAdult();
            }
        }
        living.put(entity, new Monster(randomMonsterType, entity));
        if(monsters.size() != 0) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    spawnRandomMonster();
                }
            }.runTaskLater(Monsters.getInstance(), (new Random().nextInt(5)+3)*20);
        }
    }

    public void startWave() {
        counter = 0;
        beginningTime = System.currentTimeMillis();
        Monsters.getInstance().getGame().setGameState(GameState.WAVE_RUNNING);
        Player player = Monsters.getInstance().getGame().getPlayer();
        player.sendMessage(Locale.get(player, "wave_started", wave));
        String[] waveTitle = Locale.get(player, "wave_started_title", wave).split("\n");
        player.sendTitle(waveTitle[0], waveTitle[1], 25, 70, 15);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 0.5F, 1.0F);
        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
        Monsters.getInstance().getGame().getGameMap().changeTime();

        new BukkitRunnable() {
            @Override
            public void run() {
                spawnRandomMonster();
            }
        }.runTaskLater(Monsters.getInstance(), (new Random().nextInt(15)+5)*20);
        Scoreboard.update("kills");

        if(Monsters.getInstance().getGame().getMaxWaves() == wave) {
            Monsters.getInstance().getGame().getShop().initialize();
            player.sendMessage(Locale.get(player, "shop_last-wave"));
        }
    }

    public void startWarmup() {
        Monsters.getInstance().getGame().setGameState(GameState.WAVE_WARUMUP);
        Monsters.getInstance().getGame().getGameMap().changeTime();

        Monsters.getInstance().getGame().setGameCountdown(
                warmupCountdown = new GameCountdown(counter)
                        .setLocale(Locale.class, "wavestart")
                        .setSound(Sound.BLOCK_NOTE_BLOCK_BASS)
                        .setActionOnFinish(players -> {
                            startWave();
                        }).start());
    }

    public void end() {
        Player player = Monsters.getInstance().getGame().getPlayer();
        MonstersPlayer monstersPlayer = Monsters.getInstance().getGame().getMonstersPlayer();
        if(living.size() == 0) {
            player.sendMessage(Locale.get(player, "wave_success_time", wave, TimeUtilities.getReamingTime(System.currentTimeMillis()-beginningTime)));
        }
        Wave wave = new Wave(getWave()+1);
        if(wave.getRemaining() == 0 || Monsters.getInstance().getGame().getMonstersPlayer().getLifes() == 0) {
            Monsters.getInstance().getGame().setGameState(GameState.END);
            Monsters.getInstance().getGame().setGameCountdown(
                    new GameCountdown(Monsters.getInstance().getGameConfig().getConfigFile().getInt("end-timer"))
                            .setLocale(Locale.class, "gameend")
                            .setSound(Sound.BLOCK_NOTE_BLOCK_BASS)
                            .setActionOnFinish(players -> {
                                Monsters.getInstance().getGame().end(false);
                            }).start());
            String wonOrNot = "§c✗";
            if(Monsters.getInstance().getGame().getWave().getWave() == Monsters.getInstance().getGame().getMaxWaves()
                    && Monsters.getInstance().getGame().getWave().getLiving().size() == 0) {
                wonOrNot = "§a✔";
            }
            player.sendMessage(Locale.get(player, "gameend_stats", monstersPlayer.getKills(),
                    monstersPlayer.getDeaths(), monstersPlayer.getLevel(), monstersPlayer.getEarnedMoney(),
                    TimeUtilities.getReamingTime(System.currentTimeMillis()-Monsters.getInstance().getGame().getBeginningTime()),
                    wonOrNot));
        } else {
            Monsters.getInstance().getGame().setWave(wave);
            wave.startWarmup();
            Scoreboard.update("wave");
        }
    }
}
