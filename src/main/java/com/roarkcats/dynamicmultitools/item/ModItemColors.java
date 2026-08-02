package com.roarkcats.dynamicmultitools.item;

import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.MODID;

@EventBusSubscriber(modid = MODID, value = {Dist.CLIENT})
public class ModItemColors {

    private static final int DEFAULT = -1;

    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        // Parameters are the item stack and the tint index.
        event.register((stack, tintIndex) -> tintIndex == 1 ? DyedItemColor.getOrDefault(stack, DEFAULT) : -1,
                // items to tint
                ModItems.DOLABRA
        );
    }
}
