package com.roarkcats.dynamicmultitools.compat;

import com.roarkcats.dynamicmultitools.item.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.roarkcats.dynamicmultitools.DynamicMultitools.MODID;

@JeiPlugin
public class JeiModPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(MODID, "jei_plugin");
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {

        registration.registerSubtypeInterpreter(
                ModItems.DOLABRA.get(),
                new ISubtypeInterpreter<ItemStack>() {
                    @Override
                    public @Nullable Object getSubtypeData(ItemStack ingredient, UidContext context) {
                        var data = ingredient.get(DataComponents.ITEM_NAME);
                        if (data != null) return data.hashCode();
                        else return null;
                    }

                    @Override
                    public String getLegacyStringSubtypeInfo(ItemStack ingredient, UidContext context) {
                        var data = ingredient.get(DataComponents.ITEM_NAME);
                        if (data != null) return Integer.toHexString(data.hashCode());
                        else return "";
                    }
                }
        );
    }
}
