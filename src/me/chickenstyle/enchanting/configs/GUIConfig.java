package me.chickenstyle.enchanting.configs;

import me.chickenstyle.enchanting.BetterEnchanting;
import me.chickenstyle.enchanting.menumanager.Button;
import me.chickenstyle.enchanting.menumanager.EnchantmentMenu;
import me.chickenstyle.enchanting.menumanager.FunctionButton;
import me.chickenstyle.enchanting.menumanager.Menu;
import me.chickenstyle.enchanting.utils.Function;
import me.chickenstyle.enchanting.utils.ItemStackX;
import me.chickenstyle.enchanting.utils.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GUIConfig {

    private File file;
    private YamlConfiguration config;

    public GUIConfig(BetterEnchanting main) {

        main.saveResource("gui.yml",false);
        file = new File(main.getDataFolder(), "gui.yml");
        config = YamlConfiguration.loadConfiguration(file);

        if (config.getString("items.GRAY_GLASS.material").equalsIgnoreCase("{REPLACE_MATERIAL}")) {
            config.set("items.GRAY_GLASS.material", ItemStackX.GRAY_STAINED_GLASS_PANE.getItemStack().getType().toString());
            if (ItemStackX.getServerVersion() < 13) {
                config.set("items.GRAY_GLASS.data",7);
            } else {
                config.set("items.GRAY_GLASS.data",null);
            }

        }

        if (config.getString("items.CONFIRM_BUTTON.material").equalsIgnoreCase("{REPLACE_MATERIAL}")) {
            config.set("items.CONFIRM_BUTTON.material", ItemStackX.GRAY_STAINED_GLASS_PANE.getItemStack().getType().toString());
            if (ItemStackX.getServerVersion() < 13) {
                config.set("items.CONFIRM_BUTTON.data",13);
            } else {
                config.set("items.CONFIRM_BUTTON.data",null);
            }

        }

        reloadConfig();

    }

    public EnchantmentMenu loadGUI() {
        EnchantmentMenu menu = new EnchantmentMenu(getString("enchantingGUI.title"),getInt("enchantingGUI.slotAmount"));

        HashMap<Character, Button> buttons = new HashMap<>();
        List<String> items = getStringList("enchantingGUI.items");


        for (String info:items) {
            String[] data = info.split(":");

            buttons.put(data[0].charAt(0),loadButton(data[1]));
        }

        int counter = 0;
        for (String line:getStringList("enchantingGUI.pattern")) {
            for (String character:line.split("")){
                menu.setButton(counter,buttons.get(character.charAt(0)));
                counter++;
            }
        }

        return menu;
    }

    private Button loadButton(String itemName) {
        String path = "items." + itemName + ".";
        int data = get(path + "data") == null ? 0 : getInt(path + "data");
        ItemStack item = new ItemStack(Material.valueOf(getString(path + "material").toUpperCase()),
                getInt(path + "amount"),
                Byte.parseByte(data + ""));

        ItemMeta meta = item.getItemMeta();

        if (meta != null) {



            meta.setDisplayName(Utils.color(getString(path + "displayName")));

            List<String> lore = getStringList(path + "lore").stream().map(s -> Utils.color(s)).collect(Collectors.toList());

            if (!lore.isEmpty()) {
                meta.setLore(lore);
            }

            item.setItemMeta(meta);
        }

        if (get(path + "function") != null) {

            Function function;
            try {
                function = Function.valueOf(getString(path + "function").toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException("Invalid Function Exception: " + getString(path + "function"));
            }

            return new FunctionButton(item,function);

        } else {
            return new Button(item) {
                @Override
                public void onClick(Menu menu, InventoryClickEvent event) {
                    event.setCancelled((boolean)get(path + "disableClickEvent"));
                }
            };
        }
    }

    public Object get(String path) {
        return config.get(path);
    }

    private int getInt(String path) {
        return (int)get(path);
    }

    private String getString(String path) {
        return (String) get(path);
    }

    private List<String> getStringList(String path) {
        return (ArrayList<String>) get(path);
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
