package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BPEntities;
import io.github.bioplethora.registry.BPTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.EntityTypeTagsProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BioEntityTagsProvider extends EntityTypeTagsProvider {

    public BioEntityTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, Bioplethora.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(EntityTypeTags.ARROWS)
                .add(BPEntities.BELLOPHITE_ARROW.get())
                .add(BPEntities.WIND_ARROW.get())
        ;

        tag(BPTags.Entities.FLEIGNAR_TARGETS)
                .add(EntityType.ZOMBIE)
                .add(EntityType.SPIDER)
                .add(EntityType.CAVE_SPIDER)
                .addTag(EntityTypeTags.SKELETONS)
                .addTag(EntityTypeTags.RAIDERS)
        ;

        // Wastelands of Baedoor Integration
        tag(BPTags.Entities.AVOIDER_KILLABLE)
                .add(BPEntities.CREPHOXL.get())
                .add(BPEntities.ALPHEM.get())
                .add(BPEntities.DWARF_MOSSADILE.get())
                .add(BPEntities.GAUGALEM.get())
                .add(BPEntities.FROSTBITE_GOLEM.get())
                .add(BPEntities.WOODEN_GRYLYNEN.get())
                .add(BPEntities.STONE_GRYLYNEN.get())
                .add(BPEntities.GOLDEN_GRYLYNEN.get())
                .add(BPEntities.IRON_GRYLYNEN.get())
                .add(BPEntities.DIAMOND_GRYLYNEN.get())
                .add(BPEntities.NETHERITE_GRYLYNEN.get())
                .add(BPEntities.TRAPJAW.get())
        ;
        tag(BPTags.Entities.AUTOMATON_TYPE)
                .add(BPEntities.FROSTBITE_GOLEM.get())
                .add(BPEntities.ALTYRUS.get())
                .add(BPEntities.ALPHEM_KING.get())
        ;
    }
}
