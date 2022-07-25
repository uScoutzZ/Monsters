package net.apotox.monsters.game.shop;

import net.apotox.monsters.Monsters;
import net.apotox.monsters.utilities.Locale;

public enum ShopCategory {

    MAIN,
    BLOCKS,
    UTILITIES,
    FOOD,
    POTIONS,
    WEAPONS,
    ENCH_WEAPONS,
    ARMOR,
    ENCH_ARMOR;

    public String getName() {
        return Locale.get(Monsters.getInstance().getGame().getPlayer(), "shop_" + toString().toLowerCase() + "_title");
    }
}
