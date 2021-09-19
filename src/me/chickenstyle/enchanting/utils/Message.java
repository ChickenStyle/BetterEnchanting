package me.chickenstyle.enchanting.utils;

import me.chickenstyle.enchanting.BetterEnchanting;

public enum Message {

    INVALID_AMOUNT("invalidAmount");



    private String path;
    private BetterEnchanting instance;

    Message(String path) {
        this.path = path;
        this.instance = BetterEnchanting.getPlugin(BetterEnchanting.class);
    }

    public String getMSG() {
        return Utils.color(instance.getLanguageConfig().getMessage(path));
    }
}
