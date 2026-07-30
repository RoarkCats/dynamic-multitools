package com.roarkcats.dynamicmultitools.datagen;

import com.roarkcats.dynamicmultitools.item.Items;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.MODID;

public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }


    public ItemModelBuilder layerlessItem(Item item) {
        return this.layerlessItem((ResourceLocation) Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)));
    }
    public ItemModelBuilder layerlessItem(ResourceLocation item) {
        return ((ItemModelBuilder) this.getBuilder(item.toString())).parent(new ModelFile.UncheckedModelFile("item/handheld"));
    }

    @Override
    protected void registerModels() {
        layerlessItem(Items.DOLABRA.get())
                .texture("layer0", modLoc("item/tools/dolabra_rod"))
                .texture("layer1", modLoc("item/tools/dolabra_head"));
    }
}
