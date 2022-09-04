package me.cubecrafter.migrator.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum LayoutItem {

    WOOL("blocks-category.category-content.wool", "wool"),
    CLAY("blocks-category.category-content.clay", "hardened_clay"),
    GLASS("blocks-category.category-content.glass", "blast-proof_glass"),
    END_STONE("blocks-category.category-content.stone", "end_stone"),
    LADDER("blocks-category.category-content.ladder", "ladder"),
    WOOD("blocks-category.category-content.wood", "oak_wood_planks"),
    OBSIDIAN("blocks-category.category-content.obsidian", "obsidian"),
    STONE_SWORD("melee-category.category-content.stone-sword", "stone_sword"),
    IRON_SWORD("melee-category.category-content.iron-sword", "iron_sword"),
    DIAMOND_SWORD("melee-category.category-content.diamond-sword", "diamond_sword"),
    KNOCKBACK_STICK("melee-category.category-content.stick", "stick_(knockback_i)"),
    CHAINMAIL_ARMOR("armor-category.category-content.chainmail", "chainmail_boots"),
    IRON_ARMOR("armor-category.category-content.iron-armor", "iron_boots"),
    DIAMOND_ARMOR("armor-category.category-content.diamond-armor", "diamond_boots"),
    SHEARS("tools-category.category-content.shears", "shears"),
    WOODEN_PICKAXE("tools-category.category-content.pickaxe", "wooden_pickaxe"),
    WOODEN_AXE("tools-category.category-content.axe", "wooden_axe"),
    ARROW("ranged-category.category-content.arrow", "arrow"),
    BOW_ONE("ranged-category.category-content.bow1", "bow"),
    BOW_TWO("ranged-category.category-content.bow2", "bow_(power_i)"),
    BOW_THREE("ranged-category.category-content.bow3", "bow_(power_i__punch_i)"),
    SPEED_POTION("potions-category.category-content.speed-potion", "speed_ii_potion_(45_seconds)"),
    JUMP_POTION("potions-category.category-content.jump-potion", "jump_v_potion_(45_seconds)"),
    INVISIBILITY_POTION("potions-category.category-content.invisibility", "invisibility_potion_(30_seconds)"),
    GOLDEN_APPLE("utility-category.category-content.golden-apple", "golden_apple"),
    BEDBUG("utility-category.category-content.bedbug", "bedbug"),
    DREAM_DEFENDER("utility-category.category-content.dream-defender", "dream_defender"),
    FIREBALL("utility-category.category-content.fireball", "fireball"),
    TNT("utility-category.category-content.tnt", "tnt"),
    ENDER_PEARL("utility-category.category-content.ender-pearl", "ender_pearl"),
    WATER_BUCKET("utility-category.category-content.water-bucket", "water_bucket"),
    BRIDGE_EGG("utility-category.category-content.bridge-egg", "bridge_egg"),
    MAGIC_MILK("utility-category.category-content.magic-milk", "magic_milk"),
    SPONGE("utility-category.category-content.sponge", "sponge"),
    POPUP_TOWER("utility-category.category-content.tower", "compact_pop-up_tower");

    private final String category;
    private final String id;

    public static LayoutItem matchItem(String item) {
        return Arrays.stream(values()).filter(layoutItem -> layoutItem.getId().equals(item)).findAny().orElse(null);
    }

}
