package com.haruhifanclub.hstweaker.feature.anti;

import org.auioc.mcmod.arnicalib.utils.game.PlayerUtils;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.EntityTeleportEvent.EnderPearl;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AntiGoingThroughBedrock {

    private static void warn(ServerPlayer player) {
        player.sendMessage(Anti.MSGH.create("going_through_bedrock", false), ChatType.GAME_INFO, Util.NIL_UUID);
    }

    @SubscribeEvent
    public static void onEnderPearlLand(final EnderPearl event) {
        var player = event.getPlayer();
        if (PlayerUtils.isOp(player)) return;
        var level = (ServerLevel) event.getPearlEntity().getLevel();
        double y = event.getPearlEntity().getY();
        if (level.dimensionType().hasCeiling() && y > (level.getMinBuildHeight() + level.getLogicalHeight() - 2)) {
            warn(player);
            event.setCanceled(true);
        }
    }

}
