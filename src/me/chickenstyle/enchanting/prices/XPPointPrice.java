package me.chickenstyle.enchanting.prices;

import org.bukkit.entity.Player;

public class XPPointPrice extends Price implements ICombinable{

    private int xpPoints;

    public XPPointPrice(int xpPoints){
        this.xpPoints = xpPoints;
    }

    @Override
    boolean canPayPrice(Player player) {
        return player.getTotalExperience() >= xpPoints;
    }

    @Override
    void payPrice(Player player) {
        if (!canPayPrice(player)) return;
        player.setTotalExperience(player.getTotalExperience() - xpPoints);
    }

    public int getXpPoints() {
        return xpPoints;
    }

    public void setXpPoints(int xpPoints) {
        this.xpPoints = xpPoints;
    }

    @Override
    public void add(Price price) {
        if (!(price instanceof XPPointPrice))return;
        xpPoints += ((XPPointPrice)price).getXpPoints();
    }
}
