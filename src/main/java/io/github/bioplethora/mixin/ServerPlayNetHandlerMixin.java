/**
 * MIT License
 *
 * Copyright (c) 2021 Meldexun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.bioplethora.mixin;

import io.github.bioplethora.entity.creatures.MyliothanEntity;
import io.github.bioplethora.entity.others.part.BPPartEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayNetHandler.class)
public class ServerPlayNetHandlerMixin {

    @Shadow public ServerPlayerEntity player;
    @Unique
    private double aabbRadius;

    @Redirect(method = "handleInteract", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/ServerPlayerEntity;distanceToSqr(Lnet/minecraft/entity/Entity;)D"))
    public double hitboxFix(ServerPlayerEntity player, Entity target) {

        // Fix Multipart Hitbox for Myliothan

        if (target instanceof MyliothanEntity || (target instanceof BPPartEntity && ((BPPartEntity) target).getParent() instanceof MyliothanEntity)) {
            AxisAlignedBB aabb = target.getBoundingBox();
            float collisionBorderSize = target.getPickRadius();
            if (collisionBorderSize != 0.0F) {
                aabb = aabb.inflate(collisionBorderSize);
            }
            double x = (aabb.maxX - aabb.minX) * 0.5D;
            double y = (aabb.maxY - aabb.minY) * 0.5D;
            double z = (aabb.maxZ - aabb.minZ) * 0.5D;
            this.aabbRadius = Math.sqrt(x * x + y * y + z * z);
            double x1 = aabb.minX + x - player.getX();
            double y1 = aabb.minY + y - (player.getY() + player.getEyeHeight());
            double z1 = aabb.minZ + z - player.getZ();
            return Math.sqrt(x1 * x1 + y1 * y1 + z1 * z1);
        } else {
            return player.distanceToSqr(target);
        }
    }
}
