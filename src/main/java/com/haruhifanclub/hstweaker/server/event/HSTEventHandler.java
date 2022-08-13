package com.haruhifanclub.hstweaker.server.event;

import org.auioc.mcmod.arnicalib.common.event.impl.PistonCheckPushableEvent;
import com.haruhifanclub.hstweaker.feature.antiduplication.AntiDuplicationEventHandler;
import com.haruhifanclub.hstweaker.feature.buildingworld.BuildingWorldEventHandler;
import com.haruhifanclub.hstweaker.server.command.HSTCommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.ExplosionEvent;
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

    @SubscribeEvent
    public static void onExplosionStart(final ExplosionEvent.Start event) {
        BuildingWorldEventHandler.onExplosionStart(event);
    }

    @SubscribeEvent
    public static void onPistonCheckPushable(final PistonCheckPushableEvent event) {
        AntiDuplicationEventHandler.onPistonCheckPushable(event);
    }

}
