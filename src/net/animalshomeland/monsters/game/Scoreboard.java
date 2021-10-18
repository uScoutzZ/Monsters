package net.animalshomeland.monsters.game;

import net.animalshomeland.gameapi.GameAPI;
import net.animalshomeland.monsters.Monsters;
import net.animalshomeland.monsters.utilities.Locale;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public class Scoreboard {

    public HashMap<org.bukkit.scoreboard.Scoreboard, Player> boards = new HashMap<>();
    private int letter = 0;
    private String shown = "";

    public void setup(Player player) {
        MonstersPlayer monstersPlayer = Monsters.getInstance().getGame().getMonstersPlayer();

        org.bukkit.scoreboard.Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("abcd", "abcd");

        objective.setDisplayName(" ");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.getScore("§d§8").setScore(16);

        /*LEVEL*/
        objective.getScore("§1§f" + Locale.get(player, "scoreboard_money")).setScore(15);
        Team moneyLevel = scoreboard.registerNewTeam("money");
        moneyLevel.addEntry("§d§9");
        moneyLevel.setPrefix("§a" + monstersPlayer.getMoney());
        moneyLevel.setSuffix("§2$");
        objective.getScore("§d§9").setScore(14);

        objective.getScore("§d").setScore(12);

        /*LEVEL*/
        objective.getScore("§1§f" + Locale.get(player, "scoreboard_level")).setScore(11);
        Team levelTeam = scoreboard.registerNewTeam("level");
        levelTeam.addEntry("§d§4");
        levelTeam.setPrefix("§6" + monstersPlayer.getLevel());
        objective.getScore("§d§4").setScore(10);
        objective.getScore("§e").setScore(9);

        /*WAVE*/
        objective.getScore("§4§f" + Locale.get(player, "scoreboard_wave")).setScore(8);
        Team waveTeam = scoreboard.registerNewTeam("wave");
        waveTeam.addEntry("§9§f");
        waveTeam.setPrefix("§e" + Monsters.getInstance().getGame().getWave().getWave());
        objective.getScore("§9§f").setScore(7);
        objective.getScore("§8").setScore(6);

        /*REMAINING MONSTERS*/
        objective.getScore("§2§f" + Locale.get(player, "scoreboard_monsters-left")).setScore(5);
        Team remainingTeam = scoreboard.registerNewTeam("left");
        remainingTeam.addEntry("§d§5");
        remainingTeam.setPrefix("§e" + Monsters.getInstance().getGame().getWave().getRemaining());
        objective.getScore("§d§5").setScore(4);
        objective.getScore("§4                      ").setScore(3);

        /*KILLS*/
        objective.getScore("§3§f" + Locale.get(player, "scoreboard_kills")).setScore(2);
        Team killsTeam = scoreboard.registerNewTeam("kills");
        killsTeam.addEntry("§9§7");
        killsTeam.setPrefix("§b" + Monsters.getInstance().getGame().getMonstersPlayer().getKills());
        objective.getScore("§9§7").setScore(1);

        player.setScoreboard(scoreboard);

        boards.put(scoreboard, player);
        player.setScoreboard(scoreboard);
        GameAPI.getInstance().setTag();
    }

    public void update() {
        String message = "ANIMALSHOMELAND.NET   ";

        shown = shown + message.charAt(letter);

        if(shown.length() >= 15) {
            shown = shown.substring(1);
        }

        letter++;
        if(letter >= message.length()) {
            letter = 0;
        }

        for (org.bukkit.scoreboard.Scoreboard board : boards.keySet()) {
            Objective objective = board.getObjective("abcd");

            objective.setDisplayName("§e§l" + shown);
        }
    }

    public static void update(String type) {
        MonstersPlayer monstersPlayer = Monsters.getInstance().getGame().getMonstersPlayer();
        Player player = monstersPlayer.getPlayer();
        org.bukkit.scoreboard.Scoreboard board = player.getScoreboard();

        Objective objective = board.getObjective("abcd");
        Wave wave = Monsters.getInstance().getGame().getWave();

        if(type.equals("level")) {
            Team levelTeam = board.getTeam("level");
            levelTeam.setPrefix("§e" + monstersPlayer.getLevel());
            objective.getScore("§d§4").setScore(10);
        } else if(type.equals("wave")) {
            Team waveTeam = board.getTeam("wave");
            waveTeam.setPrefix("§b" + wave.getWave());
            objective.getScore("§9§f").setScore(7);
        } else if(type.equals("kills")) {
            Team killsTeam = board.getTeam("kills");
            killsTeam.setPrefix("§b" + monstersPlayer.getKills());
            objective.getScore("§9§7").setScore(1);

            Team remainingTeam = board.getTeam("left");
            remainingTeam.setPrefix("§e" + wave.getRemaining());
            objective.getScore("§d§5").setScore(4);
            update("money");
        } else if(type.equals("money")) {
            Team moneyTeam = board.getTeam("money");
            moneyTeam.setPrefix("§a" + monstersPlayer.getMoney());
            objective.getScore("§d§9").setScore(14);
        }
    }
}
