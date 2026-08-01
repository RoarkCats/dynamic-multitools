package com.roarkcats.dynamicmultitools.item;

import com.roarkcats.dynamicmultitools.item.custom.DynamicDiggerItem;
import com.roarkcats.dynamicmultitools.item.custom.multitools.DolabraItem;
import com.roarkcats.dynamicmultitools.util.Tags;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Optional;
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

//                    output.accept(ModItems.DOLABRA);

                    displayParameters.holders().lookup(DYNAMIC_TIER_REGISTRY).ifPresent(dynamicTierRegistry -> {
                        dynamicTierRegistry.listElements().map(Holder.Reference::value).forEach(tier -> {
                            var dolabra = ModItems.DOLABRA.toStack();
                            // move this all to DynamicTiered|Digger|DolabraItem function line to create an instance from a DynamicTier
                            // also make them support DynamicTier as well as normal Tier
                            try { // known bug: must reference any tier otherwise tier.tierBase.get() will crash
                                dolabra.set(ITEM_NAME, Component.translatable("dynamic_tier." + tier.modId() + "." + tier.material()).append(" ").append(Component.translatable("item." + MODID + ".dolabra")));
                                dolabra.set(DYED_COLOR, new DyedItemColor(tier.color(), false));
                                dolabra.set(MAX_DAMAGE, tier.durability().orElse(tier.tierBase().get().getUses()));
                                dolabra.set(ENCHANTABILITY, tier.enchantability().orElse(tier.tierBase().get().getEnchantmentValue()));
                                dolabra.set(REPAIR_MATERIAL, tier.repairIngredient().orElse(tier.tierBase().get().getRepairIngredient()));
                                dolabra.set(ATTRIBUTE_MODIFIERS, DolabraItem.attributes(3F + tier.damageBonus().orElse(tier.tierBase().get().getAttackDamageBonus()), -2.8F));
                                dolabra.set(TOOL, DolabraItem.tool(List.of(
                                        DolabraItem.toolRule(tier.incorrectBlocksForDrops().orElse(tier.tierBase().get().getIncorrectBlocksForDrops())),
                                        DolabraItem.toolRule(Tags.Blocks.MINEABLE_WITH_DOLABRA, tier.speed().orElse(tier.tierBase().get().getSpeed()) * 0.8F)
                                )));
                            } catch (Exception e) {
                                LOGGER.error("Error creating dolabra for dynamic tier {}.{}: {}", tier.modId(), tier.material(), e);
                            }
                            output.accept(dolabra);
                        });
                    });
                }).build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_TAB.register(eventBus);
    }
}
