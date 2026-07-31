package com.roarkcats.dynamicmultitools.item;

import com.roarkcats.dynamicmultitools.DynamicMultitools;
import com.roarkcats.dynamicmultitools.item.custom.multitools.DolabraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DynamicMultitools.MODID);

    public static final DeferredItem<DolabraItem> DOLABRA = ITEMS.register("dolabra", () -> new DolabraItem(Tiers.IRON, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
