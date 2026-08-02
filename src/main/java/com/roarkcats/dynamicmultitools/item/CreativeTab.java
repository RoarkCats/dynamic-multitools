package com.roarkcats.dynamicmultitools.item;

import com.roarkcats.dynamicmultitools.item.custom.multitools.DolabraItem;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.ClientModEvents.DYNAMIC_TIER_REGISTRY;
import static com.roarkcats.dynamicmultitools.DynamicMultitools.LOGGER;
import static com.roarkcats.dynamicmultitools.DynamicMultitools.MODID;
import static com.roarkcats.dynamicmultitools.component.ModDataComponent.ENCHANTABILITY;
import static com.roarkcats.dynamicmultitools.component.ModDataComponent.REPAIR_MATERIAL;
import static net.minecraft.core.component.DataComponents.*;

public class CreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final Supplier<CreativeModeTab> DYNAMIC_MULTITOOLS_TAB = CREATIVE_TAB.register("main", () ->
        CreativeModeTab.builder()
                .title(Component.translatable("itemGroup."+MODID+".main"))
                .icon(() -> ModItems.DOLABRA.get().getDefaultInstance())
                .displayItems((displayParameters, output) -> {
                    displayParameters.holders().lookup(DYNAMIC_TIER_REGISTRY).ifPresent(dynamicTierRegistry -> {
                        dynamicTierRegistry.listElements().map(Holder.Reference::value).forEach(tier -> {
                            output.accept(DolabraItem.createTieredStack(ModItems.DOLABRA.toStack(), tier));
                        });
                    });
                }).build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_TAB.register(eventBus);
    }
}
