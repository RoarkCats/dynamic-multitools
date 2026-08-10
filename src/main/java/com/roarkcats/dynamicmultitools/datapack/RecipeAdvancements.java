package com.roarkcats.dynamicmultitools.datapack;

import com.roarkcats.dynamicmultitools.item.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Arrays;
import java.util.List;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.MODID;

@Deprecated // ABANDONED, CANT INJECT ADVANCEMENTS
public class RecipeAdvancements {
    public static void generateRecipesFor(List<AdvancementHolder> advancements, DynamicTier tier, AdvancementHolder recipeRoot) {

        // Generate recipe resource location list
        var rewards = AdvancementRewards.Builder.experience(0);
        ModItems.MULTITOOLS.forEach(multitool -> {
            String path = recipeIdPath(tier, multitool);
            rewards.addRecipe(ResourceLocation.fromNamespaceAndPath(MODID, path));
            rewards.addRecipe(ResourceLocation.fromNamespaceAndPath(MODID, path+"_smithing"));
            rewards.addRecipe(ResourceLocation.fromNamespaceAndPath(MODID, path+"_smithing_flipped"));
        });

        // Convert material ingredient to item list
        ItemLike[] matchingItems = Arrays.stream(
                tier.getMaterialIngredient().getItems()
        ).map(ItemStack::getItem).toArray(ItemLike[]::new);

        // Build advancement
        var advancement = Advancement.Builder.recipeAdvancement()
                .parent(recipeRoot)
                .addCriterion("has_material", InventoryChangeTrigger.TriggerInstance.hasItems(matchingItems))
                .rewards(rewards.build())
                .requirements(AdvancementRequirements.Strategy.OR)
                .build(ResourceLocation.fromNamespaceAndPath(MODID, tier.modId()+"_"+tier.material()));

        advancements.add(advancement);
    }

    public static String recipeIdPath(DynamicTier tier, DeferredItem<?> item) {
        return tier.modId()+"_"+tier.material()+"_"+item.getId().getPath();
    }
}
