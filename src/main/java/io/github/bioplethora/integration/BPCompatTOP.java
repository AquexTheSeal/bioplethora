package io.github.bioplethora.integration;

import io.github.bioplethora.Bioplethora;
import io.github.bioplethora.entity.IMobCappedEntity;
import io.github.bioplethora.entity.creatures.DwarfMossadileEntity;
import io.github.bioplethora.entity.creatures.GrylynenEntity;
import io.github.bioplethora.entity.creatures.ShachathEntity;
import io.github.bioplethora.entity.others.PrimordialRingEntity;
import mcjty.theoneprobe.api.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.InterModComms;

import java.util.function.Function;

public class BPCompatTOP {

    private BPCompatTOP() {
    }

    public static void register() {
        InterModComms.sendTo("theoneprobe", "getTheOneProbe", GetTheOneProbe::new);
    }

    public static class GetTheOneProbe implements Function<ITheOneProbe, Void> {
        @Override
        public Void apply(ITheOneProbe iTheOneProbe) {
            iTheOneProbe.registerEntityProvider(new IProbeInfoEntityProvider() {
                @Override
                public String getID() {
                    return Bioplethora.MOD_ID + ":default";
                }
                @Override
                public void addProbeEntityInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, PlayerEntity playerEntity, World world, Entity entity, IProbeHitEntityData iProbeHitEntityData) {

                    String var = "Variant: ";
                    String ownerString = "Owner: ";
                    String mobCap = "Damage Limit: ";

                    if (entity instanceof IMobCappedEntity) {
                        iProbeInfo.text(CompoundText.createLabelInfo(mobCap, ((IMobCappedEntity) entity).getMaxDamageCap()));
                    }

                    if (entity instanceof DwarfMossadileEntity) {
                        boolean netherVariant = ((DwarfMossadileEntity) entity).isNetherVariant();
                        if (netherVariant) {
                            iProbeInfo.text(CompoundText.createLabelInfo(var, "Nether"));
                        } else {
                            iProbeInfo.text(CompoundText.createLabelInfo(var, "Overworld"));
                        }
                    }

                    if (entity instanceof ShachathEntity) {
                        boolean isClone = ((ShachathEntity) entity).isClone();
                        if (isClone) {
                            iProbeInfo.text(CompoundText.createLabelInfo(var, "Clone"));
                        }
                    }

                    if (entity instanceof PrimordialRingEntity) {
                        boolean hasOwner = ((PrimordialRingEntity) entity).getOwner() != null;
                        if (hasOwner) {
                            iProbeInfo.text(CompoundText.createLabelInfo(ownerString, ((PrimordialRingEntity) entity).getOwner().getDisplayName()));
                        }
                    }

                    if (entity instanceof GrylynenEntity) {
                        iProbeInfo.text(CompoundText.createLabelInfo(var, ((GrylynenEntity) entity).getGrylynenTier().getTierName()));
                    }
                }
            });
            return null;
        }
    }
}
