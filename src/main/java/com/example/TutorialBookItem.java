package com.example;

import java.util.List;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WrittenBookContentComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.text.RawFilteredPair;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TutorialBookItem extends Item {

    public TutorialBookItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!itemStack.contains(DataComponentTypes.WRITTEN_BOOK_CONTENT)) {
            WrittenBookContentComponent content = new WrittenBookContentComponent(
                    RawFilteredPair.of("Tutorial Book"),
                    "Ivans",
                    0,
                    List.of(
                        RawFilteredPair.of(Text.translatable("book.mayday.content1")),
                        RawFilteredPair.of(Text.translatable("book.mayday.content2")),
                        RawFilteredPair.of(Text.translatable("book.mayday.content3")),
                        RawFilteredPair.of(Text.translatable("book.mayday.content4"))
                    ),
                    false
            );

            itemStack.set(DataComponentTypes.WRITTEN_BOOK_CONTENT, content);
        }

        user.useBook(itemStack, hand);
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return ActionResult.SUCCESS;
    }
    
}
