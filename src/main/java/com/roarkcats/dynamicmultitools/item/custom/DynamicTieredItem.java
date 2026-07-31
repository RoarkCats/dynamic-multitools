package com.roarkcats.dynamicmultitools.item.custom;

import com.roarkcats.dynamicmultitools.component.ModDataComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public class DynamicTieredItem extends Item {

    public DynamicTieredItem(int durability, int enchantability, Ingredient repairMaterial, Item.Properties properties) {
        super(properties
                .durability(durability)
                .component(ModDataComponent.ENCHANTABILITY.get(), enchantability)
                .component(ModDataComponent.REPAIR_MATERIAL.get(), repairMaterial)
        );
    }
    public DynamicTieredItem(Tier tier, Item.Properties properties) {
        this(tier.getUses(), tier.getEnchantmentValue(), tier.getRepairIngredient(), properties);
    }
    public DynamicTieredItem(int durability, Item.Properties properties) {
        super(properties.durability(durability));
    }


    public int getEnchantmentValue() {
        return this.components().getOrDefault(ModDataComponent.ENCHANTABILITY.get(), 15);
    }

    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        Ingredient repair_item = toRepair.getComponents().getOrDefault(ModDataComponent.REPAIR_MATERIAL.get(), Ingredient.EMPTY);
        return repair_item.test(repair) || super.isValidRepairItem(toRepair, repair);
    }
}
