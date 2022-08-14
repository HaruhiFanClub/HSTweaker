package com.haruhifanclub.hstweaker.feature.world.lobby;

import com.haruhifanclub.hstweaker.feature.world.HSTWorlds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;

public class LobbyWorld {

    public static boolean onJoin(Entity entity, ServerLevel level) {
        if (entity instanceof ServerPlayer player) {
            level.setBlockAndUpdate(HSTWorlds.LOBBY.entryPoint.below(), Blocks.BEDROCK.defaultBlockState());
            if (!player.createCommandSourceStack().hasPermission(3)) {
                player.setGameMode(GameType.ADVENTURE);
            }
        }
        return true;
    }

    public static boolean onLeave(Entity entity, ServerLevel level) {
        if (entity instanceof ServerPlayer player) {
            if (!player.createCommandSourceStack().hasPermission(3)) {
                player.setGameMode(GameType.SURVIVAL);
            }
        }
        return true;
    }

    public static boolean onExplosionStart(Explosion explosion, ServerLevel level) {
        if (explosion.getSourceMob() instanceof ServerPlayer player) HSTWorlds.LOBBY.warn(player, "no_explosion");
        return false;
    }

}
