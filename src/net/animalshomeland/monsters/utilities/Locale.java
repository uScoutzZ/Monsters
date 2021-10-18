package net.animalshomeland.monsters.utilities;

import net.animalshomeland.gameapi.user.User;
import net.animalshomeland.monsters.Monsters;
import org.bukkit.entity.Player;

public class Locale {

    public static String get(Player player, String locale, Object... variables) {
        String message = Monsters.getInstance().getLanguageHandler().translate(User.getFromPlayer(player).getLanguage(), locale, new Object[0]);

        int i = 0;
        for(Object argument : variables) {
            message = message.replace("{" + i + "}", String.valueOf(variables[i]));
            i++;
        }
        return message;
    }
}
