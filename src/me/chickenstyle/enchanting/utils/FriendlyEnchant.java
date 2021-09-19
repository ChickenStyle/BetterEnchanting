package me.chickenstyle.enchanting.utils;

import org.bukkit.enchantments.Enchantment;

import java.util.Arrays;
import java.util.Optional;

public enum FriendlyEnchant {

    ARROW_DAMAGE("Power"),
    ARROW_FIRE("Flame"),
    ARROW_INFINITE("Infinite"),
    ARROW_KNOCKBACK("Punch"),
    BINDING_CURSE("Curse of Binding",true),
    CHANNELING("Channeling"),
    DAMAGE_ALL("Sharpness"),
    DAMAGE_ARTHROPODS("Bane of Arthropods"),
    DAMAGE_UNDEAD("Smite"),
    DEPTH_STRIDER("Depth Strider"),
    DIG_SPEED("Efficiency"),
    DURABILITY("Unbreaking"),
    FIRE_ASPECT("Fire Aspect"),
    FROST_WALKER("Frost Walker"),
    IMPALING("Impaling"),
    KNOCKBACK("Knockback"),
    LOOT_BONUS_BLOCKS("Fortune"),
    LOOT_BONUS_MOBS("Looting"),
    LOYALTY("Loyalty"),
    LUCK("Luck of the Sea"),
    LURE("Lure"),
    MENDING("Mending"),
    MULTISHOT("Multishot"),
    OXYGEN("Respiration"),
    PIERCING("Piercing"),
    PROTECTION_ENVIRONMENTAL("Protection"),
    PROTECTION_EXPLOSIONS("Blast Protection"),
    PROTECTION_FALL("Feather Falling"),
    PROTECTION_FIRE("Fire Protection"),
    PROTECTION_PROJECTILE("Projectile Protection"),
    QUICK_CHARGE("Quick Charge"),
    RIPTIDE("Riptide"),
    SILK_TOUCH("Silk Touch"),
    SOUL_SPEED("Soul Speed"),
    SWEEPING_EDGE("Sweeping Edge"),
    THORNS("Thorns"),
    VANISHING_CURSE("Curse of Vanishing",true),
    WATER_WORKER("Aqua Affinity");


    private String friendlyName;
    private boolean isCurse;

    FriendlyEnchant(String friendlyName,boolean isCurse) {
        this.friendlyName = friendlyName;
        this.isCurse = isCurse;

    }

    FriendlyEnchant(String friendlyName) {
        this(friendlyName,false);
    }


    public static Optional<FriendlyEnchant> forEnchantment(Enchantment ench) {
        return Arrays.stream(values()).filter(e -> e.getFriendlyName().equals(ench.getName())).findAny();
    }

    public static boolean hasEnchant(Enchantment ench) {
        try {
            FriendlyEnchant enchant = FriendlyEnchant.valueOf(ench.getName());
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public boolean isCurse() {
        return isCurse;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public void setIsCurse(boolean isCurse) {
        this.isCurse = isCurse;
    }

}
