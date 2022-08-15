package com.haruhifanclub.hstweaker.server.event;

import com.haruhifanclub.hstweaker.feature.antiduplication.AntiDuplicationEventHandler;
import com.haruhifanclub.hstweaker.feature.itemban.ItemBanEventHandler;
import com.haruhifanclub.hstweaker.feature.world.HSTWorldEventDispatcher;
import net.minecraftforge.eventbus.api.IEventBus;

public final class HSTEventHandlers {

    public static void register(final IEventBus forgeEventBus) {
        var a = new Class[] {
            HSTUniversalEventHandler.class,
            HSTWorldEventDispatcher.class,
            AntiDuplicationEventHandler.class,
            ItemBanEventHandler.class
        };
        for (var c : a) forgeEventBus.register(c);
    }

}
