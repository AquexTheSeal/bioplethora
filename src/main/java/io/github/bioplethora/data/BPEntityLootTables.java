package io.github.bioplethora.data;

import io.github.bioplethora.registry.BPBlocks;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPItems;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.KilledByPlayer;
import net.minecraft.loot.conditions.RandomChance;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.Smelt;
import net.minecraftforge.fml.RegistryObject;

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
        add(BPEntities.CREPHOXL.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.CREPHOXL_FEATHER.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.CREPHOXL_STICK.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Alphem
        add(BPEntities.ALPHEM.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.WINDPIECE.get())
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
        add(BPEntities.BELLOPHGOLEM.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.BELLOPHITE.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.BELLOPHITE_CORE_FRAGMENT.get())
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 1F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Altyrus
        add(BPEntities.ALTYRUS.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.BELLOPHITE.get())
                                .apply(SetCount.setCount(RandomValueRange.between(4F, 7F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.0F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.BELLOPHITE_CORE_FRAGMENT.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 4F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.PRIMORDIAL_FRAGMENT.get())
                                .apply(SetCount.setCount(RandomValueRange.between(5F, 9F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.0F)))
                        ))
        );

        // Cuttlefish
        add(BPEntities.CUTTLEFISH.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.RAW_CUTTLEFISH_MEAT.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.5F)))
                                .apply(Smelt.smelted().when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
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
        add(BPEntities.PEAGUIN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.PEAGUIN_SCALES.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Nandbri
        add(BPEntities.NANDBRI.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.NANDBRI_SCALES.get())
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.NANDBRI_FANG.get())
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 1F)))
                                .when(KilledByPlayer.killedByPlayer())
                                .when(RandomChance.randomChance(0.12F))
                        ))
        );

        // Myliothan
        add(BPEntities.MYLIOTHAN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.ABYSSAL_SCALES.get())
                                .apply(SetCount.setCount(RandomValueRange.between(6F, 9F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.5F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Helioblade
        add(BPEntities.HELIOBLADE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.TOTEM_OF_SWERVING.get())
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 1F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Wooden Grylynen
        add(BPEntities.WOODEN_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.OAK_PLANKS)
                                .apply(SetCount.setCount(RandomValueRange.between(3F, 6F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.GREEN_GRYLYNEN_CRYSTAL.get())
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Stone Grylynen
        add(BPEntities.STONE_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.STONE)
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 4F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.GREEN_GRYLYNEN_CRYSTAL.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Golden Grylynen
        add(BPEntities.GOLDEN_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.GOLD_INGOT)
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.YELLOW_GRYLYNEN_CRYSTAL.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.3F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Iron Grylynen
        add(BPEntities.IRON_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.IRON_INGOT)
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.5F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.YELLOW_GRYLYNEN_CRYSTAL.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.3F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Diamond Grylynen
        add(BPEntities.DIAMOND_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.DIAMOND)
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.2F, 1.2F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.RED_GRYLYNEN_CRYSTAL.get())
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.3F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Netherite Grylynen
        add(BPEntities.NETHERITE_GRYLYNEN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.ANCIENT_DEBRIS)
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 1F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(Items.NETHERITE_INGOT)
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 1F)))
                                .when(KilledByPlayer.killedByPlayer())
                                .when(RandomChance.randomChance(0.0225F))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.RED_GRYLYNEN_CRYSTAL.get())
                                .apply(SetCount.setCount(RandomValueRange.between(0F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.3F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Cavern Fleignar
        add(BPEntities.CAVERN_FLEIGNAR.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.FLEIGNARITE_SCALES.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.RAW_FLENTAIR.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                                .apply(Smelt.smelted().when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Alphem King
        add(BPEntities.ALPHEM_KING.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.ALPHANUM_GEM.get())
                                .apply(SetCount.setCount(RandomValueRange.between(2F, 5F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.ALPHEM_KING_REMNANT.get())
                                .apply(SetCount.setCount(RandomValueRange.between(2F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.WINDPIECE.get())
                                .apply(SetCount.setCount(RandomValueRange.between(3F, 6F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.5F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.WINDY_ESSENCE.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPBlocks.ALPHANUM_PILLAR.get())
                                .apply(SetCount.setCount(RandomValueRange.between(6F, 12F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0.0F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Dwarf Mossadile
        add(BPEntities.DWARF_MOSSADILE.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.RAW_MOSILE.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                                .apply(Smelt.smelted().when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                        ))
        );

        // Soul Eurydn
        add(BPEntities.SOUL_EURYDN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.SOUL_CUBE.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                        ))
        );

        // Fiery Eurydn
        add(BPEntities.SOUL_EURYDN.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.FIERY_CUBE.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                        ))
        );


        // Trapjaw
        add(BPEntities.TRAPJAW.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.TRAPJAW_SCALES.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 2F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.RAW_JAWFLESH.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                                .apply(Smelt.smelted().when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );

        // Voidjaw
        add(BPEntities.VOIDJAW.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.VOIDJAW_SCALES.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantRange.exactly(1))
                        .add(ItemLootEntry.lootTableItem(BPItems.RAW_JAWFLESH.get())
                                .apply(SetCount.setCount(RandomValueRange.between(1F, 3F)))
                                .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
                                .apply(Smelt.smelted().when(EntityHasProperty.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                                .when(KilledByPlayer.killedByPlayer())
                        ))
        );
    }

    public LootPool.Builder addBasicEntity(RegistryObject<EntityType<?>> entity) {
        return LootPool.lootPool().setRolls(ConstantRange.exactly(1))
                        .apply(SetCount.setCount(RandomValueRange.between(1F, 2F)))
                        .apply(LootingEnchantBonus.lootingMultiplier(RandomValueRange.between(0F, 1.0F)))
        ;
    }

    @Override
    public Set<EntityType<?>> getKnownEntities() {
        return entityTypes;
    }
}
