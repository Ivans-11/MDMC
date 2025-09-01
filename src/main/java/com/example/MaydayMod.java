package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.block.MapColor;
import net.minecraft.item.ItemStack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaydayMod implements ModInitializer {
	public static final String MOD_ID = "mayday";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		MaydayBlocks.registerPumpkinVariant("ashin", MapColor.PINK);
		MaydayBlocks.registerLanternVariant("ashin_lantern", MapColor.PINK);
        MaydayBlocks.registerPumpkinVariant("monster", MapColor.RED);
		MaydayBlocks.registerLanternVariant("monster_lantern", MapColor.RED);
        MaydayBlocks.registerPumpkinVariant("stone", MapColor.GREEN);
		MaydayBlocks.registerLanternVariant("stone_lantern", MapColor.GREEN);
        MaydayBlocks.registerPumpkinVariant("masa", MapColor.YELLOW);
		MaydayBlocks.registerLanternVariant("masa_lantern", MapColor.YELLOW);
        MaydayBlocks.registerPumpkinVariant("ming", MapColor.BLUE);
		MaydayBlocks.registerLanternVariant("ming_lantern", MapColor.BLUE);
		MaydayBlocks.init();

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ItemStack book = new ItemStack(MaydayBlocks.TUTORIAL_BOOK);
			if (!handler.player.getInventory().contains(book)) {
				handler.player.giveItemStack(book);
			}
		});
	}
}