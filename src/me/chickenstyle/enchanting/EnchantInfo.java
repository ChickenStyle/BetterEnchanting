package me.chickenstyle.enchanting;

import me.chickenstyle.enchanting.prices.Price;

import java.util.ArrayList;
import java.util.List;

public class EnchantInfo {

    private final boolean enabled;

    private final int startingLevel;

    private final int maxLevel;

    private final String friendlyName;

    private final List<Price> startingPrices;

    private final List<Price> pricesPerLevel;

    public EnchantInfo(boolean enabled,int startingLevel,int maxLevel,String friendlyName,List<Price> startingPrices,List<Price> pricesPerLevel) {
        this.enabled = enabled;
        this.startingLevel = startingLevel;
        this.maxLevel = maxLevel;
        this.friendlyName = friendlyName;
        this.startingPrices = startingPrices;
        this.pricesPerLevel = pricesPerLevel;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getStartingLevel() {
        return startingLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public List<Price> getStartingPrices() {
        return new ArrayList<>(startingPrices);
    }

    public List<Price> getPricesPerLevel() {
        return new ArrayList<>(pricesPerLevel);
    }
}
