package com.haruhifanclub.hstweaker.feature.buildingworld;

import org.auioc.mcmod.arnicalib.utils.game.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.ExplosionEvent;

public class BuildingWorldEventHandler {

    public static void onPlayerChangedDimension(final PlayerEvent.PlayerChangedDimensionEvent event) {
        var player = (ServerPlayer) event.getPlayer();
        if (player.createCommandSourceStack().hasPermission(3)) return;
        if (BuildingWorld.isThis(event.getTo())) {
            player.setGameMode(GameType.CREATIVE);
        } else if (BuildingWorld.isThis(event.getFrom())) {
            player.getInventory().clearContent();
            player.setExperienceLevels(0);
            player.setExperiencePoints(0);
            player.removeAllEffects();
            player.setGameMode(GameType.SURVIVAL);
        }
    }

    public static void onEntityJoinWorld(final EntityJoinWorldEvent event) {}

    public static void onExplosionStart(final ExplosionEvent.Start event) {
        if (BuildingWorld.isThis(event.getWorld())) {
            event.setCanceled(true);
            if (event.getExplosion().getSourceMob() instanceof ServerPlayer player) {
                player.sendMessage(TextUtils.translatable(BuildingWorld.i18n("no_explosion")).withStyle(ChatFormatting.RED), ChatType.GAME_INFO, Util.NIL_UUID);
            }
        }
    }

}
