package com.haruhifanclub.mods.hstweaker;

import com.haruhifanclub.mods.hstweaker.server.event.ServerEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;

@Mod(HSTweaker.MOD_ID)
public class HSTweaker {

    public static final String MOD_ID = "hstweaker";

    public HSTweaker() {
        final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        forgeSetup(forgeEventBus);
    }


    private void forgeSetup(final IEventBus forgeEventBus) {
        forgeEventBus.register(ServerEventHandler.class);
    }

}

