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
        for (int i=1; i<count+1; i++) {
            model.override()
                    .predicate(ResourceLocation.fromNamespaceAndPath("minecraft","custom_model_data"), i)
                    .model(new ModelFile.UncheckedModelFile(modLoc("item/tools"+i+"/"+name))).end();
        }
        return model;
    }

    @Override
    protected void registerModels() {

        ModItems.MULTITOOLS.stream().forEach(multitool -> {
            String name = multitool.getId().getPath();
            overrideHelper(name, 9,
            layerlessItem(multitool.get())
                    .texture("layer0", modLoc("item/tools/"+name+"_rod"))
                    .texture("layer1", modLoc("item/tools/"+name+"_head")));

            // Netherite custom textures
            layerlessItem(modLoc("item/tools1/"+name))
                    .texture("layer0", modLoc("item/tools_netherite/"+name+"_rod"))
                    .texture("layer1", modLoc("item/tools_netherite/"+name+"_head"));

            // Golden custom textures - custom model data 2 reserved

            // Better Nether custom textures (3,4,5,6)
            layerlessItem(modLoc("item/tools3/"+name))
                    .texture("layer0", modLoc("item/tools_cincinnasite/"+name));

            // Flint Required custom textures
            layerlessItem(modLoc("item/tools7/"+name))
                    .texture("layer0", modLoc("item/tools/"+name+"_rod"))
                    .texture("layer1", modLoc("item/tools/"+name+"_head"))
                    .texture("layer2", modLoc("item/tools_flint/"+name+"_binding"));

        });
    }
}
