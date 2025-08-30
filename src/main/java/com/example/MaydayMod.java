package com.example;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.MapColor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaydayMod implements ModInitializer {
	public static final String MOD_ID = "mayday";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		MaydayBlocks.registerPumpkinVariant("ashin", MapColor.PINK);
        MaydayBlocks.registerPumpkinVariant("monster", MapColor.RED);
        MaydayBlocks.registerPumpkinVariant("stone", MapColor.GREEN);
        MaydayBlocks.registerPumpkinVariant("masa", MapColor.YELLOW);
        MaydayBlocks.registerPumpkinVariant("ming", MapColor.BLUE);
		MaydayBlocks.init();
	}
}