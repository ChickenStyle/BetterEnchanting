package me.chickenstyle.enchanting.prices;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemPrice extends Price implements ICombinable{

    private final ItemStack item;
    private int amount;

    public ItemPrice(ItemStack item,int amount) {
        this.item = item.clone();
        this.amount = amount;
        item.setAmount(1);
    }


    @Override
    boolean canPayPrice(Player player) {
        int counter = 0;

        for (ItemStack item: player.getInventory().getContents()) {
            if (item.isSimilar(this.item)) {
                counter += item.getAmount();
            }
        }
        return counter >= amount;
    }

    @Override
    void payPrice(Player player) {
        removeMaterial(player,item,amount);
    }



    public ItemStack getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private void removeMaterial(Player player, ItemStack material, int amount) {
        for (ItemStack item:player.getInventory().getContents()) {
            if (item.isSimilar(material)) {
                if (item.getAmount() >= amount) {
                    item.setAmount(item.getAmount() - amount);
                    return;
                } else {
                    amount -= item.getAmount();
                    item.setAmount(0);
                }
            }
        }
    }

    @Override
    public void add(Price price) {
        if (!(price instanceof ItemPrice)) return;
        ItemPrice itemPrice = (ItemPrice) price;

        if (itemPrice.getItem().isSimilar(item)) {
            amount += item.getAmount();

        }
    }
}
