package com.example;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GlowStickItem extends Item {
    public GlowStickItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true; // Always glint
    }
}