package com.roarkcats.dynamicmultitools;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = DynamicMultitools.MODID, dist = Dist.CLIENT)
//@EventBusSubscriber(modid = DynamicMultitools.MODID, value = Dist.CLIENT)
public class Client {
    public Client(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

//    @SubscribeEvent
//    static void onClientSetup(FMLClientSetupEvent event) {}
}
