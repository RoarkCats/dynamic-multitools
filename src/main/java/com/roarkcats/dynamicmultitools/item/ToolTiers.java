package com.roarkcats.dynamicmultitools.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ToolTiers {
    public static final Tier DOLABRA_IRON = new SimpleTier(
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            // durability 1/3 more
            (int) (250*1.333),
            6.0F * 0.8F,
            2.0F,
            14,
            () -> Ingredient.of(Items.IRON_INGOT)
    );
}
