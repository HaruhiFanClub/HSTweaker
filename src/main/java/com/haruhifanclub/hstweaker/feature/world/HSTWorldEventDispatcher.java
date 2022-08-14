package com.haruhifanclub.hstweaker.feature.world;

import com.haruhifanclub.hstweaker.feature.world.building.BuildingWorld;
import com.haruhifanclub.hstweaker.feature.world.lobby.LobbyWorld;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.BlockHitResult;

public class HSTWorldEventDispatcher {

    public static boolean onJoin(Entity entity, ServerLevel level) {
        return HSTWorlds.onJoin(entity, level);
    }

    public static boolean onLeave(Entity entity, ServerLevel level) {
        return HSTWorlds.onLeave(entity, level);
    }

    public static boolean onExplosionStart(Explosion explosion, ServerLevel level) {
        return BuildingWorld.onExplosionStart(explosion, level) && LobbyWorld.onExplosionStart(explosion, level);
    }

    public static boolean onRightClickBlock(ServerPlayer player, BlockHitResult hit, ServerLevel level) {
        return BuildingWorld.onRightClickBlock(player, hit, level);
    }

}
