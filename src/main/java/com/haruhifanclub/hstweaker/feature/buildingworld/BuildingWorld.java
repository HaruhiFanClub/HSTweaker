package com.haruhifanclub.hstweaker.feature.buildingworld;

import java.util.function.Function;
import com.haruhifanclub.hstweaker.HSTweaker;
import com.haruhifanclub.hstweaker.server.ServerProxy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.ITeleporter;

public final class BuildingWorld {

    public static final ResourceLocation LEVEL_ID = HSTweaker.id("building_world");
    public static final ResourceKey<Level> LEVEL_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY, LEVEL_ID);

    public static ServerLevel getLevel() {
        return ServerProxy.getServer().getLevel(LEVEL_KEY);
    }

    public static boolean isThis(ServerLevel level) {
        return level.dimension().equals(LEVEL_KEY);
    }

    public static ITeleporter createTeleporter(BlockPos pos) {
        return new ITeleporter() {
            @Override
            public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                entity = repositionEntity.apply(false);
                entity.teleportTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
                return entity;
            }

            @Override
            public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
                return false;
            }
        };
    }

}
