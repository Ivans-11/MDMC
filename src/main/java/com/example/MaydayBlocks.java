package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class MaydayBlocks {

    private static final Map<String, BlockItem> PUMPKIN_ITEMS = new HashMap<>();

    public static CarvedPumpkinBlock registerPumpkinVariant(String name, MapColor mapColor) {
        // Register the pumpkin block
        CarvedPumpkinBlock block = register(
                name,
                CarvedPumpkinBlock::new,
                Settings.create()
                        .mapColor(mapColor)
                        .strength(1.0F)
                        .sounds(BlockSoundGroup.WOOD)
                        .allowsSpawning(Blocks::always)
                        .pistonBehavior(PistonBehavior.DESTROY)
        );

        // Register the pumpkin item
        BlockItem item = registerItem(
                name,
                settings -> new BlockItem(block,
                        settings.component(
                                DataComponentTypes.EQUIPPABLE,
                                EquippableComponent.builder(EquipmentSlot.HEAD)
                                        .swappable(false)
                                        .cameraOverlay(Identifier.ofVanilla("misc/pumpkinblur"))
                                        .build()
                        )
                )
        );

        // Store the pumpkin item for later use
        PUMPKIN_ITEMS.put(name, item);
        return block;
    }
    
    private static <T extends Block> T register(String path, Function<AbstractBlock.Settings, T> factory, AbstractBlock.Settings settings) {
        final Identifier identifier = Identifier.of("mayday", path);
        final RegistryKey<Block> registryKey = RegistryKey.of(RegistryKeys.BLOCK, identifier);
        return Registry.register(Registries.BLOCK, identifier, factory.apply(settings.registryKey(registryKey)));
    }

    private static <T extends Item> T registerItem(String path, Function<Item.Settings, T> itemFunction) {
        final Identifier identifier = Identifier.of("mayday", path);
		final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, identifier);
		return Registry.register(Registries.ITEM, registryKey, itemFunction.apply(new Item.Settings().registryKey(registryKey)));
	}
    
    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            PUMPKIN_ITEMS.values().forEach(entries::add);
        });// Register the pumpkin items to the natural items group
    }
}