package com.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;

public class MaydayClient implements ClientModInitializer {
	public static final String MOD_ID = "mayday";
	private static MaydaySoundInstance smokeSoundInstance = null;

	@Override
	public void onInitializeClient() {
		MaydaySounds.register();

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null && client.world != null) {
				ItemStack head = client.player.getEquippedStack(EquipmentSlot.HEAD);
				String key = head.getItem().toString();
				if (key.contains("mayday")) {
					if (smokeSoundInstance == null) {
						System.out.println("Playing smoke sound");
						smokeSoundInstance = new MaydaySoundInstance(client.player, MaydaySounds.SMOKE_SOUND, SoundCategory.PLAYERS);
						client.getSoundManager().play(smokeSoundInstance);
					}
				} else if (smokeSoundInstance != null) {
					System.out.println("Stopping smoke sound");
					client.getSoundManager().stop(smokeSoundInstance);
					smokeSoundInstance = null;
				}
			}
		});
	}
}