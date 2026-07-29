package com.roarkcats.dynamicmultitools.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.MODID;

public class CreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final Supplier<CreativeModeTab> DYNAMIC_MULTITOOLS_TAB = CREATIVE_TAB.register("main", () ->
        CreativeModeTab.builder()
                .title(Component.translatable("itemGroup."+MODID+".main"))
                .icon(() -> Items.DOLABRA.get().getDefaultInstance())
                .displayItems((displayParameters, output) -> {
                    output.accept(Items.DOLABRA);
                })
                .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_TAB.register(eventBus);
    }
}
