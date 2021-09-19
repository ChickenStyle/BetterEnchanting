package me.chickenstyle.enchanting.menumanager;

import me.chickenstyle.enchanting.BetterEnchanting;
import me.chickenstyle.enchanting.utils.Function;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class FunctionButton extends Button{
    private Function function;

    public FunctionButton(ItemStack item, Function function) {
        super(item);
        this.function = function;
    }

    public FunctionButton(Function function,Material material, String name, String... lore) {
        super(material,name,lore);
        this.function = function;
    }

    @Override
    public void onClick(Menu menu, InventoryClickEvent event) {
        function.interact(menu,event);
    }

    public Function getFunction() {
        return function;
    }
}
