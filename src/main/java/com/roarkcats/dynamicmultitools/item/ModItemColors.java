package com.roarkcats.dynamicmultitools.item;

import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.List;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.MODID;
import static com.roarkcats.dynamicmultitools.component.ModDataComponent.TEXTURE_TINTS;

@EventBusSubscriber(modid = MODID, value = {Dist.CLIENT})
public class ModItemColors {

    @SubscribeEvent
    public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        // Parameters are the item stack and the tint index.
        event.register(ModItemColors::getTintColorIfSet,
                // items to tint
                ModItems.DOLABRA
        );
    }

    // Helper
    protected static int getTintColorIfSet(ItemStack stack, int tintIndex) {
        List<Integer> colors = stack.get(TEXTURE_TINTS);
        if (colors != null && colors.size() > tintIndex) {
            return FastColor.ARGB32.opaque(colors.get(tintIndex));
        } else return -1;
    }
}
