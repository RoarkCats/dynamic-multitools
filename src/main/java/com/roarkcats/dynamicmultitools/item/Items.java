package com.roarkcats.dynamicmultitools.item;

import com.roarkcats.dynamicmultitools.DynamicMultitools;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Items {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DynamicMultitools.MODID);

    public static final DeferredItem<Item> DOLABRA = ITEMS.register("dolabra", () -> new Item(
            new Item.Properties().stacksTo(1)
    ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
