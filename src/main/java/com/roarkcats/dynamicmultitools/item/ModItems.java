package com.roarkcats.dynamicmultitools.item;

import com.roarkcats.dynamicmultitools.DynamicMultitools;
import com.roarkcats.dynamicmultitools.datapack.DynamicTier;
import com.roarkcats.dynamicmultitools.item.custom.DynamicDiggerItem;
import com.roarkcats.dynamicmultitools.item.custom.multitools.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DynamicMultitools.MODID);

    public static final DynamicTier DEFAULT_TIER = new DynamicTier("minecraft", "iron", -1, Tiers.IRON);

    public static final DeferredItem<DolabraItem> DOLABRA = ITEMS.register("dolabra", () -> new DolabraItem(DEFAULT_TIER, new Item.Properties()));
    public static final DeferredItem<AdzeItem> ADZE = ITEMS.register("adze", () -> new AdzeItem(DEFAULT_TIER, new Item.Properties()));
    public static final DeferredItem<PulaskiItem> PULASKI = ITEMS.register("pulaski", () -> new PulaskiItem(DEFAULT_TIER, new Item.Properties()));
    public static final DeferredItem<MattockItem> MATTOCK = ITEMS.register("mattock", () -> new MattockItem(DEFAULT_TIER, new Item.Properties()));
    public static final DeferredItem<ExcavatorItem> EXCAVATOR = ITEMS.register("excavator", () -> new ExcavatorItem(DEFAULT_TIER, new Item.Properties()));
    public static final DeferredItem<SarchielloItem> SARCHIELLO = ITEMS.register("sarchiello", () -> new SarchielloItem(DEFAULT_TIER, new Item.Properties()));

    public static final List<DeferredItem<? extends DynamicDiggerItem>> MULTITOOLS = List.of(
            DOLABRA, PULASKI, MATTOCK, ADZE, SARCHIELLO, EXCAVATOR
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
