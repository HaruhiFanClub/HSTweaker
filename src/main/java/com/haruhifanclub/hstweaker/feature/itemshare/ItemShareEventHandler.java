package com.haruhifanclub.hstweaker.feature.itemshare;

import org.auioc.mcmod.arnicalib.utils.game.TextUtils;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ItemShareEventHandler {

    private static final String PLACEHOLDER = "%i";

    @SubscribeEvent
    public static void onServerChat(final ServerChatEvent event) {
        var message = event.getMessage();
        if (event.getMessage().contains(PLACEHOLDER)) {
            var args = ((TranslatableComponent) event.getComponent()).getArgs();
            var itemName = event.getPlayer().getMainHandItem().getDisplayName();
            if (message.equals(PLACEHOLDER)) {
                args[1] = itemName;
            } else {
                var p = message.split(PLACEHOLDER, 2);
                args[1] = TextUtils.empty().append(TextUtils.literal(p[0])).append(itemName).append(TextUtils.literal(p[1]));
            }
        }
    }

}

