package com.roarkcats.dynamicmultitools.util;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import static net.minecraft.core.component.DataComponents.ATTRIBUTE_MODIFIERS;

public class ItemStackUpdates {

    public static void updateAttributes(ItemStack itemStack, ItemAttributeModifiers attributes) {

        itemStack.update(ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY, existing -> {
            var builder = ItemAttributeModifiers.builder();

            // Copy existing attributes
            existing.modifiers().forEach(e -> builder.add(e.attribute(), e.modifier(), e.slot()));
            // Append new attributes
            attributes.modifiers().forEach(e -> builder.add(e.attribute(), e.modifier(), e.slot()));

            return builder.build();
        });
    }
}
