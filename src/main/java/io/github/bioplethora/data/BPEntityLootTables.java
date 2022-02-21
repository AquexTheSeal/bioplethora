package io.github.bioplethora.data;

import io.github.bioplethora.registry.BioplethoraEntities;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;

import java.util.HashSet;
import java.util.Set;

public class BPEntityLootTables extends EntityLootTables {

    private final Set<EntityType<?>> entityTypes = new HashSet<>();

    @Override
    public void add(EntityType<?> entity, LootTable.Builder builder) {
        super.add(entity, builder);
        entityTypes.add(entity);
    }

    @Override
    protected void addTables() {

        // Crephoxl
        add(BioplethoraEntities.CREPHOXL.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.CREPHOXL_FEATHER.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.CREPHOXL_STICK.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Alphem
        add(BioplethoraEntities.ALPHEM.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.WINDPIECE.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.OAK_PLANKS)
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 4F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.STICK)
                                .apply(SetCount.setCount(RandomValueRange.between(2F, 6F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Bellophgolem
        add(BioplethoraEntities.BELLOPHGOLEM.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.BELLOPHITE.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.BELLOPHITE_CORE_FRAGMENT.get())
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 1F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Altyrus
        add(BioplethoraEntities.ALTYRUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.BELLOPHITE.get())
                                .apply(SetCount.setCount(RandomValueRange.between(4F, 7F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.BELLOPHITE_CORE_FRAGMENT.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 4F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.PRIMORDIAL_FRAGMENT.get())
                                .apply(SetCount.setCount(RandomValueRange.between(5F, 9F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Cuttlefish
        add(BioplethoraEntities.CUTTLEFISH.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.RAW_CUTTLEFISH_MEAT.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.5F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.INK_SAC)
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Peaguin
        add(BioplethoraEntities.PEAGUIN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.PEAGUIN_SCALES.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Nandbri
        add(BioplethoraEntities.NANDBRI.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.NANDBRI_SCALES.get())
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.NANDBRI_FANG.get())
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 1F)))
                                .when(KilledByPlayer.killedByPlayer())
                                .when(RandomChance.randomChance(0.12F))
                        ))
        );

        // Myliothan
        add(BioplethoraEntities.MYLIOTHAN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.ABYSSAL_SCALES.get())
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 4F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.5F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Helioblade
        add(BioplethoraEntities.HELIOBLADE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.TOTEM_OF_SWERVING.get())
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 1F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Wooden Grylynen
        add(BioplethoraEntities.WOODEN_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.OAK_PLANKS)
                                .apply(SetCount.setCount(RandomValueRange.between(3F, 6F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.GREEN_GRYLYNEN_CRYSTAL.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Stone Grylynen
        add(BioplethoraEntities.STONE_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.STONE)
                                .apply(SetCount.setCount(RandomValueRange.between(3F, 6F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.GREEN_GRYLYNEN_CRYSTAL.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Golden Grylynen
        add(BioplethoraEntities.GOLDEN_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.GOLD_INGOT)
                                .apply(SetCount.setCount(RandomValueRange.between(2F, 5F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.YELLOW_GRYLYNEN_CRYSTAL.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.3F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Iron Grylynen
        add(BioplethoraEntities.IRON_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.IRON_INGOT)
                                .apply(SetCount.setCount(RandomValueRange.between(2F, 5F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.YELLOW_GRYLYNEN_CRYSTAL.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.3F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Diamond Grylynen
        add(BioplethoraEntities.DIAMOND_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.DIAMOND)
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.2F, 1.2F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.RED_GRYLYNEN_CRYSTAL.get())
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.3F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Netherite Grylynen
        add(BioplethoraEntities.NETHERITE_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.ANCIENT_DEBRIS)
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.2F, 1.2F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.NETHERITE_INGOT)
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 1F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                                .when(RandomChance.randomChance(0.125F))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.RED_GRYLYNEN_CRYSTAL.get())
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.3F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );
    }

    @Override
    public Set<EntityType<?>> getKnownEntities() {
        return entityTypes;
    }
}
