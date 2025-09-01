package com.example;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class MaydaySounds {
    public static final String MOD_ID = MaydayClient.MOD_ID;

    public static final Identifier REAL_ID = Identifier.of(MOD_ID, "real");
    public static final Identifier NINE_ID = Identifier.of(MOD_ID, "nine");
    public static final Identifier STAR_ID = Identifier.of(MOD_ID, "star");
    public static final Identifier THING_ID = Identifier.of(MOD_ID, "thing");
    public static final Identifier SUPERMAN_ID = Identifier.of(MOD_ID, "superman");

    public static SoundEvent REAL_SOUND = SoundEvent.of(REAL_ID);
    public static SoundEvent NINE_SOUND = SoundEvent.of(NINE_ID);
    public static SoundEvent STAR_SOUND = SoundEvent.of(STAR_ID);
    public static SoundEvent THING_SOUND = SoundEvent.of(THING_ID);
    public static SoundEvent SUPERMAN_SOUND = SoundEvent.of(SUPERMAN_ID);

    public static void register() {
        Registry.register(Registries.SOUND_EVENT, REAL_ID, REAL_SOUND);
        Registry.register(Registries.SOUND_EVENT, NINE_ID, NINE_SOUND);
        Registry.register(Registries.SOUND_EVENT, STAR_ID, STAR_SOUND);
        Registry.register(Registries.SOUND_EVENT, THING_ID, THING_SOUND);
        Registry.register(Registries.SOUND_EVENT, SUPERMAN_ID, SUPERMAN_SOUND);
    }

    public static SoundEvent getSound(String name) {
        switch (name) {
            case "mayday:ashin": return REAL_SOUND;
            case "mayday:monster": return NINE_SOUND;
            case "mayday:stone": return STAR_SOUND;
            case "mayday:masa": return THING_SOUND;
            case "mayday:ming": return SUPERMAN_SOUND;
            default: return null;
        }
    }
}