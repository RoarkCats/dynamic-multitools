package com.roarkcats.dynamicmultitools;

import com.roarkcats.dynamicmultitools.component.ModDataComponent;
import com.roarkcats.dynamicmultitools.item.CreativeTab;
import com.roarkcats.dynamicmultitools.item.ModItems;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;

@Mod(DynamicMultitools.MODID)
public class DynamicMultitools {

    public static final String MODID = "dynamic_multitools";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DynamicMultitools(IEventBus modEventBus, ModContainer modContainer) {
//        modEventBus.addListener(this::commonSetup);

        // Register items
        ModDataComponent.register(modEventBus);
        ModItems.register(modEventBus);
        CreativeTab.register(modEventBus);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

//    private void commonSetup(FMLCommonSetupEvent event) {}
}
