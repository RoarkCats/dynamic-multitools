package com.roarkcats.dynamicmultitools.item.custom;

import com.roarkcats.dynamicmultitools.component.ModDataComponent;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;

public class DynamicTieredItem extends Item {

    public DynamicTieredItem(int durability, int enchantability, Ingredient repairMaterial, @Nullable DyedItemColor color, Item.Properties properties) {
        super(properties
                .durability(durability)
                .component(ModDataComponent.ENCHANTABILITY.get(), enchantability)
                .component(ModDataComponent.REPAIR_MATERIAL.get(), repairMaterial)
                .component(DataComponents.DYED_COLOR, color)
        );
    }
    public DynamicTieredItem(Tier tier, @Nullable DyedItemColor color, Item.Properties properties) {
        this(tier.getUses(), tier.getEnchantmentValue(), tier.getRepairIngredient(), color, properties);
    }


    // Helper
    public static DyedItemColor color(int rgb) {
        return new DyedItemColor(rgb, false);
    }


    @Override
    public int getEnchantmentValue() {
        return this.components().getOrDefault(ModDataComponent.ENCHANTABILITY.get(), 15);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        Ingredient repair_item = toRepair.getComponents().getOrDefault(ModDataComponent.REPAIR_MATERIAL.get(), Ingredient.EMPTY);
        return repair_item.test(repair) || super.isValidRepairItem(toRepair, repair);
    }
}
