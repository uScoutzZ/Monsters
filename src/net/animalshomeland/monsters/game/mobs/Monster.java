package net.animalshomeland.monsters.game.mobs;

import lombok.Getter;
import net.animalshomeland.monsters.Monsters;
import net.animalshomeland.monsters.game.MonstersPlayer;
import net.animalshomeland.monsters.game.Scoreboard;
import net.animalshomeland.monsters.game.Wave;
import org.bukkit.entity.Entity;

import java.util.Iterator;
import java.util.Random;

public class Monster {

    @Getter
    private MonsterType monsterType;
    @Getter
    private Entity entity;

    public Monster(MonsterType monsterType, Entity entity) {
        this.monsterType = monsterType;
        this.entity = entity;
    }

    public void die() {
        Wave wave = Monsters.getInstance().getGame().getWave();
        MonstersPlayer player = Monsters.getInstance().getGame().getMonstersPlayer();
        wave.getLiving().remove(entity);
        wave.setRemaining(wave.getRemaining()-1);
        player.setKills(player.getKills()+1);
        int moneyDrop = new Random().nextInt(getMonsterType().getMaxMoneyDrop()-getMonsterType().getMinMoneyDrop())+ getMonsterType().getMinMoneyDrop();
        player.addMoney(moneyDrop);
        Scoreboard.update("kills");
        if(wave.getMonsters().size() == 0 && wave.getLiving().size() == 0) {
            wave.end();
        }

        for (Iterator<Entity> iterator = wave.getLiving().keySet().iterator(); iterator.hasNext();) {
            Entity entity = iterator.next();
            if(entity.isDead() || !entity.isTicking()) {
                wave.getLiving().get(entity).die();
            }
        }
    }
}
