package com.roarkcats.dynamicmultitools.item.multitools;

import com.roarkcats.dynamicmultitools.util.Tags;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.HashSet;
import java.util.Set;

import static net.minecraft.core.component.DataComponents.DYED_COLOR;

public class DolabraItem extends DiggerItem {

    public static final Set<ItemAbility> DEFAULT_DOLABRA_ACTIONS = new HashSet<>();
    static { // this all doesn't seem to do much...
        DEFAULT_DOLABRA_ACTIONS.addAll(ItemAbilities.DEFAULT_PICKAXE_ACTIONS);
        DEFAULT_DOLABRA_ACTIONS.addAll(ItemAbilities.DEFAULT_AXE_ACTIONS);
    }

    public DolabraItem(Tier tier, Properties properties) {
        this(tier, properties, new DyedItemColor(-1, false));
    }
    public DolabraItem(Tier tier, Properties properties, DyedItemColor color) {
        super(tier, Tags.Blocks.MINEABLE_WITH_DOLABRA, properties.component(DYED_COLOR, color));
    }

    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return DEFAULT_DOLABRA_ACTIONS.contains(itemAbility);
    }
}
