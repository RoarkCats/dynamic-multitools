package com.roarkcats.dynamicmultitools.datapack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.roarkcats.dynamicmultitools.util.ToolTierCollector;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nullable;
import java.util.Optional;

public record DynamicTier(

        String modId,
        String material,
        int color,
        Optional<Tier> tierBase, // located from string on file
        // Overrides
        Optional<Integer> durability,
        Optional<Float> speed,
        Optional<Float> damageBonus,
        Optional<TagKey<Block>> incorrectBlocksForDrops,
        Optional<Integer> enchantability,
        Optional<Ingredient> repairIngredient
//        String texture,
//        Ingredient pickaxeItem,
//        Ingredient axeItem,
//        Ingredient shovelItem,
//        Ingredient hoeItem
) {
    public static final Codec<DynamicTier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("mod_id", "minecraft").forGetter(DynamicTier::modId),
            Codec.STRING.fieldOf("material").forGetter(DynamicTier::material),
            Codec.INT.optionalFieldOf("color", -1).forGetter(DynamicTier::color),
            ToolTierCollector.TIER_CODEC.optionalFieldOf("tier_base").forGetter(DynamicTier::tierBase),
            // Overrides
            Codec.INT.optionalFieldOf("durability").forGetter(DynamicTier::durability),
            Codec.FLOAT.optionalFieldOf("speed").forGetter(DynamicTier::speed),
            Codec.FLOAT.optionalFieldOf("damage_bonus").forGetter(DynamicTier::damageBonus),
            TagKey.codec(Registries.BLOCK).optionalFieldOf("incorrect_blocks_for_drops").forGetter(DynamicTier::incorrectBlocksForDrops),
            Codec.INT.optionalFieldOf("enchantability").forGetter(DynamicTier::enchantability),
            Ingredient.CODEC.optionalFieldOf("repair_ingredient").forGetter(DynamicTier::repairIngredient)
    ).apply(instance, DynamicTier::new));
}
