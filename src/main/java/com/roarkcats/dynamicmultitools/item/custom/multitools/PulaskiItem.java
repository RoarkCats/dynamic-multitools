package com.roarkcats.dynamicmultitools.item.custom.multitools;

import com.roarkcats.dynamicmultitools.Config;
import com.roarkcats.dynamicmultitools.datapack.DynamicTier;
import com.roarkcats.dynamicmultitools.item.custom.DynamicDiggerItem;
import com.roarkcats.dynamicmultitools.util.Tags;
import net.minecraft.world.entity.LivingEntity;
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

public class PulaskiItem extends DynamicDiggerItem {

    public PulaskiItem(DynamicTier tier, Properties properties) {
        super(tier, pulaskiTool(tier), properties.attributes(pulaskiAttributes(tier)));
    }

    // Helpers
    public static Tool pulaskiTool(DynamicTier tier) {
        return DynamicDiggerItem.multitoolTool(tier, Tags.Blocks.MINEABLE_WITH_PULASKI);
    }
    public static ItemAttributeModifiers pulaskiAttributes(DynamicTier tier) {
        return DynamicDiggerItem.attributes(tier, 2F, -2.8F);
    }

    // Tiered Instance Maker
    public ItemStack createTieredStack(DynamicTier tier) {
        ItemStack itemStack = super.createTieredStack(tier, "pulaski");
        try {
            itemStack.set(ATTRIBUTE_MODIFIERS, pulaskiAttributes(tier));
            itemStack.set(TOOL, pulaskiTool(tier));
        } catch (Exception e) {
            LOGGER.error("Error creating PulaskiItem for dynamic tier {}.{}: {}", tier.modId(), tier.material(), e);
        }
        return itemStack;
    }

    // Actions
    public static final Set<ItemAbility> DEFAULT_PULASKI_ACTIONS = new LinkedHashSet<>();
    static {
        DEFAULT_PULASKI_ACTIONS.addAll(ItemAbilities.DEFAULT_SHOVEL_ACTIONS);
        DEFAULT_PULASKI_ACTIONS.addAll(ItemAbilities.DEFAULT_AXE_ACTIONS);
    }

    @Override
    public Set<ItemAbility> getItemAbilities() {
        return DEFAULT_PULASKI_ACTIONS;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
        return DEFAULT_PULASKI_ACTIONS.contains(itemAbility);
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return Config.AXE_MULTITOOLS_DISABLE_SHIELD.getAsBoolean();
    }

}
