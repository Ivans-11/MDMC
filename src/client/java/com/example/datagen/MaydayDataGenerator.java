package com.example.datagen;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.example.MaydayBlocks;
import com.example.MaydayClient;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
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
        pack.addProvider(LootTableProvider::new);
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
                        Text.translatable("advance.mayday.tutorial_book"), // title
                        Text.translatable("advance.mayday.welcome"), // description
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

            AdvancementEntry successAdvancement = Advancement.Builder.create().parent(rootAdvancement)
                .display(
                        MaydayBlocks.MAYDAY_BANNER_PATTERN,
                        Text.translatable("advance.mayday.success"),
                        Text.translatable("advance.mayday.mayday_banner_pattern"),
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
                .build(consumer, MaydayClient.MOD_ID + "/success");
            
            AdvancementEntry finalAdvancement = Advancement.Builder.create().parent(successAdvancement)
                .display(
                        MaydayBlocks.MAYDAY_BANNER_PATTERN,
                        Text.translatable("advance.mayday.final"),
                        Text.translatable("advance.mayday.final.description"),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        true
                )
                .criterion("got_ashin_lantern", InventoryChangedCriterion.Conditions.items(MaydayBlocks.getPumpkinItem("ashin_lantern")))
                .criterion("got_monster_lantern", InventoryChangedCriterion.Conditions.items(MaydayBlocks.getPumpkinItem("monster_lantern")))
                .criterion("got_stone_lantern", InventoryChangedCriterion.Conditions.items(MaydayBlocks.getPumpkinItem("stone_lantern")))
                .criterion("got_masa_lantern", InventoryChangedCriterion.Conditions.items(MaydayBlocks.getPumpkinItem("masa_lantern")))
                .criterion("got_ming_lantern", InventoryChangedCriterion.Conditions.items(MaydayBlocks.getPumpkinItem("ming_lantern")))
                .build(consumer, MaydayClient.MOD_ID + "/final");
        }

        private AdvancementEntry gotPumpkinsAdvancement(Consumer<AdvancementEntry> consumer, String name, AdvancementEntry parent) {
            return Advancement.Builder.create().parent(parent)
                .display(
                        MaydayBlocks.getPumpkinItem(name),
                        Text.translatable("advance.mayday." + name),
                        Text.translatable("advance.mayday." + name + ".description"),
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
                        Text.translatable("advance.mayday." + name),
                        Text.translatable("advance.mayday." + name + ".description"),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        true
                )
               .criterion("got_" + name, InventoryChangedCriterion.Conditions.items(MaydayBlocks.getPumpkinItem(name)))
               .build(consumer, MaydayClient.MOD_ID + "/" + name);
        }
    }

    static class LootTableProvider extends SimpleFabricLootTableProvider {
        public LootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
          super(output, registryLookup, LootContextTypes.ADVANCEMENT_REWARD);
        }
       
        public static final RegistryKey<LootTable> GIVE_BANNER_PATTERN = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MaydayClient.MOD_ID, "rewards/give_banner_pattern"));
       
        @Override
        public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
            lootTableBiConsumer.accept(GIVE_BANNER_PATTERN, LootTable.builder()
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                    .with(ItemEntry.builder(MaydayBlocks.MAYDAY_BANNER_PATTERN)
            )));
        }
    }
}
