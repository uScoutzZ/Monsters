package net.animalshomeland.monsters.game;

import lombok.Getter;
import lombok.Setter;
import net.animalshomeland.gameapi.util.TimeUtilities;
import net.animalshomeland.monsters.utilities.Locale;
import net.animalshomeland.monsters.Monsters;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class GameCountdown {

    @Getter @Setter
    private int lobbyTime;
    @Getter
    private int endTime, defaultLobbyTime, defaultEndTime;
    @Getter
    private BukkitTask lobbyTask, endTask;

    public GameCountdown() {
        defaultLobbyTime = Monsters.getInstance().getGameConfig().getConfigFile().getInt("lobby-timer");
        defaultEndTime = Monsters.getInstance().getGameConfig().getConfigFile().getInt("end-timer");
        lobbyTime = defaultLobbyTime;
        endTime = defaultEndTime;
    }

    public void startLobbyCounter(Player player) {
        Monsters.getInstance().getGame().setPlayer(player);
        Monsters.getInstance().getGame().setMonstersPlayer(new MonstersPlayer(player));
        if(lobbyTime != 0 && Monsters.getInstance().getGame().getGameState() == GameState.LOBBY) {
            lobbyTask = new BukkitRunnable() {
                @Override
                public void run() {
                    if(lobbyTime > 0) {
                        if(lobbyTime == defaultLobbyTime || lobbyTime == 20 || lobbyTime == 15 || lobbyTime == 10 || lobbyTime <= 5) {
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0F, 1.0F);
                            String number = "plural";
                            if(lobbyTime == 1) {
                                number = "singular";
                            }
                            player.sendMessage(Locale.get(player, "gamestart_" + number, String.valueOf(lobbyTime)));
                        }
                        lobbyTime--;
                    } else {
                        cancel();
                        Monsters.getInstance().getGame().start();
                    }
                }
            }.runTaskTimer(Monsters.getInstance(), 0, 20);
        }
    }

    public void startEndCounter() {
        Player player = Monsters.getInstance().getGame().getPlayer();
        MonstersPlayer monstersPlayer = Monsters.getInstance().getGame().getMonstersPlayer();
        if(endTime != 0 && Monsters.getInstance().getGame().getGameState() == GameState.END) {
            endTask = new BukkitRunnable() {
                @Override
                public void run() {
                    if(endTime > 0) {
                        if(endTime == defaultEndTime || endTime == 20 || endTime == 15 || endTime == 10 || endTime <= 5) {
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0F, 1.0F);
                            String number = "plural";
                            if(endTime == 1) {
                                number = "singular";
                            }
                            player.sendMessage(Locale.get(player, "gameend_" + number, String.valueOf(endTime)));
                        }
                        endTime--;
                    } else {
                        cancel();
                        Monsters.getInstance().getGame().end(false);
                    }
                }
            }.runTaskTimer(Monsters.getInstance(), 0, 20);
            String wonOrNot = "§c✗";
            if(Monsters.getInstance().getGame().getWave().getWave() == Monsters.getInstance().getGame().getMaxWaves()
                    && Monsters.getInstance().getGame().getWave().getLiving().size() == 0) {
                wonOrNot = "§a✔";
            }
            player.sendMessage(Locale.get(player, "gameend_stats", monstersPlayer.getKills(),
                    monstersPlayer.getDeaths(), monstersPlayer.getLevel(), monstersPlayer.getEarnedMoney(),
                    TimeUtilities.getReamingTime(System.currentTimeMillis()-Monsters.getInstance().getGame().getBeginningTime()),
                    wonOrNot));
        }
    }
}
