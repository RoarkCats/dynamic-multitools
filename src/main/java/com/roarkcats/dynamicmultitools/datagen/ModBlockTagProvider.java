package com.roarkcats.dynamicmultitools.datagen;

import com.roarkcats.dynamicmultitools.util.Tags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.MODID;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        tag(Tags.Blocks.MINEABLE_WITH_DOLABRA)
                .addTag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTag(BlockTags.MINEABLE_WITH_AXE);

        tag(Tags.Blocks.MINEABLE_WITH_ADZE)
                .addTag(BlockTags.MINEABLE_WITH_HOE)
                .addTag(BlockTags.MINEABLE_WITH_AXE);

        tag(Tags.Blocks.MINEABLE_WITH_PULASKI)
                .addTag(BlockTags.MINEABLE_WITH_SHOVEL)
                .addTag(BlockTags.MINEABLE_WITH_AXE);
    }
}
