package com.example;

import net.minecraft.block.entity.BannerPattern;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class MaydayBannerPatterns {
    public static final Identifier MAYDAY_ID = Identifier.of(MaydayMod.MOD_ID, "mayday");
    public static final RegistryKey<BannerPattern> MAYDAY = RegistryKey.of(RegistryKeys.BANNER_PATTERN, MAYDAY_ID);

    public static void bootstrap(Registerable<BannerPattern> registry){
        registry.register(MAYDAY, new BannerPattern(MAYDAY_ID, "block.mayday.banner.mayday"));
    }
}