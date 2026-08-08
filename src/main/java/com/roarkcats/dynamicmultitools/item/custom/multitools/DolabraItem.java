package com.roarkcats.dynamicmultitools.item.custom.multitools;

import com.roarkcats.dynamicmultitools.Config;
import com.roarkcats.dynamicmultitools.datapack.DynamicTier;
import com.roarkcats.dynamicmultitools.item.custom.DynamicDiggerItem;
import com.roarkcats.dynamicmultitools.item.custom.DynamicTieredItem;
import com.roarkcats.dynamicmultitools.util.Tags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.LOGGER;
import static net.minecraft.core.component.DataComponents.ATTRIBUTE_MODIFIERS;
import static net.minecraft.core.component.DataComponents.TOOL;

public class DolabraItem extends DynamicDiggerItem {

    public DolabraItem(DynamicTier tier, Properties properties) {
        super(tier, dolabraTool(tier), properties.attributes(dolabraAttributes(tier)));
    }

    // Helpers
    public static Tool dolabraTool(DynamicTier tier) {
        return DynamicDiggerItem.multitoolTool(tier, Tags.Blocks.MINEABLE_WITH_DOLABRA);
    }
    public static ItemAttributeModifiers dolabraAttributes(DynamicTier tier) {
        return DynamicDiggerItem.attributes(tier, 3F, -2.8F);
    }

    // Tiered Instance Maker
    public static ItemStack createTieredStack(ItemStack itemStack, DynamicTier tier) {
        DynamicTieredItem.createTieredStack(itemStack, tier, "dolabra");
        try {
            itemStack.set(ATTRIBUTE_MODIFIERS, dolabraAttributes(tier));
            itemStack.set(TOOL, dolabraTool(tier));
        } catch (Exception e) {
            LOGGER.error("Error creating DolabraItem for dynamic tier {}.{}: {}", tier.modId(), tier.material(), e);
        }
        return itemStack;
    }

    // Actions
    public static final Set<ItemAbility> DEFAULT_DOLABRA_ACTIONS = new HashSet<>();
    static {
        DEFAULT_DOLABRA_ACTIONS.addAll(ItemAbilities.DEFAULT_PICKAXE_ACTIONS);
        DEFAULT_DOLABRA_ACTIONS.addAll(ItemAbilities.DEFAULT_AXE_ACTIONS);
    }

    @Override
    public Set<ItemAbility> getItemAbilities() {
        return DEFAULT_DOLABRA_ACTIONS;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return DEFAULT_DOLABRA_ACTIONS.contains(itemAbility);
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return Config.AXE_MULTITOOLS_DISABLE_SHIELD.getAsBoolean();
    }

}
