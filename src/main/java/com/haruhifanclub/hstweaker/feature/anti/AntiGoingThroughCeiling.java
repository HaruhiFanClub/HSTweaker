package com.haruhifanclub.hstweaker.feature.anti;

import static com.haruhifanclub.hstweaker.HSTweaker.LOGGER;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AntiGoingThroughCeiling {

    private static boolean isNearCeiling(ServerLevel level, double y, double yOffset) {
        return level.dimensionType().hasCeiling() && y >= ((level.getMinBuildHeight() + level.getLogicalHeight()) - 1 + yOffset);
    }

    private static void warn(ServerPlayer player) {
        player.sendMessage(Anti.MSGH.create("go_through_bedrock_ceiling", false), ChatType.GAME_INFO, Util.NIL_UUID);
        LOGGER.warn(Anti.MARKER, "Player {}({}) attempts to go through bedrock ceiling at {}", player.getGameProfile().getName(), player.getStringUUID(), player.position().toString());
    }

    @SubscribeEvent
    public static void onEnderPearlLand(final EntityTeleportEvent.EnderPearl event) {
        if (isNearCeiling((ServerLevel) event.getPearlEntity().getLevel(), event.getPearlEntity().getY(), -1.75D)) {
            warn(event.getPlayer());
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEntityMount(final EntityMountEvent event) {
        if (
            event.isMounting()
                && event.getEntityMounting() instanceof ServerPlayer player
                && event.getEntityBeingMounted() instanceof Boat boat
        ) {
            if (isNearCeiling((ServerLevel) event.getWorldObj(), boat.getY(), -1.75D)) {
                warn(player);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockPlace(final BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            if (isNearCeiling(player.getLevel(), event.getPos().getY(), 0.0D)) {
                player.sendMessage(Anti.MSGH.create("place_block_above_bedrock_ceiling", false), ChatType.GAME_INFO, Util.NIL_UUID);
                LOGGER.warn(Anti.MARKER, "Player {}({}) attempts to place block above bedrock ceiling at {}", player.getGameProfile().getName(), player.getStringUUID(), event.getPos().toString());
                event.setCanceled(true);
            }
        }
    }

}
