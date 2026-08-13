package com.roarkcats.dynamicmultitools.item.custom.multitools;

import com.roarkcats.dynamicmultitools.datapack.DynamicTier;
import com.roarkcats.dynamicmultitools.item.custom.DynamicDiggerItem;
import com.roarkcats.dynamicmultitools.util.ItemStackUpdates;
import com.roarkcats.dynamicmultitools.util.Tags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.LinkedHashSet;
import java.util.Set;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.LOGGER;
import static net.minecraft.core.component.DataComponents.TOOL;

public class ExcavatorItem extends DynamicDiggerItem {

    public ExcavatorItem(DynamicTier tier, Properties properties) {
        super(tier, excavatorTool(tier), properties.attributes(excavatorAttributes(tier)));
    }

    // Helpers
    public static Tool excavatorTool(DynamicTier tier) {
        return DynamicDiggerItem.multitoolTool(tier, Tags.Blocks.MINEABLE_WITH_EXCAVATOR);
    }
    public static ItemAttributeModifiers excavatorAttributes(DynamicTier tier) {
        return DynamicDiggerItem.attributes(tier, 1F, -2.8F);
    }

    // Tiered Instance Maker
    public ItemStack createTieredStack(DynamicTier tier) {
        ItemStack itemStack = super.createTieredStack(tier, "excavator");
        try {
            ItemStackUpdates.updateAttributes(itemStack, excavatorAttributes(tier));
            itemStack.set(TOOL, excavatorTool(tier));
        } catch (Exception e) {
            LOGGER.error("Error creating ExcavatorItem for dynamic tier {}.{}: {}", tier.modId(), tier.material(), e);
        }
        return itemStack;
    }

    // Actions
    public static final Set<ItemAbility> DEFAULT_EXCAVATOR_ACTIONS = new LinkedHashSet<>();
    static {
        DEFAULT_EXCAVATOR_ACTIONS.addAll(ItemAbilities.DEFAULT_PICKAXE_ACTIONS);
        DEFAULT_EXCAVATOR_ACTIONS.addAll(ItemAbilities.DEFAULT_SHOVEL_ACTIONS);
    }

    @Override
    public Set<ItemAbility> getItemAbilities() {
        return DEFAULT_EXCAVATOR_ACTIONS;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return DEFAULT_EXCAVATOR_ACTIONS.contains(itemAbility);
    }

}
