package com.haruhifanclub.hstweaker.feature.prism;

import com.haruhifanclub.hstweaker.server.event.impl.MsgCommandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PrismEventDispatcher {

    static {
        com.haruhifanclub.hstweaker.HSTweaker.LOGGER.info(Prism.MARKER, "Register PRISM event dispatcher");
    }

    @SubscribeEvent
    public static void onMsgCommand(final MsgCommandEvent event) {
        TellMonitor.onMsgCommand(event);
    }

}
