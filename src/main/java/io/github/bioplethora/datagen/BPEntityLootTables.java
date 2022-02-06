package io.github.bioplethora.datagen;

import io.github.bioplethora.registry.BioplethoraEntities;
import io.github.bioplethora.registry.BioplethoraItems;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
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
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.5F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.CREPHOXL_STICK.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 2.0F)))
                        ))
        );

        // Alphem
        add(BioplethoraEntities.ALPHEM.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.WINDPIECE.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.OAK_PLANKS)
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 4F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.STICK)
                                .apply(SetCount.setCount(RandomValueRange.between(2F, 6F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                        ))
        );

        // Bellophgolem
        add(BioplethoraEntities.BELLOPHGOLEM.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.BELLOPHITE.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.5F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.BELLOPHITE_CORE_FRAGMENT.get())
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 1F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F)))
                        ))
        );

        // Altyrus
        add(BioplethoraEntities.ALTYRUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.BELLOPHITE.get())
                                .apply(SetCount.setCount(RandomValueRange.between(4F, 7F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 2F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.BELLOPHITE_CORE_FRAGMENT.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 4F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.PRIMORDIAL_FRAGMENT.get())
                                .apply(SetCount.setCount(RandomValueRange.between(5F, 9F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 2F)))
                        ))
        );

        // Cuttlefish
        add(BioplethoraEntities.CUTTLEFISH.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.RAW_CUTTLEFISH_MEAT.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.5F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.INK_SAC)
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                        ))
        );

        // Peaguin
        add(BioplethoraEntities.PEAGUIN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.PEAGUIN_SCALES.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F)))
                        ))
        );

        // Nandbri
        add(BioplethoraEntities.NANDBRI.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.NANDBRI_SCALES.get())
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F)))
                        ))
        );

        // Myliothan
        add(BioplethoraEntities.MYLIOTHAN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.ABYSSAL_SCALES.get())
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 4F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.5F)))
                        ))
        );

        // Helioblade
        add(BioplethoraEntities.HELIOBLADE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.TOTEM_OF_SWERVING.get())
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 1F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F)))
                        ))
        );

        // Wooden Grylynen
        add(BioplethoraEntities.WOODEN_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.OAK_PLANKS)
                                .apply(SetCount.setCount(RandomValueRange.between(3F, 6F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.GREEN_GRYLYNEN_CRYSTAL.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                        ))
        );

        // Stone Grylynen
        add(BioplethoraEntities.STONE_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.STONE)
                                .apply(SetCount.setCount(RandomValueRange.between(3F, 6F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.GREEN_GRYLYNEN_CRYSTAL.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                        ))
        );

        // Golden Grylynen
        add(BioplethoraEntities.GOLDEN_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.GOLD_INGOT)
                                .apply(SetCount.setCount(RandomValueRange.between(2F, 5F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.YELLOW_GRYLYNEN_CRYSTAL.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.3F)))
                        ))
        );

        // Iron Grylynen
        add(BioplethoraEntities.IRON_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.IRON_INGOT)
                                .apply(SetCount.setCount(RandomValueRange.between(2F, 5F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BioplethoraItems.YELLOW_GRYLYNEN_CRYSTAL.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.3F)))
                        ))
        );
    }

    @Override
    public Set<EntityType<?>> getKnownEntities() {
        return entityTypes;
    }
}
