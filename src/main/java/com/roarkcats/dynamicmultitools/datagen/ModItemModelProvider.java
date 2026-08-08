package com.roarkcats.dynamicmultitools.datagen;

import com.roarkcats.dynamicmultitools.item.ModItems;
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

    // Layered Model Helpers
    public ItemModelBuilder layerlessItem(Item item) {
        return this.layerlessItem((ResourceLocation) Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)));
    }
    public ItemModelBuilder layerlessItem(ResourceLocation item) {
        return ((ItemModelBuilder) this.getBuilder(item.toString())).parent(new ModelFile.UncheckedModelFile("item/handheld"));
    }

    // Override Helper
    // provides N unused custom model pointers for ease of use
    public ItemModelBuilder overrideHelper(String name, int count, ItemModelBuilder model) {
        for (int i=0; i<count; i++) {
            model.override()
                    .predicate(ResourceLocation.fromNamespaceAndPath("minecraft","custom_model_data"), i)
                    .model(new ModelFile.UncheckedModelFile(modLoc("item/"+i+"/"+name))).end();
        }
        return model;
    }

    @Override
    protected void registerModels() {
        overrideHelper("dolabra", 10,
        layerlessItem(ModItems.DOLABRA.get())
                .texture("layer0", modLoc("item/tools/dolabra_rod"))
                .texture("layer1", modLoc("item/tools/dolabra_head")));

        overrideHelper("adze", 10,
        layerlessItem(ModItems.ADZE.get())
                .texture("layer0", modLoc("item/tools/adze_rod"))
                .texture("layer1", modLoc("item/tools/adze_head")));
    }
}
