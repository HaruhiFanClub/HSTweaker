package com.haruhifanclub.hstweaker.feature.anti.ceiling;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NoThroughCeiling {

    private static void warn(ServerPlayer player) {
        Ceiling.warn(player, "no_through", "go through bedrock ceiling", player.position().toString());
    }

    @SubscribeEvent
    public static void onEnderPearlLand(final EntityTeleportEvent.EnderPearl event) {
        if (Ceiling.isNearCeiling((ServerLevel) event.getPearlEntity().getLevel(), event.getPearlEntity().getY(), -1.75D)) {
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
            if (Ceiling.isNearCeiling((ServerLevel) event.getWorldObj(), boat.getY(), -1.75D)) {
                warn(player);
                event.setCanceled(true);
            }
        }
    }

}
