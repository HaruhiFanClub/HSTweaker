package com.haruhifanclub.hstweaker.feature.anti.ceiling;

import static com.haruhifanclub.hstweaker.HSTweaker.LOGGER;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import org.auioc.mcmod.arnicalib.utils.game.MessageHelper;
import com.haruhifanclub.hstweaker.HSTweaker;
import com.haruhifanclub.hstweaker.feature.anti.Anti;
import net.minecraft.Util;
import net.minecraft.network.chat.ChatType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class Ceiling {

    protected static final Marker MARKER = LogUtil.getMarker(Ceiling.class).addParents(Anti.MARKER);

    protected static final MessageHelper MSGH = new MessageHelper(HSTweaker.MESSAGE_PREFIX, (key) -> HSTweaker.i18n("anti.ceiling." + key));

    protected static boolean isNearCeiling(ServerLevel level, double y, double yOffset) {
        return level.dimensionType().hasCeiling() && y >= ((level.getMinBuildHeight() + level.getLogicalHeight()) - 1 + yOffset);
    }

    protected static void warn(ServerPlayer player, String key, String log, String pos) {
        player.sendMessage(MSGH.create(key, false), ChatType.GAME_INFO, Util.NIL_UUID);
        LOGGER.warn(Ceiling.MARKER, "Player {}({}) attempts to {} at {},{}", player.getGameProfile().getName(), player.getStringUUID(), log, player.getLevel().dimension().location().toString(), pos);
    }

}
