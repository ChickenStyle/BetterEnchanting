package me.chickenstyle.enchanting.menumanager;

import me.chickenstyle.enchanting.BetterEnchanting;
import me.chickenstyle.enchanting.EnchantInfo;
import me.chickenstyle.enchanting.EnchantManager;
import me.chickenstyle.enchanting.configs.EnchantmentsConfig;
import me.chickenstyle.enchanting.configs.GUIConfig;
import me.chickenstyle.enchanting.utils.Function;
import me.chickenstyle.enchanting.utils.PageUtils;
import me.chickenstyle.enchanting.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class EnchantmentMenu extends Menu {

    private int pageNumber;
    private List<Integer> indexes;

    public EnchantmentMenu(String title, int size) {
        super(Bukkit.createInventory(null, size,Utils.color(title)));
        pageNumber = 1;
    }

    public int getItemPlaceSlot() {
        HashMap<Integer,Button> buttons = getButtons();

        for (int slot: buttons.keySet()) {
            if (buttons.get(slot) instanceof FunctionButton) {
                if (((FunctionButton) buttons.get(slot)).getFunction() == Function.ITEMPLACE) {
                    return slot;
                }
            }
        }
        throw new RuntimeException("No Item Slot Place In GUI!");
    }

    public List<Enchantment> getEnchantmentsFor(ItemStack item) {
        loadIndexes();
        List<Enchantment> list = new ArrayList<>();
        BetterEnchanting instance = BetterEnchanting.getPlugin(BetterEnchanting.class);

        if (item == null || item.getType() == Material.AIR) return list;

        boolean allowCurse = instance.getConfig().getBoolean("allowCurseEnchantments");
        boolean ignoreItemType = instance.getConfig().getBoolean("ignoreItemType");
        boolean ignoreConflicts = instance.getConfig().getBoolean("ignoreConflicts");

        for (Enchantment enchantment: Enchantment.values()) {

            if (!allowCurse && enchantment.isCursed()) continue;
            if (!ignoreItemType) {
                if (!enchantment.canEnchantItem(item)) continue;
            }

            list.add(enchantment);
        }

        return list;
    }

    public void loadEnchantmentItems(List<ItemStack> enchants,int page) {
        loadIndexes();
        //Clear Slots
        clearEnchantmentItems(indexes);
        enchants = PageUtils.getPageItems(enchants,page,indexes.size());
        int counter = 0;


        for (ItemStack enchantItem : enchants) {
            getInventory().setItem(indexes.get(counter),enchantItem);
            counter++;
            System.out.println();
        }

        this.pageNumber = page;
    }

    public List<ItemStack> getEnchantmentsItemsFor(ItemStack item) {
        loadIndexes();
        List<Enchantment> enchantments = getEnchantmentsFor(item);
        List<ItemStack> enchantmentItems = new ArrayList<>();

        if (item == null || item.getType() == Material.AIR) return enchantmentItems;
        
        for (Enchantment enchantment : enchantments) {
            GUIConfig guiConfig = BetterEnchanting.getInstance().getGUIConfig();
            ItemStack ench = new ItemStack(Material.valueOf((String)guiConfig.get("enchantingGUI.bookItem.material")));
            ench.setAmount((int)guiConfig.get("enchantingGUI.bookItem.amount"));
            ItemMeta meta = ench.getItemMeta();

            meta.setDisplayName(replacePlaceHolders((String) guiConfig.get("enchantingGUI.bookItem.displayName"),enchantment));

            List<String> lore = (List<String>) guiConfig.get("enchantingGUI.bookItem.lore");
            meta.setLore(lore.stream().map(line -> replacePlaceHolders(line,enchantment)).collect(Collectors.toList()));
            ench.setItemMeta(meta);

            ench = BetterEnchanting.getInstance().getNMSHandler().addStringTag(ench,"BetterEnchantment",enchantment.getName());

            enchantmentItems.add(ench);
        }



        return enchantmentItems;




    }

    private String replacePlaceHolders(String text,Enchantment enchantment) {
        EnchantManager manager = BetterEnchanting.getInstance().getEnchantManager();
        EnchantInfo info = manager.getEnchantInfo(enchantment);

        text = text.replace("%enchantment_name%",info.getFriendlyName())
                .replace("%starting_level%",info.getStartingLevel() + "")
                .replace("%max_level%",info.getMaxLevel() + "");
        return Utils.color(text);
    }

    public void clearEnchantmentItems(){
        loadIndexes();
        clearEnchantmentItems(indexes);
    }

    public List<Integer> getIndexes() {
        return indexes;
    }

    public int getCurrentPage() {
        return pageNumber;
    }

    private void clearEnchantmentItems(List<Integer> indexes) {
        for (int slot : indexes) {
            getInventory().setItem(slot,new ItemStack(Material.AIR));
        }
    }

    private void loadIndexes() {
        Map<Integer,Button> buttons = getButtons();
        indexes = new ArrayList<>();

        for (Integer index : buttons.keySet()) {
            if (buttons.get(index) instanceof FunctionButton) {
                FunctionButton button = (FunctionButton) buttons.get(index);
                if (button.getFunction() == Function.ENCHANTMENTSPLACE) {
                    indexes.add(index);
                }
            }
        }
    }
}
