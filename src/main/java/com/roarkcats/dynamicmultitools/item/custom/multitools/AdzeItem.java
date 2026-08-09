package com.roarkcats.dynamicmultitools.item.custom.multitools;

import com.roarkcats.dynamicmultitools.Config;
import com.roarkcats.dynamicmultitools.datapack.DynamicTier;
import com.roarkcats.dynamicmultitools.item.custom.DynamicDiggerItem;
import com.roarkcats.dynamicmultitools.item.custom.DynamicTieredItem;
import com.roarkcats.dynamicmultitools.util.Tags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.HashSet;
import java.util.Set;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.LOGGER;
import static net.minecraft.core.component.DataComponents.ATTRIBUTE_MODIFIERS;
import static net.minecraft.core.component.DataComponents.TOOL;

public class AdzeItem extends DynamicDiggerItem {

    public AdzeItem(DynamicTier tier, Properties properties) {
        super(tier, adzeTool(tier), properties.attributes(adzeAttributes(tier)));
    }

    // Helpers
    public static Tool adzeTool(DynamicTier tier) {
        return DynamicDiggerItem.multitoolTool(tier, Tags.Blocks.MINEABLE_WITH_ADZE);
    }
    public static ItemAttributeModifiers adzeAttributes(DynamicTier tier) {
        return DynamicDiggerItem.attributes(tier, 0.5F, -2F);
    }

    // Tiered Instance Maker
    public ItemStack createTieredStack(DynamicTier tier) {
        ItemStack itemStack = super.createTieredStack(tier, "adze");
        try {
            itemStack.set(ATTRIBUTE_MODIFIERS, adzeAttributes(tier));
            itemStack.set(TOOL, adzeTool(tier));
        } catch (Exception e) {
            LOGGER.error("Error creating AdzeItem for dynamic tier {}.{}: {}", tier.modId(), tier.material(), e);
        }
        return itemStack;
    }

    // Actions
    public static final Set<ItemAbility> DEFAULT_ADZE_ACTIONS = new HashSet<>();
    static {
        DEFAULT_ADZE_ACTIONS.addAll(ItemAbilities.DEFAULT_HOE_ACTIONS);
        DEFAULT_ADZE_ACTIONS.addAll(ItemAbilities.DEFAULT_AXE_ACTIONS);
    }

    @Override
    public Set<ItemAbility> getItemAbilities() {
        return DEFAULT_ADZE_ACTIONS;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return DEFAULT_ADZE_ACTIONS.contains(itemAbility);
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return Config.AXE_MULTITOOLS_DISABLE_SHIELD.getAsBoolean();
    }

}
