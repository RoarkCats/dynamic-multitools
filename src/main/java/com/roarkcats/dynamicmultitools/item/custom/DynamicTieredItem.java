package com.roarkcats.dynamicmultitools.item.custom;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.LOGGER;
import static com.roarkcats.dynamicmultitools.DynamicMultitools.MODID;
import static com.roarkcats.dynamicmultitools.component.ModDataComponent.*;
import static net.minecraft.core.component.DataComponents.*;

import com.google.errorprone.annotations.ForOverride;
import com.roarkcats.dynamicmultitools.Config;
import com.roarkcats.dynamicmultitools.datapack.DynamicTier;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class DynamicTieredItem extends Item {

    public DynamicTieredItem(int durability, int enchantability, Ingredient repairMaterial, @Nullable List<Integer> colors, Item.Properties properties) {
        super(properties
                .durability(durability)
                .component(ENCHANTABILITY.get(), enchantability)
                .component(REPAIR_MATERIAL.get(), repairMaterial)
                .component(TEXTURE_TINTS.get(), colors)
        );
    }
    public DynamicTieredItem(DynamicTier tier, Item.Properties properties) {
        this(tier.getDurability(), tier.getEnchantability(), tier.getRepairIngredient(), List.of(tier.rodColor(), tier.color()), properties);
    }


    // Tiered Instance Maker
    @ForOverride
    public ItemStack createTieredStack(DynamicTier tier) {return createTieredStack(tier, "UNDEFINED");}
    public ItemStack createTieredStack(DynamicTier tier, String itemType) {
        ItemStack itemStack = this.getDefaultInstance();
        try {
            itemStack.set(ITEM_NAME, Component.translatable("dynamic_tier." + tier.modId() + "." + tier.material()).append(" ").append(Component.translatable("item." + MODID + "." + itemType)));
            itemStack.set(TEXTURE_TINTS, List.of(tier.rodColor(), tier.color()));
            itemStack.set(MAX_DAMAGE, (int) (tier.getDurability() * Config.getConfigFloat(Config.MULTITOOL_DURABILITY_MULTIPLIER)) );
            itemStack.set(ENCHANTABILITY, tier.getEnchantability());
            itemStack.set(REPAIR_MATERIAL, tier.getRepairIngredient());
            itemStack.applyComponents(tier.defaultComponents());
        } catch (Exception e) {
            LOGGER.error("Error creating DynamicTieredItem for dynamic tier {}.{}: {}", tier.modId(), tier.material(), e);
        }
        return itemStack;
    }

    @Override
    public int getEnchantmentValue(@NotNull ItemStack itemStack) {
        return itemStack.getOrDefault(ENCHANTABILITY.get(), 15);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        Ingredient repair_item = toRepair.getComponents().getOrDefault(REPAIR_MATERIAL.get(), Ingredient.EMPTY);
        return repair_item.test(repair) || super.isValidRepairItem(toRepair, repair);
    }
}
