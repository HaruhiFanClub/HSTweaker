package com.haruhifanclub.hstweaker.feature.world.lobby;

import com.haruhifanclub.hstweaker.api.world.AbstractHSTWorld;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;

public final class LobbyWorld extends AbstractHSTWorld {

    public LobbyWorld() {
        super("lobby");
    }

    @Override
    public boolean onEntityJoin(Entity entity, ServerLevel level) {
        if (entity instanceof ItemEntity) return false;
        else return super.onEntityJoin(entity, level);
    }

    @Override
    public void onPlayerJoin(ServerPlayer player, ServerLevel level) {
        level.setBlockAndUpdate(entryPoint.below(), Blocks.BEDROCK.defaultBlockState());
        if (!player.createCommandSourceStack().hasPermission(3)) {
            player.setGameMode(GameType.ADVENTURE);
        }
    }

    @Override
    public void onPlayerLeave(ServerPlayer player, ServerLevel level) {
        if (!player.createCommandSourceStack().hasPermission(3)) {
            player.setGameMode(GameType.SURVIVAL);
        }
    }

    @Override
    public boolean onExplosionStart(Explosion explosion, ServerLevel level) {
        if (explosion.getSourceMob() instanceof ServerPlayer player) warnChat(player, "no_explosion");
        return false;
    }

}
