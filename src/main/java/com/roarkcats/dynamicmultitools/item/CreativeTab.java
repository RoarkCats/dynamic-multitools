package com.roarkcats.dynamicmultitools.item;

import com.roarkcats.dynamicmultitools.datapack.DynamicTier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Comparator;
import java.util.function.Supplier;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.MODID;
import static com.roarkcats.dynamicmultitools.datapack.Server.DYNAMIC_TIER_REGISTRY;

public class CreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final Supplier<CreativeModeTab> DYNAMIC_MULTITOOLS_TAB = CREATIVE_TAB.register("main", () ->
        CreativeModeTab.builder()
                .title(Component.translatable("itemGroup."+MODID+".main"))
                .icon(() -> ModItems.DOLABRA.get().getDefaultInstance())
                .displayItems((displayParameters, output) -> {
                    displayParameters.holders().lookup(DYNAMIC_TIER_REGISTRY).ifPresent(dynamicTierRegistry -> {
                        dynamicTierRegistry.listElements().map(Holder.Reference::value).sorted(Comparator.comparingDouble(DynamicTier::getSpeed)).forEach(tier -> {

                            ModItems.MULTITOOLS.stream().forEachOrdered(multitool -> {
                                output.accept(multitool.get().createTieredStack(tier));
                            });
                        });
                    });
                }).build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_TAB.register(eventBus);
    }
}
