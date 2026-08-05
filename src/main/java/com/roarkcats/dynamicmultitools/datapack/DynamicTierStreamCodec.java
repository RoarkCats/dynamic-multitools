package com.roarkcats.dynamicmultitools.datapack;

import com.roarkcats.dynamicmultitools.util.ToolTierCollector;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;

public class DynamicTierStreamCodec {

    //  -- UNUSED --
    // Default JSON nbt codec good enough
    // rather not upkeep 2 additional stream codecs
    // when it should only be syncing data once or twice
    // kept methods encase I need them down the line
    // Tip: Don't forget to register stream codec in 'dynamicmultitools.datapack.server'

    public static final StreamCodec<RegistryFriendlyByteBuf, DynamicTier> STREAM_CODEC = StreamCodec.of(
            DynamicTierStreamCodec::encode,
            DynamicTierStreamCodec::decode
    );

    private static void encode(RegistryFriendlyByteBuf buf, DynamicTier value) {
        ByteBufCodecs.STRING_UTF8.encode(buf, value.modId());
        ByteBufCodecs.STRING_UTF8.encode(buf, value.material());
        ByteBufCodecs.INT.encode(buf, value.color());
        ByteBufCodecs.INT.encode(buf, value.rodColor());

        // Tier Base
        ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8)
                .encode(buf, value.tierBase().map(tier -> {
                    return tier.toString().toLowerCase();
                }));

        // Overrides
        ByteBufCodecs.optional(ByteBufCodecs.INT).encode(buf, value.durability());
        ByteBufCodecs.optional(ByteBufCodecs.FLOAT).encode(buf, value.speed());
        ByteBufCodecs.optional(ByteBufCodecs.FLOAT).encode(buf, value.damageBonus());

        // TagKey<Block>
        ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC.map(
                loc -> TagKey.create(Registries.BLOCK, loc),
                TagKey::location
        )).encode(buf, value.incorrectBlocksForDrops());

        ByteBufCodecs.optional(ByteBufCodecs.INT).encode(buf, value.enchantability());
        ByteBufCodecs.optional(Ingredient.CONTENTS_STREAM_CODEC).encode(buf, value.repairIngredient());

        // Extras
        ByteBufCodecs.optional(Ingredient.CONTENTS_STREAM_CODEC).encode(buf, value.materialIngredient());
        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, value.rodIngredient());
        DataComponentPatch.STREAM_CODEC.encode(buf, value.defaultComponents());
    }

    private static DynamicTier decode(RegistryFriendlyByteBuf buf) {
        return new DynamicTier(
                ByteBufCodecs.STRING_UTF8.decode(buf),
                ByteBufCodecs.STRING_UTF8.decode(buf),
                ByteBufCodecs.INT.decode(buf),
                ByteBufCodecs.INT.decode(buf),

                // Decode TierBase
                ByteBufCodecs.optional(ByteBufCodecs.STRING_UTF8)
                        .decode(buf)
                        .map(str -> ToolTierCollector.searchForTier(str.toLowerCase())),

                // Overrides
                ByteBufCodecs.optional(ByteBufCodecs.INT).decode(buf),
                ByteBufCodecs.optional(ByteBufCodecs.FLOAT).decode(buf),
                ByteBufCodecs.optional(ByteBufCodecs.FLOAT).decode(buf),

                // Decode TagKey<Block>
                ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC.map(
                        loc -> TagKey.create(Registries.BLOCK, loc),
                        TagKey::location
                )).decode(buf),

                ByteBufCodecs.optional(ByteBufCodecs.INT).decode(buf),
                ByteBufCodecs.optional(Ingredient.CONTENTS_STREAM_CODEC).decode(buf),

                // Extras
                ByteBufCodecs.optional(Ingredient.CONTENTS_STREAM_CODEC).decode(buf),
                Ingredient.CONTENTS_STREAM_CODEC.decode(buf),
                DataComponentPatch.STREAM_CODEC.decode(buf)
        );
    }
}
