package com.haruhifanclub.hstweaker.feature.buildingworld;

import org.auioc.mcmod.arnicalib.utils.game.TextUtils;
import com.haruhifanclub.hstweaker.HSTweaker;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.ExplosionEvent;

public class BuildingWorldEventHandler {

    public static void onPlayerChangedDimension(final PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            if (player.createCommandSourceStack().hasPermission(3)) return;
            if (event.getTo().equals(BuildingWorld.LEVEL_KEY)) {
                player.setGameMode(GameType.CREATIVE);
            } else if (event.getFrom().equals(BuildingWorld.LEVEL_KEY)) {
                player.getInventory().clearContent();
                player.setExperienceLevels(0);
                player.setExperiencePoints(0);
                player.removeAllEffects();
                player.setGameMode(GameType.SURVIVAL);
            }
        }
    }

    public static void onExplosionStart(final ExplosionEvent.Start event) {
        if (event.getWorld().dimension().equals(BuildingWorld.LEVEL_KEY)) {
            event.setCanceled(true);
            if (event.getExplosion().getSourceMob() instanceof ServerPlayer player) {
                player.sendMessage(TextUtils.translatable(HSTweaker.i18n("buildingworld.no_explosion")).withStyle(ChatFormatting.RED), ChatType.GAME_INFO, Util.NIL_UUID);
            }
        }
    }

}
