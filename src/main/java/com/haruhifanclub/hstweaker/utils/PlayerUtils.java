package com.haruhifanclub.hstweaker.utils;

import net.minecraft.server.level.ServerPlayer;

public interface PlayerUtils extends org.auioc.mcmod.arnicalib.utils.game.PlayerUtils {

    static String getName(ServerPlayer player) {
        return player.getGameProfile().getName();
    }

}
