package net.animalshomeland.monsters;

import lombok.Getter;
import net.animalshomeland.gameapi.GameAPI;
import net.animalshomeland.gameapi.GameConfig;
import net.animalshomeland.gameapi.GamePlugin;
import net.animalshomeland.gameapi.language.LanguageHandler;
import net.animalshomeland.monsters.game.Game;
import net.animalshomeland.monsters.utilities.LocationManager;
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

        getAutoRegister().registerCommands("net.animalshomeland.monsters.commands");
        getAutoRegister().registerListeners("net.animalshomeland.monsters.listener");

        Bukkit.getWorlds().get(0).setDifficulty(Difficulty.HARD);
    }

    @Override
    public void onPluginDisable() {

    }
}
