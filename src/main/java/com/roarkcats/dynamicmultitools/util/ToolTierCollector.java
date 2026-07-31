package com.roarkcats.dynamicmultitools.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;

import java.util.HashSet;
import java.util.Set;

public class ToolTierCollector {

    private static final Set<Tier> discoveredTiers = new HashSet<>();

    public static Set<Tier> getAllTiers() {
        // Must be run first time post-initialization
        if (discoveredTiers.isEmpty()) {discoverTiers();}
        return discoveredTiers;
    }

    private static void discoverTiers() {
        // Scan all items registered in the game for Tiers
        for (Item item : BuiltInRegistries.ITEM) {
            if (item instanceof TieredItem tieredItem) {
                Tier tier = tieredItem.getTier();
                discoveredTiers.add(tier);
            }
        }
    }
}
