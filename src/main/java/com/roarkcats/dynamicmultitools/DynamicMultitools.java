package com.roarkcats.dynamicmultitools;

import com.roarkcats.dynamicmultitools.component.ModDataComponent;
import com.roarkcats.dynamicmultitools.datapack.DynamicTier;
import com.roarkcats.dynamicmultitools.item.CreativeTab;
import com.roarkcats.dynamicmultitools.item.ModItems;
import com.roarkcats.dynamicmultitools.util.ToolTierCollector;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(DynamicMultitools.MODID)
public class DynamicMultitools {

    public static final String MODID = "dynamic_multitools";
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public DynamicMultitools(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register items
        ModDataComponent.register(modEventBus);
        ModItems.register(modEventBus);
        CreativeTab.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // (necessary if and only if we want *this* class to respond directly to events.)
        NeoForge.EVENT_BUS.register(this);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    @EventBusSubscriber(modid = MODID)
    public static class ClientModEvents {

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
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Server starting");

        ToolTierCollector.getAllTiers().forEach((tier) -> LOGGER.info("TIER >> {}", tier));
        event.getServer().registryAccess().registryOrThrow(ClientModEvents.DYNAMIC_TIER_REGISTRY).forEach((tier) -> {
            LOGGER.info("DYNAMIC TIER >> {}", tier.material());
        });
    }
}
