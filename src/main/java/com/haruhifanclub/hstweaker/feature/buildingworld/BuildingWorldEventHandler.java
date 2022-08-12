package com.haruhifanclub.hstweaker.feature.buildingworld;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraftforge.event.entity.player.PlayerEvent;

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

}
