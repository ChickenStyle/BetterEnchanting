package me.chickenstyle.enchanting.prices;

import org.bukkit.entity.Player;

public abstract class Price {

    abstract boolean canPayPrice(Player player);

    abstract void payPrice(Player player);

}
