package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.registry.BioplethoraEntities;
import io.github.bioplethora.registry.BioplethoraTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BioEntityTagsProvider extends EntityTypeTagsProvider {

    public BioEntityTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, Bioplethora.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(EntityTypeTags.ARROWS)
                .add(BioplethoraEntities.BELLOPHITE_ARROW.get())
                .add(BioplethoraEntities.WIND_ARROW.get())
        ;

        // Wastelands of Baedoor Integration
        tag(BioplethoraTags.Entities.AVOIDER_KILLABLE)
                .add(BioplethoraEntities.CREPHOXL.get())
                .add(BioplethoraEntities.ALPHEM.get())
                .add(BioplethoraEntities.DWARF_MOSSADILE.get())
                .add(BioplethoraEntities.GAUGALEM.get())
                .add(BioplethoraEntities.BELLOPHGOLEM.get())
                .add(BioplethoraEntities.WOODEN_GRYLYNEN.get())
                .add(BioplethoraEntities.STONE_GRYLYNEN.get())
                .add(BioplethoraEntities.GOLDEN_GRYLYNEN.get())
                .add(BioplethoraEntities.IRON_GRYLYNEN.get())
                .add(BioplethoraEntities.DIAMOND_GRYLYNEN.get())
                .add(BioplethoraEntities.NETHERITE_GRYLYNEN.get())
        ;
        tag(BioplethoraTags.Entities.AUTOMATON_TYPE)
                .add(BioplethoraEntities.BELLOPHGOLEM.get())
                .add(BioplethoraEntities.ALTYRUS.get())
                .add(BioplethoraEntities.ALPHEM_KING.get())
        ;
    }
}
