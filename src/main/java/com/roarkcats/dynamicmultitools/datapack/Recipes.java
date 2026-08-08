package com.roarkcats.dynamicmultitools.datapack;

import com.roarkcats.dynamicmultitools.item.ModItems;
import com.roarkcats.dynamicmultitools.item.custom.DynamicDiggerItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.*;
import net.neoforged.neoforge.registries.DeferredItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.MODID;

// Organization helper class
public class Recipes {
    public static void generateRecipesFor(List<RecipeHolder<?>> recipes, DynamicTier tier) {
        craftingRecipe(recipes, tier);
        smithingRecipe(recipes, tier);
        // TODO: Recipe granting advancements
    }

    public static final Set<MultitoolRecipe> MULTITOOL_RECIPES = Set.of(
            new MultitoolRecipe(ModItems.DOLABRA, "axe", "pickaxe"),
            new MultitoolRecipe(ModItems.ADZE, "axe", "hoe")
    );

    public static void craftingRecipe(List<RecipeHolder<?>> recipes, DynamicTier tier) {
        MULTITOOL_RECIPES.stream().forEach(multitoolRecipe -> {
            var recipe = new ShapedRecipe(
                    "",
                    CraftingBookCategory.EQUIPMENT,
                    ShapedRecipePattern.of(
                        Map.of('L', multitoolRecipe.getTool1Ingredient(tier),
                            'R', multitoolRecipe.getTool2Ingredient(tier),
                            'm', tier.getMaterialIngredient(),
                            's', tier.rodIngredient()),
                        List.of("LmR"," s "," s ")
                    ),
                    multitoolRecipe.multitool().get().createTieredStack(tier)
            );
            recipes.add(new RecipeHolder<>(multitoolRecipe.getRecipeId(tier, null), recipe));
        });
    }

    public static void smithingRecipe(List<RecipeHolder<?>> recipes, DynamicTier tier) {
        MULTITOOL_RECIPES.stream().forEach(multitoolRecipe -> {
            var recipe = new SmithingTransformRecipe(
                    multitoolRecipe.getTool1Ingredient(tier),
                    multitoolRecipe.getTool2Ingredient(tier),
                    tier.getMaterialIngredient(),
                    multitoolRecipe.multitool().get().createTieredStack(tier)
            );
            var recipeFlipped = new SmithingTransformRecipe(
                    multitoolRecipe.getTool2Ingredient(tier),
                    multitoolRecipe.getTool1Ingredient(tier),
                    tier.getMaterialIngredient(),
                    multitoolRecipe.multitool().get().createTieredStack(tier)
            );
            recipes.add(new RecipeHolder<>(multitoolRecipe.getRecipeId(tier, "smithing"), recipe));
            recipes.add(new RecipeHolder<>(multitoolRecipe.getRecipeId(tier, "smithing_flipped"), recipeFlipped));
        });
    }

    // -- Helper Functions --
    protected static Ingredient getIngredient(String namespace, String path) {
        return Ingredient.of(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(namespace, path)));
    }
    public static record MultitoolRecipe(DeferredItem<? extends DynamicDiggerItem> multitool, String tool1, String tool2) {
        public Ingredient getTool1Ingredient(DynamicTier tier) {
            return getIngredient(tier.modId(), tier.material()+"_"+tool1());
        }
        public Ingredient getTool2Ingredient(DynamicTier tier) {
            return getIngredient(tier.modId(), tier.material()+"_"+tool2());
        }
        public ResourceLocation getRecipeId(DynamicTier tier, @Nullable String type) {
            String path = tier.modId()+"_"+tier.material()+"_"+multitool().getId().getPath();
            if (type != null) return ResourceLocation.fromNamespaceAndPath(MODID, path+"_"+type);
            else return ResourceLocation.fromNamespaceAndPath(MODID, path);
        }
    }
}
