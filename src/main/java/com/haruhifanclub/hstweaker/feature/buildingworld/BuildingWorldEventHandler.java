package com.haruhifanclub.hstweaker.feature.buildingworld;

import org.auioc.mcmod.arnicalib.utils.game.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.ExplosionEvent;

public class BuildingWorldEventHandler {

    public static void onEntityJoinWorld(final EntityJoinWorldEvent event) {
        if (BuildingWorld.isThis(event.getWorld())) {
            var entity = event.getEntity();
            if (entity instanceof ItemEntity) event.setCanceled(true);
            else if (entity instanceof ServerPlayer player) BuildingWorld.onEnter(player);
        }
    }

    public static void onEntityLeaveWorld(final EntityLeaveWorldEvent event) {
        if (BuildingWorld.isThis(event.getWorld())) {
            var entity = event.getEntity();
            if (entity instanceof ServerPlayer player) BuildingWorld.onExit(player);
        }
    }

    public static void onExplosionStart(final ExplosionEvent.Start event) {
        if (BuildingWorld.isThis(event.getWorld())) {
            event.setCanceled(true);
            if (event.getExplosion().getSourceMob() instanceof ServerPlayer player) {
                player.sendMessage(TextUtils.translatable(BuildingWorld.i18n("no_explosion")).withStyle(ChatFormatting.RED), ChatType.GAME_INFO, Util.NIL_UUID);
            }
        }
    }

    public static void onRightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
        if (BuildingWorld.isThis(event.getWorld())) {
            var hit = event.getHitVec();
            if (hit.getType() == HitResult.Type.BLOCK) {
                var block = event.getWorld().getBlockState(hit.getBlockPos());
                if (block.is(Blocks.ENDER_CHEST)) {
                    event.setCanceled(true);
                    ((ServerPlayer) event.getPlayer()).sendMessage(TextUtils.translatable(BuildingWorld.i18n("block_ender_chest")).withStyle(ChatFormatting.RED), ChatType.GAME_INFO, Util.NIL_UUID);
                }
            }
        }
    }

}
