package net.apotox.monsters.listener.player;

import net.apotox.gameapi.minigame.GameCountdown;
import net.apotox.monsters.Monsters;
import net.apotox.monsters.game.GameState;
import net.apotox.monsters.game.MonstersPlayer;
import net.apotox.monsters.utilities.Locale;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(Monsters.getInstance().getGame().getGameState() == GameState.LOBBY) {
            Monsters.getInstance().getGame().setPlayer(player);
            Monsters.getInstance().getGame().setMonstersPlayer(new MonstersPlayer(player));

            Monsters.getInstance().getGame().setGameCountdown(
                    new GameCountdown(Monsters.getInstance().getGameConfig().getConfigFile().getInt("lobby-timer"))
                            .setLocale(Locale.class, "gamestart")
                            .setSound(Sound.BLOCK_NOTE_BLOCK_BASS)
                            .setActionOnFinish(players -> {
                                Monsters.getInstance().getGame().start();
                            }).start());
        } else {
            Monsters.getInstance().getGame().getPlayer().hidePlayer(Monsters.getInstance(), player);
        }
    }
}
