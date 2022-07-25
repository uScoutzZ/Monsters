package net.apotox.monsters.game;

import lombok.Getter;
import lombok.Setter;
import net.apotox.monsters.Monsters;
import net.apotox.monsters.utilities.Locale;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class MonstersPlayer {

    @Getter @Setter
    private int xp, level, kills, money, lifes = 3, deaths, earnedMoney;
    @Getter
    private Player player;

    public MonstersPlayer(Player player) {
        this.player = player;
    }

    public String getProgressbar() {
        if(Monsters.getInstance().getGame().getLevel().getMinXpByLevel().size() == level) {
            return Locale.get(player, "level_maximum-reached");
        }
        int percentage = (xp*100)/ Monsters.getInstance().getGame().getLevel().getMinXpByLevel().get(level+1);
        int percentPerStripe = 2;
        int coloredStripes = percentage/percentPerStripe;
        StringBuilder progressbar = new StringBuilder();

        for(int i = 0; i < 100/percentPerStripe; i++) {
            if(i >= coloredStripes) {
                progressbar.append("§7|");
            } else {
                progressbar.append("§a|");
            }
        }

        return progressbar.toString() + " §7" + percentage + "%";
    }

    public String getLifesBar() {
        switch(this.lifes) {
            case 3:
                return "§c❤❤❤";
            case 2:
                return "§c❤❤§7❤";
            case 1:
                return "§c❤§7❤❤";
            default:
                return "§7❤❤❤";
        }
    }

    public Entity getNearestMonster() {
        Entity nearestEntity = null;
        double distance = 1337;
        for(Entity entity : Monsters.getInstance().getGame().getWave().getLiving().keySet()) {
            if(nearestEntity == null) {
                nearestEntity = entity;
                distance = player.getLocation().distance(entity.getLocation());
            }

            double newDistance = entity.getLocation().distance(player.getLocation());
            if(distance > newDistance) {
                distance = newDistance;
                nearestEntity = entity;
            }
        }

        return nearestEntity;
    }

    public void addMoney(int money) {
        if(money > 0) {
            player.sendMessage(Locale.get(player, "add_money", money));
        } else {
            player.sendMessage(Locale.get(player, "remove_money", String.valueOf(money).replace("-", "")));
        }
        this.money += money;
        if(money > 0) {
            earnedMoney += money;
        }

    }

    public void addXp(int xp) {
        player.sendMessage(Locale.get(player, "add_xp", xp));
        if(Monsters.getInstance().getGame().getLevel().getMinXpByLevel().get(level+1) <= xp+this.xp) {
            addLevel();
        } else {
            this.xp += xp;
        }
    }

    public void addLevel() {
        level += 1;
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        Scoreboard.update("level");
    }

    public void removeLife() {
        deaths++;
        lifes -= 1;
    }
}
