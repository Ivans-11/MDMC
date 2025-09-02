package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;

public class MaydayClient implements ClientModInitializer {
	public static final String MOD_ID = "mayday";
	private static MaydaySoundInstance maydaySoundInstance = null;

	@Override
	public void onInitializeClient() {
		MaydaySounds.register();

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null && client.world != null) {
				ItemStack head = client.player.getEquippedStack(EquipmentSlot.HEAD);
				String key = head.getItem().toString();
				if (key.contains("mayday")) {
					if (maydaySoundInstance == null) {
						client.getMusicTracker().stop();
						maydaySoundInstance = new MaydaySoundInstance(client.player, MaydaySounds.getSound(key), SoundCategory.PLAYERS, key);
						client.getSoundManager().play(maydaySoundInstance);
					} else if (!maydaySoundInstance.name.equals(key)) {
						maydaySoundInstance.stop();
						maydaySoundInstance = new MaydaySoundInstance(client.player, MaydaySounds.getSound(key), SoundCategory.PLAYERS, key);
						client.getSoundManager().play(maydaySoundInstance);
					}
				} else if (maydaySoundInstance != null) {
					maydaySoundInstance.stop();
					maydaySoundInstance = null;
					client.getMusicTracker().tick();
				}
			}
		});
	}
}