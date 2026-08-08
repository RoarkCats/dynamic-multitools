package com.roarkcats.dynamicmultitools.datagen;

import com.roarkcats.dynamicmultitools.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override @Deprecated // ABANDONED FOR DYNAMIC GENERATION
    protected void buildRecipes(RecipeOutput recipeOutput) {

//        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DOLABRA.get())
//                .pattern("LmR").pattern(" s ").pattern(" s ")
//                .define('L', Items.IRON_AXE)
//                .define('R', Items.IRON_PICKAXE)
//                .define('m', Tags.Items.INGOTS_IRON)
//                .define('s', Tags.Items.RODS_WOODEN)
//                .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
//                .save(recipeOutput);
//
//        SmithingTransformRecipeBuilder.smithing(
//                Ingredient.of(Items.IRON_AXE), // template
//                Ingredient.of(Items.IRON_PICKAXE), // gear
//                Ingredient.of(Tags.Items.INGOTS_IRON), // material
//                RecipeCategory.TOOLS, ModItems.DOLABRA.get())
//                .unlocks("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
//                .save(recipeOutput, ModItems.DOLABRA.getRegisteredName() + "_smithing_pick");
//
//        SmithingTransformRecipeBuilder.smithing(
//                        Ingredient.of(Items.IRON_PICKAXE), // template
//                        Ingredient.of(Items.IRON_AXE), // gear
//                        Ingredient.of(Tags.Items.INGOTS_IRON), // material
//                        RecipeCategory.TOOLS, ModItems.DOLABRA.get())
//                .unlocks("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
//                .save(recipeOutput, ModItems.DOLABRA.getRegisteredName() + "_smithing_axe");
    }
}
