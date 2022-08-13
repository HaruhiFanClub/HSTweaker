package com.haruhifanclub.hstweaker.server.event;

import org.auioc.mcmod.arnicalib.common.event.impl.PistonCheckPushableEvent;
import com.haruhifanclub.hstweaker.feature.antiduplication.AntiDuplicationEventHandler;
import com.haruhifanclub.hstweaker.feature.buildingworld.BuildingWorldEventHandler;
import com.haruhifanclub.hstweaker.server.command.HSTCommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@SuppressWarnings("resource")
public final class HSTEventHandler {

    @SubscribeEvent
    public static void registerCommands(final RegisterCommandsEvent event) {
        HSTCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(final EntityJoinWorldEvent event) {
        if (event.getWorld().isClientSide) return;
        BuildingWorldEventHandler.onEntityJoinWorld(event);
    }

    @SubscribeEvent
    public static void onEntityLeaveWorld(final EntityLeaveWorldEvent event) {
        if (event.getWorld().isClientSide) return;
        BuildingWorldEventHandler.onEntityLeaveWorld(event);
    }

    @SubscribeEvent
    public static void onExplosionStart(final ExplosionEvent.Start event) {
        if (event.getWorld().isClientSide) return;
        BuildingWorldEventHandler.onExplosionStart(event);
    }

    @SubscribeEvent
    public static void onRightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
        if (event.getWorld().isClientSide) return;
        BuildingWorldEventHandler.onRightClickBlock(event);
    }


    @SubscribeEvent
    public static void onPistonCheckPushable(final PistonCheckPushableEvent event) {
        if (event.getWorld().isClientSide()) return;
        AntiDuplicationEventHandler.onPistonCheckPushable(event);
    }

}
