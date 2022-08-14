package com.haruhifanclub.hstweaker.api.world;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.BlockHitResult;

public interface IHSTWorld {

    ServerLevel get();

    default boolean onEntityJoin(Entity entity, ServerLevel level) {
        if (entity instanceof ServerPlayer player) onPlayerJoin(player, level);
        return true;
    }

    default boolean onEntityLeave(Entity entity, ServerLevel level) {
        if (entity instanceof ServerPlayer player) onPlayerLeave(player, level);
        return true;
    }

    default void onPlayerJoin(ServerPlayer player, ServerLevel level) {}

    default void onPlayerLeave(ServerPlayer player, ServerLevel level) {}

    default void onPlayerTravelTo(ServerPlayer player, ServerLevel from) {}

    default void onPlayerTravelFrom(ServerPlayer player, ServerLevel to) {}

    default boolean onExplosionStart(Explosion explosion, ServerLevel level) {
        return true;
    }

    default boolean onRightClickBlock(ServerPlayer player, BlockHitResult hit, ServerLevel level) {
        return true;
    }

}
