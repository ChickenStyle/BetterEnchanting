package me.chickenstyle.enchanting.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Optional;

public enum ItemStackX {

    GRAY_STAINED_GLASS_PANE("STAINED_GLASS_PANE",7,"GRAY_STAINED_GLASS_PANE"),
    RED_STAINED_GLASS_PANE("STAINED_GLASS_PANE",14,"RED_STAINED_GLASS_PANE"),
    ENCHANTING_TABLE("ENCHANTMENT_TABLE","ENCHANTING_TABLE");


    private static final int version;

    private final String oldMaterial;
    private final String newMaterial;
    private final byte data;
    private final int versionChange;

    ItemStackX(String oldMaterial,int data,String newMaterial,int versionChange) {
        this.oldMaterial = oldMaterial;
        this.newMaterial = newMaterial;
        this.data = (byte) data;
        this.versionChange = versionChange;
    }

    ItemStackX(String oldMaterial,String newMaterial,int versionChange) {
        this(oldMaterial,(byte)0,newMaterial,versionChange);
    }

    //Default versionChange 13
    ItemStackX(String oldMaterial,int data,String newMaterial) {
        this(oldMaterial,data,newMaterial,13);
    }

    //Default versionChange 13
    ItemStackX(String oldMaterial,String newMaterial) {
        this (oldMaterial,(byte)0,newMaterial,13);
    }



    static {
        version = Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[1].split("-")[0]);
    }

    public ItemStack getItemStack() {
        return version >= versionChange ? new ItemStack(Material.valueOf(newMaterial)) : new ItemStack(Material.valueOf(oldMaterial),1,data);
    }

    public static Optional<ItemStackX> fromMaterial(String material) {
        return Arrays.stream(values()).filter(e -> e.getItemStack().getType().toString().equalsIgnoreCase(material)).findAny();
    }

    public static int getServerVersion() {
        return version;
    }



}

