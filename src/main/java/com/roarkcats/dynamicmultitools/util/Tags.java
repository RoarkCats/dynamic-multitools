package com.roarkcats.dynamicmultitools.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.MODID;

public class Tags {

    public static class Blocks {
        public static final TagKey<Block> MINEABLE_WITH_DOLABRA = createTag("mineable/dolabra");
        public static final TagKey<Block> MINEABLE_WITH_ADZE = createTag("mineable/adze");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MODID, name));
        }
    }
}
