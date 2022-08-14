package com.haruhifanclub.hstweaker.feature.world.building;

import com.haruhifanclub.hstweaker.feature.world.HSTWorlds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public final class BuildingWorld {

    public static boolean onJoin(Entity entity, ServerLevel level) {
        if (entity instanceof ItemEntity) return false;
        else if (entity instanceof ServerPlayer player) {
            level.setBlockAndUpdate(HSTWorlds.BUILDING.entryPoint.below(), Blocks.BEDROCK.defaultBlockState());
            if (!player.createCommandSourceStack().hasPermission(3)) {
                player.setGameMode(GameType.CREATIVE);
            }
        }
        return true;
    }

    public static boolean onLeave(Entity entity, ServerLevel level) {
        if (entity instanceof ServerPlayer player) {
            if (!player.createCommandSourceStack().hasPermission(3)) {
                player.getInventory().clearContent();
                player.setExperienceLevels(0);
                player.setExperiencePoints(0);
                player.removeAllEffects();
                player.setGameMode(GameType.SURVIVAL);
            }
        }
        return true;
    }

    public static boolean onExplosionStart(Explosion explosion, ServerLevel level) {
        if (explosion.getSourceMob() instanceof ServerPlayer player) HSTWorlds.BUILDING.warn(player, "no_explosion");
        return false;
    }

    public static boolean onRightClickBlock(ServerPlayer player, BlockHitResult hit, ServerLevel level) {
        if (hit.getType() == HitResult.Type.BLOCK) {
            var block = level.getBlockState(hit.getBlockPos());
            if (block.is(Blocks.ENDER_CHEST)) {
                HSTWorlds.BUILDING.warn(player, "block_ender_chest");
                return false;
            }
        }
        return true;
    }
}
