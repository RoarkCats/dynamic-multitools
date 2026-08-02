package com.roarkcats.dynamicmultitools.item.custom;

import com.roarkcats.dynamicmultitools.datapack.DynamicTier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DynamicDiggerItem extends DynamicTieredItem {

    public DynamicDiggerItem(DynamicTier tier, Tool tool, @Nullable DyedItemColor color, Item.Properties properties) {
        super(tier, color, properties.component(DataComponents.TOOL, tool));
    }
    public DynamicDiggerItem(DynamicTier tier, TagKey<Block> block, @Nullable DyedItemColor color, Item.Properties properties) {
        super(tier, color, properties.component(DataComponents.TOOL, tool(tier, block)));
    }


    // Helpers
    public static ItemAttributeModifiers attributes(float attackDamage, float attackSpeed) {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, (double) attackDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, (double) attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }
    public static ItemAttributeModifiers attributes(DynamicTier tier, float attackDamage, float attackSpeed) {
        return attributes(attackDamage + tier.getDamageBonus(), attackSpeed);
    }

    public static Tool tool(DynamicTier tier, TagKey<Block> blocks) {
        ArrayList<Tool.Rule> rules = new ArrayList<>( List.of(toolRule(blocks, tier.getSpeed())) );
        if (tier.getIncorrectBlocksForDrops() != null) rules.add(toolRule(tier.getIncorrectBlocksForDrops()));
        return tool(rules);
    }
    public static Tool tool(List<Tool.Rule> rules) {
        return new Tool(rules, 1.0F, 1);
    }
    public static Tool.Rule toolRule(TagKey<Block> incorrectBlocksForDrops) {
        return Tool.Rule.deniesDrops(incorrectBlocksForDrops);
    }
    public static Tool.Rule toolRule(TagKey<Block> blocks, float speed) {
        return Tool.Rule.minesAndDrops(blocks, speed);
    }
    // --

    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(2, attacker, EquipmentSlot.MAINHAND);
    }
}
