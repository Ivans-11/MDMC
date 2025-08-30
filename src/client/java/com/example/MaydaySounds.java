package com.example;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class MaydaySounds {
    public static final String MOD_ID = "mayday";
    public static final Identifier SMOKE_ID = Identifier.of(MOD_ID, "smoke");
    public static SoundEvent SMOKE_SOUND = SoundEvent.of(SMOKE_ID);

    public static void register() {
        Registry.register(Registries.SOUND_EVENT, SMOKE_ID, SMOKE_SOUND);
    }
}