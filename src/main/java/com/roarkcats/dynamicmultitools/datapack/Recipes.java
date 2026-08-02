package com.roarkcats.dynamicmultitools.datapack;

import com.roarkcats.dynamicmultitools.item.ModItems;
import com.roarkcats.dynamicmultitools.item.custom.multitools.DolabraItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.Map;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.MODID;

public class Recipes {
    // Organization helper class

    public static void generateRecipesFor(List<RecipeHolder<?>> recipes, DynamicTier tier) {
        craftingRecipe(recipes, tier);
        smithingRecipe(recipes, tier);
    }

    public static void craftingRecipe(List<RecipeHolder<?>> recipes, DynamicTier tier) {

        var recipeId = ResourceLocation.fromNamespaceAndPath(MODID, tier.modId()+"_"+tier.material()+"_dolabra");
        var recipe = new ShapedRecipe(
                "",
                CraftingBookCategory.EQUIPMENT,
                ShapedRecipePattern.of(
                    Map.of('L', getIngredient(tier.modId(), tier.material()+"_axe"),
                        'R', getIngredient(tier.modId(), tier.material()+"_pickaxe"),
                        'm', tier.getMaterialIngredient(),
                        's', tier.rodIngredient()),
                    List.of("LmR"," s "," s ")
                ),
                DolabraItem.createTieredStack(ModItems.DOLABRA.toStack(), tier)
        );
        recipes.add(new RecipeHolder<>(recipeId, recipe));
    }

    public static void smithingRecipe(List<RecipeHolder<?>> recipes, DynamicTier tier) {

        var recipeId = ResourceLocation.fromNamespaceAndPath(MODID, tier.modId()+"_"+tier.material()+"_dolabra_smithing");
        var recipe = new SmithingTransformRecipe(
                getIngredient(tier.modId(), tier.material()+"_axe"),
                getIngredient(tier.modId(), tier.material()+"_pickaxe"),
                tier.getMaterialIngredient(),
                DolabraItem.createTieredStack(ModItems.DOLABRA.toStack(), tier)
        );
        var recipeIdFlipped = ResourceLocation.fromNamespaceAndPath(MODID, tier.modId()+"_"+tier.material()+"_dolabra_smithing_flipped");
        var recipeFlipped = new SmithingTransformRecipe(
                getIngredient(tier.modId(), tier.material()+"_pickaxe"),
                getIngredient(tier.modId(), tier.material()+"_axe"),
                tier.getMaterialIngredient(),
                DolabraItem.createTieredStack(ModItems.DOLABRA.toStack(), tier)
        );
        recipes.add(new RecipeHolder<>(recipeId, recipe));
        recipes.add(new RecipeHolder<>(recipeIdFlipped, recipeFlipped));
    }

    // -- Helper Functions --
    protected static Ingredient getIngredient(String namespace, String path) {
        return Ingredient.of(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(namespace, path)));
    }
}
