package io.github.bioplethora.item.functionals;

import io.github.bioplethora.entity.BPBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;

public class BPBoatItem extends BoatItem {

    private static final Predicate<Entity> ENTITY_PREDICATE = EntityPredicates.NO_SPECTATORS.and(Entity::canBeCollidedWith);
    private final String woodType;

    public BPBoatItem(Properties properties, String woodType) {
        super(null, properties);
        this.woodType = woodType;
    }

    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        RayTraceResult raytraceresult = getPlayerPOVHitResult(pLevel, pPlayer, RayTraceContext.FluidMode.ANY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return ActionResult.pass(itemstack);
        } else {
            Vector3d vector3d = pPlayer.getViewVector(1.0F);
            double d0 = 5.0D;
            List<Entity> list = pLevel.getEntities(pPlayer, pPlayer.getBoundingBox().expandTowards(vector3d.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vector3d vector3d1 = pPlayer.getEyePosition(1.0F);

                for(Entity entity : list) {
                    AxisAlignedBB axisalignedbb = entity.getBoundingBox().inflate((double)entity.getPickRadius());
                    if (axisalignedbb.contains(vector3d1)) {
                        return ActionResult.pass(itemstack);
                    }
                }
            }

            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
                BPBoatEntity boatentity = new BPBoatEntity(pLevel, raytraceresult.getLocation().x, raytraceresult.getLocation().y, raytraceresult.getLocation().z);
                boatentity.setWoodType(woodType);
                boatentity.yRot = pPlayer.yRot;
                if (!pLevel.noCollision(boatentity, boatentity.getBoundingBox().inflate(-0.1D))) {
                    return ActionResult.fail(itemstack);
                } else {
                    if (!pLevel.isClientSide) {
                        pLevel.addFreshEntity(boatentity);
                        if (!pPlayer.abilities.instabuild) {
                            itemstack.shrink(1);
                        }
                    }

                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                    return ActionResult.sidedSuccess(itemstack, pLevel.isClientSide());
                }
            } else {
                return ActionResult.pass(itemstack);
            }
        }
    }
}
