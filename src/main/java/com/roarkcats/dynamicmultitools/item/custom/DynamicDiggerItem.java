package com.roarkcats.dynamicmultitools.item.custom;

import com.roarkcats.dynamicmultitools.datapack.DynamicTier;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.LOGGER;

public class DynamicDiggerItem extends DynamicTieredItem {

    public DynamicDiggerItem(DynamicTier tier, Tool tool, Item.Properties properties) {
        super(tier, properties.component(DataComponents.TOOL, tool));
    }
    public DynamicDiggerItem(DynamicTier tier, TagKey<Block> block, Item.Properties properties) {
        super(tier, properties.component(DataComponents.TOOL, tool(tier, block)));
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

    // -- Item Abilities --

    // Override this with ability set
    public Set<ItemAbility> getItemAbilities() {
        return Set.of();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        for (ItemAbility ability : getItemAbilities()) {
            InteractionResult result = tryPerformToolAction(context, ability);
            if (result != InteractionResult.PASS) return result;
        }
        return super.useOn(context);
    }

    // Item Ability helpers
    protected InteractionResult tryPerformToolAction(UseOnContext context, ItemAbility ability) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var player = context.getPlayer();
        var state = level.getBlockState(pos);
        var item = context.getItemInHand();

        // Query NeoForge's built-in tool action handlers
        var modifiedState = state.getToolModifiedState(context, ability, false);
        if (modifiedState != null) {
            // Play sound & handle block replacement
            playSoundFor(context, ability);
            particlesFor(context, ability);

            level.setBlock(pos, modifiedState, Block.UPDATE_ALL_IMMEDIATE);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, modifiedState)); // sculk sensor

            if (player instanceof ServerPlayer) CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, pos, item);
            if (player != null) item.hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));

            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    protected void playSoundFor(UseOnContext context, ItemAbility ability) {
        SoundEvent sound = null;
        if (ability.equals(ItemAbilities.AXE_STRIP)) sound = SoundEvents.AXE_STRIP;
        else if (ability.equals(ItemAbilities.AXE_SCRAPE)) sound = SoundEvents.AXE_SCRAPE;
        else if (ability.equals(ItemAbilities.AXE_WAX_OFF)) sound = SoundEvents.AXE_WAX_OFF;
        else if (ability.equals(ItemAbilities.HOE_TILL)) sound = SoundEvents.HOE_TILL;
        else if (ability.equals(ItemAbilities.SHOVEL_FLATTEN)) sound = SoundEvents.SHOVEL_FLATTEN;
        else if (ability.equals(ItemAbilities.SHOVEL_DOUSE)) sound = SoundEvents.GENERIC_EXTINGUISH_FIRE;

        if (sound != null) context.getLevel().playSound(context.getPlayer(), context.getClickedPos(), sound, SoundSource.BLOCKS, 1.0F, 1.0F);
        else LOGGER.debug("Unknown sound missing definition for ItemAbility: {}", ability);
    }

    protected void particlesFor(UseOnContext context, ItemAbility ability) {
        int levelEvent = 0;
        if (ability.equals(ItemAbilities.AXE_SCRAPE)) levelEvent = LevelEvent.PARTICLES_SCRAPE;
        else if (ability.equals(ItemAbilities.AXE_WAX_OFF)) levelEvent = LevelEvent.PARTICLES_WAX_OFF;
//        else if (ability.equals(ItemAbilities.HOE_TILL)) levelEvent = LevelEvent.PARTICLES_DESTROY_BLOCK;
//        else if (ability.equals(ItemAbilities.SHOVEL_DOUSE)) levelEvent = LevelEvent.PARTICLES_SHOOT_SMOKE;

        if (levelEvent != 0) context.getLevel().levelEvent(context.getPlayer(), levelEvent, context.getClickedPos(), 0);
    }
}
