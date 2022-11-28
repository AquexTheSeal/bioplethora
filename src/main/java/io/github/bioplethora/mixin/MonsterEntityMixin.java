package io.github.bioplethora.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

import static io.github.bioplethora.entity.creatures.VoidjawEntity.checkMobSpawnRules;
import static net.minecraft.entity.monster.MonsterEntity.isDarkEnoughToSpawn;

@Mixin(MonsterEntity.class)
public class MonsterEntityMixin {

    @Inject(at = @At("HEAD"), method = ("checkMonsterSpawnRules"), cancellable = true)
    private static void checkMonsterSpawnRules(EntityType<? extends MonsterEntity> pType, IServerWorld pLevel, SpawnReason pSpawnType, BlockPos pPos, Random pRandom, CallbackInfoReturnable<Boolean> cir) {
        if (pType == EntityType.ENDERMAN) {
            if (pLevel.getLevel().dimension() == World.END) {
                cir.setReturnValue(true);
            }
        }
    }
}
