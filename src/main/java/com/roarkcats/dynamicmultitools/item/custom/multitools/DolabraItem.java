package com.roarkcats.dynamicmultitools.item.custom.multitools;

import com.roarkcats.dynamicmultitools.datapack.DynamicTier;
import com.roarkcats.dynamicmultitools.item.custom.DynamicDiggerItem;
import com.roarkcats.dynamicmultitools.util.Tags;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;

import java.util.List;

public class DolabraItem extends DynamicDiggerItem {

//    public static final Set<ItemAbility> DEFAULT_DOLABRA_ACTIONS = new HashSet<>();
//    static { // this all doesn't seem to do much...
//        DEFAULT_DOLABRA_ACTIONS.addAll(ItemAbilities.DEFAULT_PICKAXE_ACTIONS);
//        DEFAULT_DOLABRA_ACTIONS.addAll(ItemAbilities.DEFAULT_AXE_ACTIONS);
//    }

    public DolabraItem(DynamicTier tier, Properties properties) {
        this(tier, null, properties);
    }
    public DolabraItem(DynamicTier tier, DyedItemColor color, Properties properties) {
        super(tier, dolabraTool(tier), color, properties.attributes(dolabraAttributes(tier)));
    }

    // Helpers
    public static Tool dolabraTool(DynamicTier tier) {
        return DynamicDiggerItem.tool(List.of(
                DynamicDiggerItem.toolRule(tier.getIncorrectBlocksForDrops()),
                DynamicDiggerItem.toolRule(Tags.Blocks.MINEABLE_WITH_DOLABRA, tier.getSpeed()*0.8F)
        ));
    }
    public static ItemAttributeModifiers dolabraAttributes(DynamicTier tier) {
        return DynamicDiggerItem.attributes(tier, 3F, -2.8F);
    }

//    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
//        return DEFAULT_DOLABRA_ACTIONS.contains(itemAbility);
//    }
}
