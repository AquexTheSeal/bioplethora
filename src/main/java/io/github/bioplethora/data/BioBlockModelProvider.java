package io.github.bioplethora.data;

import io.github.bioplethora.Bioplethora;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

public class BioBlockModelProvider extends BlockModelProvider {

    public BioBlockModelProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return Bioplethora.MOD_ID + " Block Models";
    }

    /**
     * List every Block that needs data generating here.
     */
    @Override
    protected void registerModels() {
        this.cubeAll("bellophite_block", bioResLoc("bellophite_block"));
        this.cubeAll("bellophite_core_block", bioResLoc("bellophite_core_block"));
        this.cubeAll("nandbri_scale_block", bioResLoc("nandbri_scale_block"));

        this.cubeAll("mirestone", bioResLoc("mirestone"));

        this.cubeAll("green_grylynen_crystal_block", bioResLoc("green_grylynen_crystal_block"));
        this.cubeAll("yellow_grylynen_crystal_block", bioResLoc("yellow_grylynen_crystal_block"));
        this.cubeAll("red_grylynen_crystal_block", bioResLoc("red_grylynen_crystal_block"));

        this.orientableWithBottom("reinforcing_table", bioResLoc("reinforcing_table_side"), bioResLoc("reinforcing_table_side"), bioResLoc("reinforcing_table_bottom"), bioResLoc("reinforcing_table_top"));

        this.carpet("fleignarite_remains", bioResLoc("fleignarite_remains"));
        this.cross("fleignarite_vines", bioResLoc("fleignarite_vines"));
        this.cross("fleignarite_vines_plant", bioResLoc("fleignarite_vines_plant"));

        // Nether Plants
        this.smallMushroom("soul_minishroom", bioResLoc("soul_minishroom"));

        this.bigMushroom("soul_bigshroom", bioResLoc("soul_bigshroom"));

        this.cross("lava_spire", bioResLoc("lava_spire"));
        this.cross("warped_dancer", bioResLoc("warped_dancer"));

        this.cross("soul_sprouts", bioResLoc("soul_sprouts"));
        this.doubleCropPlant("soul_tall_grass", bioResLoc("soul_tall_grass_bottom"), bioResLoc("soul_tall_grass_top"));

        this.cross("spirit_dangler", bioResLoc("spirit_dangler"));
        this.cross("spirit_dangler_plant", bioResLoc("spirit_dangler_plant"));

        this.cross("basalt_speleotherm", bioResLoc("basalt_speleotherm"));
        this.cross("basalt_speleotherm_plant", bioResLoc("basalt_speleotherm_plant"));
        this.cross("fiery_basalt_speleotherm", bioResLoc("fiery_basalt_speleotherm"));

        this.cross("soul_etern", bioResLoc("soul_etern"));
        this.cross("soul_etern_plant", bioResLoc("soul_etern_plant"));
        this.cross("flourished_soul_etern", bioResLoc("flourished_soul_etern"));

        // End Plants
        this.cubeAll("cyra", bioResLoc("cyra"));

        this.cross("azurlia", bioResLoc("azurlia"));

        // Alphanum stone set
        this.cubeAll("alphanum", bioResLoc("alphanum"));
        this.simpleStoneSet("alphanum", "alphanum_stairs", "alphanum_wall", "alphanum_slab");

        this.cubeAll("alphanum_bricks", bioResLoc("alphanum_bricks"));
        this.simpleStoneSet("alphanum_bricks", "alphanum_brick_stairs", "alphanum_brick_wall", "alphanum_brick_slab");

        this.cubeAll("polished_alphanum", bioResLoc("polished_alphanum"));
        this.simpleStoneSet("polished_alphanum", "polished_alphanum_stairs", "polished_alphanum_wall", "polished_alphanum_slab");

        this.cubeColumnHorizontal("alphanum_pillar", bioResLoc("alphanum_pillar"), bioResLoc("alphanum_pillar_top"));
        this.cubeColumnHorizontal("alphanum_nucleus", bioResLoc("alphanum_nucleus"), bioResLoc("alphanum_pillar_top"));

        this.simpleWoodSet("caerulwood");
    }

    public void bigMushroom(String name, ResourceLocation all) {
        singleTexture(name, bioResLoc("big_mushroom"), "0", all).texture("particle", all);
    }

    public void smallMushroom(String name, ResourceLocation all) {
        singleTexture(name + "_one", bioResLoc("small_mushroom_one"), "0", all).texture("particle", all);
        singleTexture(name + "_two", bioResLoc("small_mushroom_two"), "0", all).texture("particle", all);
        singleTexture(name + "_three", bioResLoc("small_mushroom_three"), "0", all).texture("particle", all);
    }

    private ResourceLocation bioResLoc(String texture) {
        return new ResourceLocation(Bioplethora.MOD_ID, BLOCK_FOLDER + "/" + texture);
    }

    private ResourceLocation mcResLoc(String texture) {
        return new ResourceLocation("minecraft", BLOCK_FOLDER + "/" + texture);
    }
    
    public void simpleWoodSet(String woodType) {
        this.cubeColumnHorizontal(woodType + "_log", bioResLoc(woodType + "_log_side"), bioResLoc(woodType + "_log_top"));
        this.cubeAll(woodType + "_wood", bioResLoc(woodType + "_log_side"));
        this.cubeColumnHorizontal("stripped_" + woodType + "_log", bioResLoc("stripped_" + woodType + "_log_side"), bioResLoc("stripped_" + woodType + "_log_top"));
        this.cubeAll("stripped_" + woodType + "_wood", bioResLoc("stripped_" + woodType + "_log_side"));
        this.cubeAll(woodType + "_planks", bioResLoc(woodType + "_planks"));
        this.cubeAll(woodType + "_leaves", bioResLoc(woodType + "_leaves"));
        this.cross(woodType + "_sapling", bioResLoc(woodType + "_sapling"));

        this.fencePost(woodType + "_fence_post", bioResLoc(woodType + "_planks"));
        this.fenceInventory(woodType + "_fence_inventory", bioResLoc(woodType + "_planks"));
        this.fenceSide(woodType + "_fence_side", bioResLoc(woodType + "_planks"));

        this.fenceGateWall(woodType + "_fence_gate_wall", bioResLoc(woodType + "_planks"));
        this.fenceGateWallOpen(woodType + "_fence_gate_wall_open", bioResLoc(woodType + "_planks"));
        this.fenceGate(woodType + "_fence_gate", bioResLoc(woodType + "_planks"));
        this.fenceGateOpen(woodType + "_fence_gate_open", bioResLoc(woodType + "_planks"));

        this.slab(woodType + "_slab", bioResLoc(woodType + "_planks"), bioResLoc(woodType + "_planks"), bioResLoc(woodType + "_planks"));
        this.slabTop(woodType + "_slab_top", bioResLoc(woodType + "_planks"), bioResLoc(woodType + "_planks"), bioResLoc(woodType + "_planks"));

        this.pressurePlateUp(woodType + "_pressure_plate", bioResLoc(woodType + "_planks"));
        this.pressurePlateDown(woodType + "_pressure_plate_down", bioResLoc(woodType + "_planks"));

        this.stairs(woodType + "_stairs", bioResLoc(woodType + "_planks"), bioResLoc(woodType + "_planks"), bioResLoc(woodType + "_planks"));
        this.stairsInner(woodType + "_stairs_inner", bioResLoc(woodType + "_planks"), bioResLoc(woodType + "_planks"), bioResLoc(woodType + "_planks"));
        this.stairsOuter(woodType + "_stairs_outer", bioResLoc(woodType + "_planks"), bioResLoc(woodType + "_planks"), bioResLoc(woodType + "_planks"));

        this.button(woodType + "_button", bioResLoc(woodType + "_planks"));
        this.buttonPressed(woodType + "_button_pressed", bioResLoc(woodType + "_planks"));
        this.buttonInventory(woodType + "_button_inventory", bioResLoc(woodType + "_planks"));

        this.doorBottomLeft(woodType + "_door_bottom", bioResLoc(woodType + "_door_lower"), bioResLoc(woodType + "_door_upper"));
        this.doorBottomRight(woodType + "_door_bottom_hinge", bioResLoc(woodType + "_door_lower"), bioResLoc(woodType + "_door_upper"));
        this.doorTopLeft(woodType + "_door_top", bioResLoc(woodType + "_door_lower"), bioResLoc(woodType + "_door_upper"));
        this.doorTopRight(woodType + "_door_top_hinge", bioResLoc(woodType + "_door_lower"), bioResLoc(woodType + "_door_upper"));

        this.trapdoorBottom(woodType + "_trapdoor_bottom", bioResLoc(woodType + "_trapdoor"));
        this.trapdoorOpen(woodType + "_trapdoor_open", bioResLoc(woodType + "_trapdoor"));
        this.trapdoorTop(woodType + "_trapdoor_top", bioResLoc(woodType + "_trapdoor"));

        this.sign(woodType + "_sign", bioResLoc(woodType + "_planks"));
    }

    public void simpleStoneSet(String mainBlock, String stairs, String wall, String slab) {
        this.stairs(stairs, bioResLoc(mainBlock), bioResLoc(mainBlock), bioResLoc(mainBlock));
        this.stairsInner(stairs, bioResLoc(mainBlock), bioResLoc(mainBlock), bioResLoc(mainBlock));
        this.stairsOuter(stairs, bioResLoc(mainBlock), bioResLoc(mainBlock), bioResLoc(mainBlock));
        this.wallPost(wall, bioResLoc(mainBlock));
        this.wallSide(wall, bioResLoc(mainBlock));
        this.wallSideTall(wall, bioResLoc(mainBlock));
        this.wallInventory(wall, bioResLoc(mainBlock));
        this.slab(slab, bioResLoc(mainBlock), bioResLoc(mainBlock), bioResLoc(mainBlock));
        this.slabTop(slab, bioResLoc(mainBlock), bioResLoc(mainBlock), bioResLoc(mainBlock));
    }

    public void doubleCrossPlant(String name, ResourceLocation bottom, ResourceLocation top) {
        cross(name + "_lower", bottom);
        cross(name + "_upper", top);
    }

    public void doubleCropPlant(String name, ResourceLocation bottom, ResourceLocation top) {
        crop(name + "_lower", bottom);
        crop(name + "_upper", top);
    }

    public void pressurePlateUp(String name, ResourceLocation all) {
        singleTexture(name, mcResLoc("pressure_plate_up"), all);
    }

    public void pressurePlateDown(String name, ResourceLocation all) {
        singleTexture(name, mcResLoc("pressure_plate_down"), all);
    }

    public void button(String name, ResourceLocation all) {
        singleTexture(name, mcResLoc("button"), all);
    }

    public void buttonPressed(String name, ResourceLocation all) {
        singleTexture(name + "_pressed", mcResLoc("button_pressed"), all);
    }

    public void buttonInventory(String name, ResourceLocation all) {
        singleTexture(name + "_inventory", mcResLoc("button_inventory"), all);
    }

    public void sign(String name, ResourceLocation particle) {
        getBuilder(name).texture("particle", particle);
    }
}
