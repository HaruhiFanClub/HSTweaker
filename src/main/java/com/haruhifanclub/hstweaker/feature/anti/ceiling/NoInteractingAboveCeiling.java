package com.haruhifanclub.hstweaker.feature.anti.ceiling;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NoInteractingAboveCeiling {

    @SubscribeEvent
    public static void onBlockPlace(final BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            if (Ceiling.isNearCeiling(player.getLevel(), event.getPos().getY(), 0.0D)) {
                Ceiling.warn(player, "no_placing_block_above", "place block above bedrock ceiling", event.getPos().toString());
                event.setCanceled(true);
            }
        }
    }

}
