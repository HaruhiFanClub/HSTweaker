package com.haruhifanclub.hstweaker.server.event;

import com.haruhifanclub.hstweaker.feature.anti.AntiDuplication;
import com.haruhifanclub.hstweaker.feature.anti.ceiling.NoInteractingAboveCeiling;
import com.haruhifanclub.hstweaker.feature.anti.ceiling.NoThroughCeiling;
import com.haruhifanclub.hstweaker.feature.itemban.ItemBanEventHandler;
import com.haruhifanclub.hstweaker.feature.itemshare.ItemShareEventHandler;
import com.haruhifanclub.hstweaker.feature.world.HSTWorldEventDispatcher;
import net.minecraftforge.eventbus.api.IEventBus;

public final class HSTEventHandlers {

    public static void register(final IEventBus forgeEventBus) {
        var a = new Class[] {
            HSTUniversalEventHandler.class,
            HSTWorldEventDispatcher.class,
            AntiDuplication.class,
            NoInteractingAboveCeiling.class,
            NoThroughCeiling.class,
            ItemBanEventHandler.class,
            ItemShareEventHandler.class,
        };
        for (var c : a) forgeEventBus.register(c);
    }

}
