package com.haruhifanclub.hstweaker.server.event;

import com.haruhifanclub.hstweaker.feature.buildingworld.BuildingWorldEventHandler;
import com.haruhifanclub.hstweaker.server.command.HSTCommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class HSTEventHandler {

    @SubscribeEvent
    public static void registerCommands(final RegisterCommandsEvent event) {
        HSTCommands.register(event.getDispatcher());
    }


    @SubscribeEvent
    public static void onPlayerChangedDimension(final PlayerEvent.PlayerChangedDimensionEvent event) {
        BuildingWorldEventHandler.onPlayerChangedDimension(event);
    }

}
