package com.example.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.example.MaydayBlocks;
import com.example.MaydayClient;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MaydayDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(AdvancementsProvider::new);
    }

    static class AdvancementsProvider extends FabricAdvancementProvider {
        protected AdvancementsProvider(FabricDataOutput dataGenerator, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
            super(dataGenerator, registryLookup);
        }
 
        @Override
        public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
            // Create root advancement
            AdvancementEntry rootAdvancement = Advancement.Builder.create()
                .display(
                        MaydayBlocks.TUTORIAL_BOOK,
                        Text.literal("Welcome to the Maydayland!"), // title
                        Text.literal("You received the tutorial book."), // description
                        Identifier.of("gui/advancements/backgrounds/adventure"), // background
                        AdvancementFrame.TASK, // options: TASK, CHALLENGE, GOAL
                        true, // Show toast top right
                        true, // Announce to chat
                        false // Hidden in the advancement tab
                )
                .criterion("got_tutorial_book", InventoryChangedCriterion.Conditions.items(MaydayBlocks.TUTORIAL_BOOK))
                .build(consumer, MaydayClient.MOD_ID + "/root");

            AdvancementEntry gotAshinAdvancement = gotPumpkinsAdvancement(consumer, "ashin", rootAdvancement);
            AdvancementEntry gotMonsterAdvancement = gotPumpkinsAdvancement(consumer, "monster", rootAdvancement);
            AdvancementEntry gotStoneAdvancement = gotPumpkinsAdvancement(consumer, "stone", rootAdvancement);
            AdvancementEntry gotMasaAdvancement = gotPumpkinsAdvancement(consumer, "masa", rootAdvancement);
            AdvancementEntry gotMingAdvancement = gotPumpkinsAdvancement(consumer, "ming", rootAdvancement);

            AdvancementEntry gotAshinLanternAdvancement = gotLanternsAdvancement(consumer, "ashin_lantern", gotAshinAdvancement);
            AdvancementEntry gotMonsterLanternAdvancement = gotLanternsAdvancement(consumer, "monster_lantern", gotMonsterAdvancement);
            AdvancementEntry gotStoneLanternAdvancement = gotLanternsAdvancement(consumer, "stone_lantern", gotStoneAdvancement);
            AdvancementEntry gotMasaLanternAdvancement = gotLanternsAdvancement(consumer, "masa_lantern", gotMasaAdvancement);
            AdvancementEntry gotMingLanternAdvancement = gotLanternsAdvancement(consumer, "ming_lantern", gotMingAdvancement);

            AdvancementEntry goalAdvancement = Advancement.Builder.create().parent(rootAdvancement)
                .display(
                        MaydayBlocks.MAYDAY_BANNER_PATTERN,
                        Text.literal("Success"),
                        Text.literal("Good job! You received the Mayday Banner Pattern as a reward. You can now make a banner with it."),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        true
                )
                .criterion("got_ashin", InventoryChangedCriterion.Conditions.items(MaydayBlocks.getPumpkinItem("ashin")))
                .criterion("got_monster", InventoryChangedCriterion.Conditions.items(MaydayBlocks.getPumpkinItem("monster")))
                .criterion("got_stone", InventoryChangedCriterion.Conditions.items(MaydayBlocks.getPumpkinItem("stone")))
                .criterion("got_masa", InventoryChangedCriterion.Conditions.items(MaydayBlocks.getPumpkinItem("masa")))
                .criterion("got_ming", InventoryChangedCriterion.Conditions.items(MaydayBlocks.getPumpkinItem("ming")))
                .rewards(AdvancementRewards.Builder.loot(RegistryKey.of(
                    RegistryKeys.LOOT_TABLE,
                    Identifier.of(MaydayClient.MOD_ID, "rewards/give_banner_pattern")
                )))
                .build(consumer, MaydayClient.MOD_ID + "/vectory");
        }

        private AdvancementEntry gotPumpkinsAdvancement(Consumer<AdvancementEntry> consumer, String name, AdvancementEntry parent) {
            return Advancement.Builder.create().parent(parent)
                .display(
                        MaydayBlocks.getPumpkinItem(name),
                        Text.literal(name.toUpperCase() + " PUMPKIN"),
                        Text.literal("You got the " + name + " pumpkin! Try wearing it or use it to make a pumpkin lantern."),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
               .criterion("got_" + name, InventoryChangedCriterion.Conditions.items(MaydayBlocks.getPumpkinItem(name)))
               .build(consumer, MaydayClient.MOD_ID + "/" + name);
        }

        private AdvancementEntry gotLanternsAdvancement(Consumer<AdvancementEntry> consumer, String name, AdvancementEntry parent) {
            return Advancement.Builder.create().parent(parent)
                .display(
                        MaydayBlocks.getPumpkinItem(name),
                        Text.literal(name.split(name + "_")[0].toUpperCase() + " LANTERN"),
                        Text.literal("You got the " + name.split(name + "_")[0] + " lantern!"),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                )
               .criterion("got_" + name, InventoryChangedCriterion.Conditions.items(MaydayBlocks.getPumpkinItem(name)))
               .build(consumer, MaydayClient.MOD_ID + "/" + name);
        }
    }
}
