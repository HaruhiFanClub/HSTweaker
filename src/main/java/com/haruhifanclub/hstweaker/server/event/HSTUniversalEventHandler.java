package com.haruhifanclub.hstweaker.server.event;

import org.auioc.mcmod.arnicalib.utils.game.PlayerUtils;
import org.auioc.mcmod.arnicalib.utils.game.TextUtils;
import com.haruhifanclub.hstweaker.HSTweaker;
import com.haruhifanclub.hstweaker.server.command.HSTCommands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class HSTUniversalEventHandler {

    @SubscribeEvent
    public static void registerCommands(final RegisterCommandsEvent event) {
        HSTCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayer player && PlayerUtils.isOp(player)) {
            HSTweaker.MSGH.sendSystemMessage(player, TextUtils.literal(HSTweaker.FULL_VERSION));
        }
    }

}
