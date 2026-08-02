package com.roarkcats.dynamicmultitools.item;

import com.roarkcats.dynamicmultitools.DynamicMultitools;
import com.roarkcats.dynamicmultitools.datapack.DynamicTier;
import com.roarkcats.dynamicmultitools.item.custom.multitools.DolabraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DynamicMultitools.MODID);

    public static final DynamicTier DEFAULT_TIER = new DynamicTier("minecraft", "iron", -1, Tiers.IRON);

    public static final DeferredItem<DolabraItem> DOLABRA = ITEMS.register("dolabra", () -> new DolabraItem(DEFAULT_TIER, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
