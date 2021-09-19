package me.chickenstyle.enchanting;

import me.chickenstyle.enchanting.configs.EnchantmentsConfig;
import me.chickenstyle.enchanting.configs.GUIConfig;
import me.chickenstyle.enchanting.configs.LanguageConfig;
import me.chickenstyle.enchanting.menumanager.Menu;
import me.chickenstyle.enchanting.menumanager.MenuHandler;
import me.chickenstyle.enchanting.nms.NMSHandler;
import me.chickenstyle.enchanting.utils.ItemStackX;
import me.chickenstyle.enchanting.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.stream.Collectors;

public class BetterEnchanting extends JavaPlugin{

    private static BetterEnchanting instance;
    private NMSHandler nmsHandler;
    private EnchantmentsConfig enchConfig;
    private LanguageConfig langConfig;
    private GUIConfig guiConfig;
    private EnchantManager enchantManager;

    @Override
    public void onEnable() {
        if (!detectNMSVersion()) {
            getServer().getPluginManager().disablePlugin(this);
            System.out.println(Utils.color("&4BetterEnchanting isn't supported on this version."));
            return;
        }

        instance = this;

        this.getConfig().options().copyDefaults();
        saveDefaultConfig();


        enchConfig = new EnchantmentsConfig(this);
        langConfig = new LanguageConfig(this);
        guiConfig = new GUIConfig(this);
        enchantManager = new EnchantManager(this);

        getServer().getPluginManager().registerEvents(MenuHandler.getInstance().getListeners(), this);
        getServer().getPluginManager().registerEvents(enchantManager.getListener(),this);


        if (!getConfig().getBoolean("replaceDefaultEnchantingTable")
            && getConfig().getBoolean("specialEnchantingBlock.item.recipe.loadRecipe")) {
            loadCustomBlockRecipe();
        }


        System.out.println(Utils.color("&aBetterEnchanting has been loaded"));

    }

    @Override
    public void onDisable() {
        MenuHandler.getInstance().closeAll();
    }

    private void loadCustomBlockRecipe() {
        ItemStack item = ItemStackX.ENCHANTING_TABLE.getItemStack();
        item = nmsHandler.addStringTag(item,"BetterEnchanting","Poggers");
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.color(getConfig().getString("specialEnchantingBlock.item.displayName")));

        List<String> lore = (List<String>) getConfig().get("specialEnchantingBlock.item.lore");
        lore = lore.stream().map(line -> Utils.color(line)).collect(Collectors.toList());
        meta.setLore(lore);
        item.setItemMeta(meta);

        ShapedRecipe recipe;
        if (Bukkit.getVersion().contains("1.8") ||
                Bukkit.getVersion().contains("1.9") ||
                Bukkit.getVersion().contains("1.10") ||
                Bukkit.getVersion().contains("1.11")) {
            recipe = new ShapedRecipe(item);
        } else {
            recipe = new ShapedRecipe(new NamespacedKey(this,"CustomEnchantingTable"),item);
        }

        List<String> shape = (List<String>) getConfig().get("specialEnchantingBlock.item.recipe.grid");

        recipe.shape(shape.toArray(new String[0]));

        for (String info:(List<String>)getConfig().get("specialEnchantingBlock.item.recipe.ingredients")) {
            String[] data = info.split(":");

            recipe.setIngredient(data[0].charAt(0), Material.valueOf(data[1]));

        }

        Bukkit.addRecipe(recipe);
    }


    private boolean detectNMSVersion() {

        String version;

        try {

            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        } catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
            return false;
        }
        version = version.substring(1);

        try {
            nmsHandler = (NMSHandler) Class.forName("me.chickenstyle.enchanting.nms.Handler_" +version).newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            return false;
        }

        return true;
    }



    public NMSHandler getNMSHandler() {
        return nmsHandler;
    }

    public EnchantmentsConfig getEnchantmentsConfig() {
        return enchConfig;
    }

    public LanguageConfig getLanguageConfig() {
        return langConfig;
    }

    public EnchantManager getEnchantManager() {
        return enchantManager;
    }

    public GUIConfig getGUIConfig() {
        return guiConfig;
    }

    public static BetterEnchanting getInstance() {
        return instance;
    }
}
