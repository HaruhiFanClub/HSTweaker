package com.haruhifanclub.hstweaker.server.cooldown;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HSTCooldowns {

    private static final Map<String, Pair<Integer, Integer>> cooldowns = new HashMap<>();
    private static int tickCount = 0;

    public static void add(String id, int ticks) {
        cooldowns.put(id, Pair.of(ticks, tickCount + ticks));
    }

    public static void remove(String id) {
        cooldowns.remove(id);
    }

    public static boolean isOnCooldown(String id) {
        return (cooldowns.get(id) == null) ? true : false;
    }

    public static int getRemainingTicks(String id) {
        var c = cooldowns.get(id);
        if (c == null) return 0;
        return c.getRight() - tickCount;
    }

    @SubscribeEvent
    public static void onServerTick(final TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ++tickCount;
            if (cooldowns.isEmpty()) return;
            Iterator<Entry<String, Pair<Integer, Integer>>> iterator = cooldowns.entrySet().iterator();
            while (iterator.hasNext()) if ((iterator.next().getValue()).getRight() <= tickCount) iterator.remove();
        }
    }

}
