package net.apotox.monsters;

import lombok.Getter;
import net.apotox.gameapi.GameAPI;
import net.apotox.gameapi.GameConfig;
import net.apotox.gameapi.GamePlugin;
import net.apotox.gameapi.language.LanguageHandler;
import net.apotox.monsters.game.Game;
import net.apotox.monsters.utilities.LocationManager;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;

import java.io.IOException;

public class Monsters extends GamePlugin {

    @Getter
    static Monsters instance;
    @Getter
    private String path;
    @Getter
    private Game game;
    @Getter
    private LocationManager locationManager;

    @Override
    public void onPluginEnable() throws IOException {
        instance = this;
        path = "/home/networksync/monsters/";
        GameAPI.getInstance().setDebugMode(true);

        GameConfig gameConfig = new GameConfig(this);
        gameConfig.setPath(getPath());
        setGameConfig(gameConfig);

        gameConfig.setLanguageHandler(new LanguageHandler(this));
        gameConfig.setConfigFile(path + "config.yml");

        locationManager = new LocationManager();
        game = new Game();
        game.getGameMap().init();

        getAutoRegister().registerCommands("net.apotox.monsters.commands");
        getAutoRegister().registerListeners("net.apotox.monsters.listener");

        Bukkit.getWorlds().get(0).setDifficulty(Difficulty.HARD);
    }

    @Override
    public void onPluginDisable() {

    }
}
