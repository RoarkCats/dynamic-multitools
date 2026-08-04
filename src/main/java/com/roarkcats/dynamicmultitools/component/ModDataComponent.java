package com.roarkcats.dynamicmultitools.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.UnaryOperator;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.MODID;

public class ModDataComponent {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MODID);

    // -- Custom Components --
    // added vanilla 1.21.2 :/
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Ingredient>> REPAIR_MATERIAL =
            register("repair_material", builder -> builder.persistent(Ingredient.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ENCHANTABILITY =
            register("enchantability", builder -> builder.persistent(Codec.INT));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<List<Integer>>> TEXTURE_TINTS =
            register("texture_tints", builder -> builder.persistent(Codec.list(Codec.INT)));
    // -- -- -- -- -- -- -- --

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>>
            register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return DATA_COMPONENT_TYPES.register(name, () -> builder.apply(DataComponentType.builder()).build());
    } // Holy method

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
