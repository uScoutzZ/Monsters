package net.animalshomeland.monsters.game;

import lombok.Getter;
import net.animalshomeland.monsters.Monsters;

import java.util.HashMap;

public class Level {

    @Getter
    private HashMap<Integer, Integer> minXpByLevel;
    private int maxLevels;

    public Level() {
        minXpByLevel = new HashMap<>();
        maxLevels = Monsters.getInstance().getGameConfig().getConfigFile().getInt("max-levels");
        for(int i = 1; i <= maxLevels; i++) {
            minXpByLevel.put(i, Monsters.getInstance().getGameConfig().getConfigFile().getInt("min-xp-level_" + i));
        }
    }

    public int getLevelFromXp(int xp) {
        int level = 0;
        for(int i : minXpByLevel.values()) {
            if(i <= Monsters.getInstance().getGame().getMonstersPlayer().getXp()) {
                level++;
            } else {
                break;
            }
        }

        return level;
    }
}
