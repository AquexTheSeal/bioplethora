package io.github.bioplethora.generators;

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

        // Petrawood woodset
        this.cubeColumnHorizontal("petrawood_log", bioResLoc("petrawood_log_side"), bioResLoc("petrawood_log_top"));
        this.cubeAll("petrawood_wood", bioResLoc("petrawood_log_side"));
        this.cubeAll("petrawood_planks", bioResLoc("petrawood_planks"));
        this.cubeAll("petrawood_leaves", bioResLoc("petrawood_leaves"));

        this.fencePost("petrawood_fence", bioResLoc("petrawood_planks"));
        this.fenceInventory("petrawood_fence", bioResLoc("petrawood_planks"));
        this.fenceSide("petrawood_fence", bioResLoc("petrawood_planks"));
        this.fenceGateWall("petrawood_fence_gate", bioResLoc("petrawood_planks"));
        this.fenceGateWallOpen("petrawood_fence_gate", bioResLoc("petrawood_planks"));
        this.fenceGate("petrawood_fence_gate", bioResLoc("petrawood_planks"));
        this.fenceGateOpen("petrawood_fence_gate", bioResLoc("petrawood_planks"));
        this.slab("petrawood_slab", bioResLoc("petrawood_planks"), bioResLoc("petrawood_planks"), bioResLoc("petrawood_planks"));
        this.slabTop("petrawood_slab", bioResLoc("petrawood_planks"), bioResLoc("petrawood_planks"), bioResLoc("petrawood_planks"));
        this.pressurePlateUp("petrawood_pressure_plate", bioResLoc("petrawood_planks"));
        this.pressurePlateDown("petrawood_pressure_plate", bioResLoc("petrawood_planks"));
        this.stairs("petrawood_stairs", bioResLoc("petrawood_planks"), bioResLoc("petrawood_planks"), bioResLoc("petrawood_planks"));
        this.stairsInner("petrawood_stairs", bioResLoc("petrawood_planks"), bioResLoc("petrawood_planks"), bioResLoc("petrawood_planks"));
        this.stairsOuter("petrawood_stairs", bioResLoc("petrawood_planks"), bioResLoc("petrawood_planks"), bioResLoc("petrawood_planks"));
        this.button("petrawood_button", bioResLoc("petrawood_planks"));
        this.buttonPressed("petrawood_button", bioResLoc("petrawood_planks"));
        this.buttonInventory("petrawood_button", bioResLoc("petrawood_planks"));
    }

    private ResourceLocation bioResLoc(String texture) {
        return new ResourceLocation(Bioplethora.MOD_ID, BLOCK_FOLDER + "/" + texture);
    }

    private ResourceLocation mcResLoc(String texture) {
        return new ResourceLocation("minecraft", BLOCK_FOLDER + "/" + texture);
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
}
