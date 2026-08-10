package com.roarkcats.dynamicmultitools.item.custom.multitools;

import com.roarkcats.dynamicmultitools.datapack.DynamicTier;
import com.roarkcats.dynamicmultitools.item.custom.DynamicDiggerItem;
import com.roarkcats.dynamicmultitools.util.Tags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.LinkedHashSet;
import java.util.Set;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.LOGGER;
import static net.minecraft.core.component.DataComponents.ATTRIBUTE_MODIFIERS;
import static net.minecraft.core.component.DataComponents.TOOL;

public class SarchielloItem extends DynamicDiggerItem {

    public SarchielloItem(DynamicTier tier, Properties properties) {
        super(tier, sarchielloTool(tier), properties.attributes(sarchielloAttributes(tier)));
    }

    // Helpers
    public static Tool sarchielloTool(DynamicTier tier) {
        return DynamicDiggerItem.multitoolTool(tier, Tags.Blocks.MINEABLE_WITH_SARCHIELLO);
    }
    public static ItemAttributeModifiers sarchielloAttributes(DynamicTier tier) {
        return DynamicDiggerItem.attributes(tier, 1F, -2.4F);
    }

    // Tiered Instance Maker
    public ItemStack createTieredStack(DynamicTier tier) {
        ItemStack itemStack = super.createTieredStack(tier, "sarchiello");
        try {
            itemStack.set(ATTRIBUTE_MODIFIERS, sarchielloAttributes(tier));
            itemStack.set(TOOL, sarchielloTool(tier));
        } catch (Exception e) {
            LOGGER.error("Error creating SarchielloItem for dynamic tier {}.{}: {}", tier.modId(), tier.material(), e);
        }
        return itemStack;
    }

    // Actions
    public static final Set<ItemAbility> DEFAULT_SARCHIELLO_ACTIONS = new LinkedHashSet<>();
    static {
        DEFAULT_SARCHIELLO_ACTIONS.addAll(ItemAbilities.DEFAULT_HOE_ACTIONS);
        DEFAULT_SARCHIELLO_ACTIONS.addAll(ItemAbilities.DEFAULT_PICKAXE_ACTIONS);
    }

    @Override
    public Set<ItemAbility> getItemAbilities() {
        return DEFAULT_SARCHIELLO_ACTIONS;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return DEFAULT_SARCHIELLO_ACTIONS.contains(itemAbility);
    }

}
