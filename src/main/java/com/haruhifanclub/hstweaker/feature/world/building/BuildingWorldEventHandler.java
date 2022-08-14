package com.haruhifanclub.hstweaker.feature.world.building;

import org.auioc.mcmod.arnicalib.utils.game.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class BuildingWorldEventHandler {

    private static void warn(ServerPlayer player, String key) {
        player.sendMessage(TextUtils.translatable(BuildingWorld.i18n(key)).withStyle(ChatFormatting.RED), ChatType.GAME_INFO, Util.NIL_UUID);
    }

    public static boolean onEntityJoinWorld(Entity entity, ServerLevel level) {
        if (BuildingWorld.isThis(level)) {
            if (entity instanceof ItemEntity) return false;
            else if (entity instanceof ServerPlayer player) BuildingWorld.onEnter(player);
        }
        return true;
    }

    public static boolean onEntityLeaveWorld(Entity entity, ServerLevel level) {
        if (BuildingWorld.isThis(level)) {
            if (entity instanceof ServerPlayer player) BuildingWorld.onExit(player);
        }
        return true;
    }

    public static boolean onExplosionStart(Explosion explosion, ServerLevel level) {
        if (BuildingWorld.isThis(level)) {
            if (explosion.getSourceMob() instanceof ServerPlayer player) warn(player, "no_explosion");
            return false;
        }
        return true;
    }

    public static boolean onRightClickBlock(ServerPlayer player, BlockHitResult hit, ServerLevel level) {
        if (BuildingWorld.isThis(level)) {
            if (hit.getType() == HitResult.Type.BLOCK) {
                var block = level.getBlockState(hit.getBlockPos());
                if (block.is(Blocks.ENDER_CHEST)) {
                    warn(player, "block_ender_chest");
                    return false;
                }
            }
        }
        return true;
    }

}
