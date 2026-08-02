package com.roarkcats.dynamicmultitools.datapack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.roarkcats.dynamicmultitools.util.ToolTierCollector;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nullable;
import java.util.NoSuchElementException;
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
        Optional<Ingredient> repairIngredient,
        // Extras
        Optional<Ingredient> materialIngredient,
        Ingredient rodIngredient
//        String texture,
//        Ingredient pickaxeItem,
//        Ingredient axeItem,
//        Ingredient shovelItem,
//        Ingredient hoeItem
) {
    private static final Ingredient DEFAULT_ROD = Ingredient.of(Tags.Items.RODS_WOODEN);

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
            Ingredient.CODEC.optionalFieldOf("repair_ingredient").forGetter(DynamicTier::repairIngredient),
            Ingredient.CODEC.optionalFieldOf("material_ingredient").forGetter(DynamicTier::materialIngredient),
            Ingredient.CODEC.optionalFieldOf("rod_ingredient", DEFAULT_ROD).forGetter(DynamicTier::rodIngredient)
    ).apply(instance, DynamicTier::new));


    // Helper Constructor
    private static final Optional X = Optional.empty();

    public DynamicTier(String modId, String material, int color, Tier tierBase) {
        this(modId, material, color, Optional.of(tierBase), X,X,X,X,X,X,X, DEFAULT_ROD);
    }

    // -- Getters --
    // helper methods combining the base and explicit value

    public int getDurability() throws NoSuchElementException {
        return durability.orElseGet(() -> tierBase.orElseThrow().getUses());
    }

    public float getSpeed() throws NoSuchElementException {
        return speed.orElseGet(() -> tierBase.orElseThrow().getSpeed());
    }

    public float getDamageBonus() throws NoSuchElementException {
        return damageBonus.orElseGet(() -> tierBase.orElseThrow().getAttackDamageBonus());
    }

    public @Nullable TagKey<Block> getIncorrectBlocksForDrops() {
        return incorrectBlocksForDrops.orElse(tierBase.isPresent() ? tierBase.get().getIncorrectBlocksForDrops() : null);
    }

    public int getEnchantability() throws NoSuchElementException {
        return enchantability.orElseGet(() -> tierBase.orElseThrow().getEnchantmentValue());
    }

    public @Nullable Ingredient getRepairIngredient() {
        return repairIngredient.orElse(tierBase.isPresent() ? tierBase.get().getRepairIngredient() : null);
    }

    public Ingredient getMaterialIngredient() throws NoSuchElementException {
        return materialIngredient.orElseGet(this::getRepairIngredient);
    }
}
