package net.animalshomeland.monsters.game.mobs;

import com.google.common.reflect.ClassPath;
import lombok.Getter;
import lombok.Setter;
import net.animalshomeland.monsters.Monsters;
import net.animalshomeland.monsters.game.MonstersPlayer;
import net.animalshomeland.monsters.game.Scoreboard;
import net.animalshomeland.monsters.game.Wave;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public abstract class Monster {

    @Getter @Setter
    private int health, maxHealth, damagePerHit, minMoneyDrop = 7, maxMoneyDrop = 13;
    @Getter @Setter
    private EntityType type;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private Equipment equipment;
    @Getter @Setter
    private boolean baby;

    public static HashMap<String, Monster> monstersByType = new HashMap<>();

    public abstract Monster init();

    public static void initMonsters() {
        ClassPath classPath = null;
        try {
            classPath = ClassPath.from(Monsters.class.getClassLoader());
        } catch (IOException e) {
            e.printStackTrace();
        }
        classPath.getTopLevelClassesRecursive("net.animalshomeland.monsters.game.mobs.types").forEach(classInfo -> {

            Class c = null;
            try {
                c = Class.forName(classInfo.getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }

            try {
                Object object = c.newInstance();
                Monster monster = (Monster) object;
                monster.init();

                monstersByType.put(monster.getName(), monster);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        });
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
