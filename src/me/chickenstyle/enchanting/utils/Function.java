package me.chickenstyle.enchanting.utils;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import me.chickenstyle.enchanting.BetterEnchanting;
import me.chickenstyle.enchanting.menumanager.EnchantmentMenu;
import me.chickenstyle.enchanting.menumanager.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public enum Function {

    CONFIRMBUTTON {
        @Override
        public void interact(Menu menu, InventoryClickEvent e) {
            System.out.println("CONFIRM BUTTON");
            e.setCancelled(true);
        }
    },
    ITEMPLACE {
        @Override
        public void interact(Menu menu, InventoryClickEvent e) {
            if (!(menu instanceof EnchantmentMenu)) return;
            EnchantmentMenu enchantmentMenu = (EnchantmentMenu)menu;

            new BukkitRunnable() {

                @Override
                public void run() {
                    Player player = (Player) e.getWhoClicked();
                    ItemStack placedItem = e.getClickedInventory().getItem(e.getSlot());
                    if (placedItem == null) {
                        enchantmentMenu.clearEnchantmentItems();
                        return;
                    }


                    if (placedItem.getAmount() > 1) {
                        player.sendMessage(Message.INVALID_AMOUNT.getMSG());
                        e.setCancelled(true);
                    } else {
                        List<ItemStack> enchants = enchantmentMenu.getEnchantmentsItemsFor(placedItem);
                        enchantmentMenu.loadEnchantmentItems(enchants,1);
                    }


                }
            }.runTaskLater(BetterEnchanting.getInstance(),0);
        }
    },
    ENCHANTMENTSPLACE {
        @Override
        public void interact(Menu menu, InventoryClickEvent e) {
            ItemStack book = e.getClickedInventory().getItem(e.getSlot());

            if (book == null  || book.getType() == Material.AIR) {
                e.setCancelled(true);
                return;
            }
            System.out.println("Check");


            e.setCancelled(true);
        }
    },
    NEXTBUTTON {
        @Override
        public void interact(Menu menu, InventoryClickEvent e) {
            if (menu instanceof EnchantmentMenu) {
                EnchantmentMenu enchantmentMenu = (EnchantmentMenu) menu;
                ItemStack item = enchantmentMenu.getInventory().getItem(enchantmentMenu.getItemPlaceSlot());
                List<ItemStack> enchants = enchantmentMenu.getEnchantmentsItemsFor(item);

                if (PageUtils.isPageValid(enchants,enchantmentMenu.getCurrentPage() + 1,enchantmentMenu.getIndexes().size())) {
                    enchantmentMenu.loadEnchantmentItems(enchants,enchantmentMenu.getCurrentPage() + 1);
                }

            }
            e.setCancelled(true);
        }
    },
    PREVIOUSBUTTON {
        @Override
        public void interact(Menu menu, InventoryClickEvent e) {
            if (menu instanceof EnchantmentMenu) {
                EnchantmentMenu enchantmentMenu = (EnchantmentMenu) menu;
                ItemStack item = enchantmentMenu.getInventory().getItem(enchantmentMenu.getItemPlaceSlot());
                List<ItemStack> enchants = enchantmentMenu.getEnchantmentsItemsFor(item);

                if (PageUtils.isPageValid(enchants,enchantmentMenu.getCurrentPage() - 1,enchantmentMenu.getIndexes().size())) {
                    enchantmentMenu.loadEnchantmentItems(enchants,enchantmentMenu.getCurrentPage() - 1);
                }

            }
            e.setCancelled(true);
        }
    }
    ;

    public abstract void interact(Menu menu, InventoryClickEvent e);
}
