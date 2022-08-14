package com.haruhifanclub.hstweaker.server.event;

import org.auioc.mcmod.arnicalib.common.event.impl.PistonCheckPushableEvent;
import com.haruhifanclub.hstweaker.feature.antiduplication.AntiDuplicationEventHandler;
import com.haruhifanclub.hstweaker.server.command.HSTCommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class HSTEventHandler {

    @SubscribeEvent
    public static void registerCommands(final RegisterCommandsEvent event) {
        HSTCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPistonCheckPushable(final PistonCheckPushableEvent event) {
        if (event.getWorld().isClientSide()) return;
        AntiDuplicationEventHandler.onPistonCheckPushable(event);
    }

}
