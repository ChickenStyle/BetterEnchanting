package me.chickenstyle.enchanting;

import me.chickenstyle.enchanting.menumanager.Menu;
import me.chickenstyle.enchanting.menumanager.MenuHandler;
import me.chickenstyle.enchanting.utils.ItemStackX;
import net.minecraft.server.Main;
import net.minecraft.server.v1_9_R1.EnchantmentArrowKnockback;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class EnchantManager {
    private BetterEnchanting main;
    private List<Location> blockLocs;
    private HashMap<Enchantment, EnchantInfo> enchants;

    public EnchantManager(BetterEnchanting main) {
        this.main = main;
        loadEnchantsInfo();
        blockLocs = new LinkedList<>();

    }

    public void loadEnchantsInfo() {
        enchants = new HashMap<>();
        for (Enchantment enchantment : Enchantment.values()) {
            enchants.put(enchantment,main.getEnchantmentsConfig().getInfo(enchantment));
        }
    }

    public EnchantInfo getEnchantInfo(Enchantment ench) {
        return enchants.get(ench);
    }

    public Listener getListener() {
        return new Listener() {
            @EventHandler
            public void onPlayerBlockClick(PlayerInteractEvent e) {
                if (e.getClickedBlock() == null) return;
                if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
                if (e.getClickedBlock().getType() != ItemStackX.ENCHANTING_TABLE.getItemStack().getType()) return;

                if (main.getConfig().getBoolean("replaceDefaultEnchantingTable")) {

                    e.setCancelled(true);
                    Menu menu = main.getGUIConfig().loadGUI();
                    MenuHandler.getInstance().openMenu(e.getPlayer(),menu);

                } else {
                    if (!blockLocs.contains(e.getClickedBlock().getLocation())) return;

                }


            }
        };
    }
}
