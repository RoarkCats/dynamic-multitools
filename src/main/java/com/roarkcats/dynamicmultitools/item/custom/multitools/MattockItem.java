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

public class MattockItem extends DynamicDiggerItem {

    public MattockItem(DynamicTier tier, Properties properties) {
        super(tier, mattockTool(tier), properties.attributes(mattockAttributes(tier)));
    }

    // Helpers
    public static Tool mattockTool(DynamicTier tier) {
        return DynamicDiggerItem.multitoolTool(tier, Tags.Blocks.MINEABLE_WITH_MATTOCK);
    }
    public static ItemAttributeModifiers mattockAttributes(DynamicTier tier) {
        return DynamicDiggerItem.attributes(tier, 1.5F, -2.4F);
    }

    // Tiered Instance Maker
    public ItemStack createTieredStack(DynamicTier tier) {
        ItemStack itemStack = super.createTieredStack(tier, "mattock");
        try {
            ItemStackUpdates.updateAttributes(itemStack, mattockAttributes(tier));
            itemStack.set(TOOL, mattockTool(tier));
        } catch (Exception e) {
            LOGGER.error("Error creating MattockItem for dynamic tier {}.{}: {}", tier.modId(), tier.material(), e);
        }
        return itemStack;
    }

    // Actions
    public static final Set<ItemAbility> DEFAULT_MATTOCK_ACTIONS = new LinkedHashSet<>();
    static {
        DEFAULT_MATTOCK_ACTIONS.addAll(ItemAbilities.DEFAULT_HOE_ACTIONS);
        DEFAULT_MATTOCK_ACTIONS.addAll(ItemAbilities.DEFAULT_SHOVEL_ACTIONS);
    }

    @Override
    public Set<ItemAbility> getItemAbilities() {
        return DEFAULT_MATTOCK_ACTIONS;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return DEFAULT_MATTOCK_ACTIONS.contains(itemAbility);
    }

}
