package com.haruhifanclub.hstweaker.feature.world.impl;

import static com.haruhifanclub.hstweaker.HSTweaker.LOGGER;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import org.auioc.mcmod.arnicalib.utils.game.EntityUtils;
import org.auioc.mcmod.arnicalib.utils.game.PlayerUtils;
import org.auioc.mcmod.arnicalib.utils.game.RandomTeleporter;
import com.haruhifanclub.hstweaker.api.world.AbstractHSTWorld;
import com.haruhifanclub.hstweaker.feature.world.HSTWorlds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.Level;

public class Overworld extends AbstractHSTWorld {

    public static final Marker MARKER = LogUtil.getMarker(Overworld.class).addParents(HSTWorlds.MARKER);

    public Overworld() {
        super(Level.OVERWORLD);
    }

    @Override
    public void onPlayerLoggedIn(ServerPlayer player, ServerLevel level) {
        var totalWorldTime = player.getStats().getValue(Stats.CUSTOM.get(Stats.PLAY_TIME));
        if (totalWorldTime < 20) {
            LOGGER.info(MARKER, "{} first logged in", player.getGameProfile().getName());
            EntityUtils.teleportTo(player, HSTWorlds.LOBBY.key, HSTWorlds.LOBBY.entryPoint);
            this.msgh.sendChatMessage(player, "first_login");
        }
    }

    @Override
    public void onPlayerJoin(ServerPlayer player, ServerLevel level) {}

    @Override
    public void onPlayerLeave(ServerPlayer player, ServerLevel level) {}

    @Override
    public void onPlayerRespawn(ServerPlayer player, ServerLevel level) {
        randomRespawn(player);
    }

    @Override
    public void onPlayerTravelledTo(ServerPlayer player, ServerLevel from) {
        if (HSTWorlds.LOBBY.is(from)) randomRespawn(player);
    }

    private void randomRespawn(ServerPlayer player) {
        if (!PlayerUtils.isOp(player) && (player.getRespawnPosition() == null || !player.getRespawnDimension().equals(Level.OVERWORLD))) {
            LOGGER.info(MARKER, "Random respawn player {}", player.getGameProfile().getName());
            RandomTeleporter.teleport(player, player.blockPosition(), 256, true, 16);
            this.msgh.sendGameInfo(player, "random_respawn");
        }
    }

}
