package com.haruhifanclub.hstweaker.feature.world.overworld;

import org.auioc.mcmod.arnicalib.utils.game.EntityUtils;
import org.auioc.mcmod.arnicalib.utils.game.RandomTeleporter;
import com.haruhifanclub.hstweaker.api.world.AbstractHSTWorld;
import com.haruhifanclub.hstweaker.feature.world.HSTWorlds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.Level;

public class Overworld extends AbstractHSTWorld {

    public Overworld() {
        super(Level.OVERWORLD);
    }

    @Override
    public void onPlayerLoggedIn(ServerPlayer player, ServerLevel level) {
        var totalWorldTime = player.getStats().getValue(Stats.CUSTOM.get(Stats.TOTAL_WORLD_TIME));
        if (totalWorldTime < 20) {
            EntityUtils.teleportTo(player, HSTWorlds.LOBBY.key, HSTWorlds.LOBBY.entryPoint);
            sendChatMessage(player, "first_login");
        }
    }

    @Override
    public void onPlayerRespawn(ServerPlayer player, ServerLevel level) {
        randomRespawn(player);
    }

    @Override
    public void onPlayerTravelTo(ServerPlayer player, ServerLevel from) {
        if (HSTWorlds.LOBBY.is(from)) randomRespawn(player);
    }

    private static void randomRespawn(ServerPlayer player) {
        if (player.getRespawnPosition() == null || !player.getRespawnDimension().equals(Level.OVERWORLD)) {
            RandomTeleporter.teleport(player, 10, 10);
            HSTWorlds.OVERWORLD.sendBarMessage(player, "random_respawn");
        }
    }

}
