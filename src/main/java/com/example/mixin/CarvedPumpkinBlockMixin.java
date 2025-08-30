package com.example.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.CarvedPumpkinBlock;

import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CarvedPumpkinBlock.class)
public abstract class CarvedPumpkinBlockMixin {
	// Replace the predicate used to check if a block is a pumpkin head with a custom one.
    @Shadow @Final @Mutable
    private static Predicate<BlockState> IS_GOLEM_HEAD_PREDICATE;

    static {
        IS_GOLEM_HEAD_PREDICATE = (state) -> state != null && state.getBlock() instanceof CarvedPumpkinBlock;
    }
}
