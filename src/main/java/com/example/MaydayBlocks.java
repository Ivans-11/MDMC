package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class MaydayBlocks {

    private static final List<BlockItem> PUMPKIN_ITEMS = new ArrayList<>();
    private static final Map<String, Integer> PUMPKIN_INDEX = new HashMap<>();

    public static final Item MAYDAY_BANNER_PATTERN = registerItem(
            "mayday_banner_pattern",
            Item::new,
            new Item.Settings()
                    .maxCount(1)
                    .component(
                        DataComponentTypes.PROVIDES_BANNER_PATTERNS,
                        TagKey.of(RegistryKeys.BANNER_PATTERN, Identifier.of(MaydayMod.MOD_ID, "pattern_item/mayday"))
                    )
    );

    public static final Item TUTORIAL_BOOK = registerItem(
            "tutorial_book",
            TutorialBookItem::new,
            new Item.Settings()
                   .maxCount(1)
    );

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

        // Register the item
        BlockItem item = registerItem(
                name,
                settings -> new BlockItem(block,
                        settings.component(
                                DataComponentTypes.EQUIPPABLE,
                                EquippableComponent.builder(EquipmentSlot.HEAD)
                                        .swappable(false)
                                        //.cameraOverlay(Identifier.ofVanilla("misc/pumpkinblur"))
                                        .cameraOverlay(Identifier.of(MaydayMod.MOD_ID, "misc/" + name))
                                        .build()
                        )
                )
        );

        // Store the item for later use
        PUMPKIN_INDEX.put(name, PUMPKIN_ITEMS.size());
        PUMPKIN_ITEMS.add(item);
        return block;
    }

    public static CarvedPumpkinBlock registerLanternVariant(String name, MapColor mapColor) {
        // Register the latern block
        CarvedPumpkinBlock block = register(
                name,
                CarvedPumpkinBlock::new,
                Settings.create()
                        .mapColor(mapColor)
                        .strength(1.0F)
                        .sounds(BlockSoundGroup.WOOD)
                        .luminance((state) -> { return 15; })
                        .allowsSpawning(Blocks::always)
                        .pistonBehavior(PistonBehavior.DESTROY)
        );

        // Register the item
        BlockItem item = registerItem(name, settings -> new BlockItem(block, settings));

        // Store the item for later use
        PUMPKIN_INDEX.put(name, PUMPKIN_ITEMS.size());
        PUMPKIN_ITEMS.add(item);
        return block;
    }
    
    private static <T extends Block> T register(String path, Function<AbstractBlock.Settings, T> factory, AbstractBlock.Settings settings) {
        final Identifier identifier = Identifier.of(MaydayMod.MOD_ID, path);
        final RegistryKey<Block> registryKey = RegistryKey.of(RegistryKeys.BLOCK, identifier);
        return Registry.register(Registries.BLOCK, identifier, factory.apply(settings.registryKey(registryKey)));
    }

    private static <T extends Item> T registerItem(String path, Function<Item.Settings, T> itemFunction) {
        final Identifier identifier = Identifier.of(MaydayMod.MOD_ID, path);
		final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, identifier);
		return Registry.register(Registries.ITEM, registryKey, itemFunction.apply(new Item.Settings().registryKey(registryKey)));
	}

    private static Item registerItem(String path, Function<Item.Settings, Item> itemFunction, Item.Settings settings) {
        final Identifier identifier = Identifier.of(MaydayMod.MOD_ID, path);
		final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, identifier);
		return Registry.register(Registries.ITEM, registryKey, itemFunction.apply(settings.registryKey(registryKey)));
	}
    
    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            // Add the items to the natural items group
            for (BlockItem item : PUMPKIN_ITEMS) {
                entries.addBefore(Items.HAY_BLOCK, item);
            }
        });
        // Add banner pattern
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.addAfter(Items.GUSTER_BANNER_PATTERN, MAYDAY_BANNER_PATTERN);
        });
        // Add tutorial book
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.addAfter(Items.WRITABLE_BOOK, TUTORIAL_BOOK);
        });
    }

    // Get the pumpkin items by name
    public static Item getPumpkinItem(String name) {
        return PUMPKIN_ITEMS.get(PUMPKIN_INDEX.get(name));
    }
}