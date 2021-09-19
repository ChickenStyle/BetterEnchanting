package me.chickenstyle.enchanting.configs;

import me.chickenstyle.enchanting.BetterEnchanting;
import me.chickenstyle.enchanting.EnchantInfo;
import me.chickenstyle.enchanting.prices.Price;
import me.chickenstyle.enchanting.utils.FriendlyEnchant;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EnchantmentsConfig {


    /*
     *  enchantments:
     * 		{ENCHANTMENT_NAME}:
     * 			enabled: true
     * 			friendlyName: {name}
     *			startingLevel: {startingLevel}
     * 			maxLevel: {maxLevel}
     * 			startingPrice:[]
     *			pricePerLevel: []
     *
     *
     *
     */

    private File file;
    private YamlConfiguration config;

    public EnchantmentsConfig(BetterEnchanting main) {
        file = new File(main.getDataFolder(), "Enchantments.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                config = YamlConfiguration.loadConfiguration(file);
                try {
                    config.save(file);
                    config = YamlConfiguration.loadConfiguration(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        config = YamlConfiguration.loadConfiguration(file);
        loadDefaultEnchants();
    }

    private void loadDefaultEnchants() {
        for (Enchantment ench:Enchantment.values()) {
            if (!config.contains("enchantments." + ench.getName())) {
                String path = "enchantments." + ench.getName() + ".";

                config.set(path + "enabled",true);


                config.set(path + "friendlyName",
                        FriendlyEnchant.hasEnchant(ench) ?
                                FriendlyEnchant.valueOf(ench.getName()).getFriendlyName() : ench.getName());

                config.set(path + "startingLevel",ench.getStartLevel());
                config.set(path + "maxLevel",ench.getMaxLevel());
                config.set(path + "startingPrice",new ArrayList<>());
                config.set(path + "pricePerLevel",new ArrayList<>());
                reloadConfig();
            }
        }
    }

    public EnchantInfo getInfo(Enchantment enchantment) {
        String path = "enchantments." + enchantment.getName() + ".";
        boolean enabled = config.getBoolean(path + "enabled");
        int startingLevel = config.getInt(path + "startingLevel");
        int maxLevel = config.getInt(path + "maxLevel");
        String friendlyName = config.getString(path + "friendlyName");
        ArrayList<Price> startingPrices = (ArrayList<Price>) config.get(path + "startingPrice");
        ArrayList<Price> pricesPerLevel = (ArrayList<Price>) config.get(path + "pricePerLevel");

        return new EnchantInfo(enabled,startingLevel,maxLevel,friendlyName,startingPrices,pricesPerLevel);

    }

    public void reloadConfig() {
        try {
            config.save(file);
            config = YamlConfiguration.loadConfiguration(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}