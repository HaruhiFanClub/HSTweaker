package com.haruhifanclub.mods.hstweaker;

import com.haruhifanclub.mods.hstweaker.common.alchemy.PotionRegistry;
import com.haruhifanclub.mods.hstweaker.server.event.ServerEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(HSTweaker.MOD_ID)
public class HSTweaker {

    public static final String MOD_ID = "hstweaker";

    public HSTweaker() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        modSetup(modEventBus);
        forgeSetup(forgeEventBus);
    }

    private void modSetup(final IEventBus modEventBus) {
        PotionRegistry.POTIONS.register(modEventBus);
        modEventBus.register(PotionRegistry.class);
    }


    private void forgeSetup(final IEventBus forgeEventBus) {
        forgeEventBus.register(ServerEventHandler.class);
    }

}

