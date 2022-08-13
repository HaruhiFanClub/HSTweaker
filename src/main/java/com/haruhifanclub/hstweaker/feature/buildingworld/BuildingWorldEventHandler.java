package com.haruhifanclub.hstweaker.feature.buildingworld;

import org.auioc.mcmod.arnicalib.utils.game.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
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

}
