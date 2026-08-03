package com.roarkcats.dynamicmultitools.datapack;

import com.roarkcats.dynamicmultitools.Config;
import com.roarkcats.dynamicmultitools.util.ToolTierCollector;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.MODID;
import static com.roarkcats.dynamicmultitools.DynamicMultitools.LOGGER;

@EventBusSubscriber(modid = MODID)
public class Server {

    public static final ResourceKey<Registry<DynamicTier>> DYNAMIC_TIER_REGISTRY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MODID, "dynamic_tier"));

    @SubscribeEvent
    public static void addRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
                DYNAMIC_TIER_REGISTRY,
                DynamicTier.CODEC,
                DynamicTier.CODEC,
                builder -> builder.maxId(256)
        );
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Server starting");
        final boolean logTiers = Config.LOG_TIERS.getAsBoolean();
        if (logTiers) ToolTierCollector.getAllTiers().forEach((tier) -> LOGGER.info("TIER >> {}", tier));

        var recipeManager = event.getServer().getRecipeManager();
        List<RecipeHolder<?>> generatedRecipes = new ArrayList<>();

        event.getServer().registryAccess().registryOrThrow(DYNAMIC_TIER_REGISTRY).forEach((tier) -> {
            if (logTiers) LOGGER.info("DYNAMIC TIER >> {}", tier.material());
            Recipes.generateRecipesFor(generatedRecipes, tier);
        });

        recipeManager.replaceRecipes(Stream.concat( recipeManager.getRecipes().stream(), generatedRecipes.stream() ).toList());
    }
}
