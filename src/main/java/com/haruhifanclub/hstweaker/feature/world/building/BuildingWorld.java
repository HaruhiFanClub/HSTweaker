package com.haruhifanclub.hstweaker.feature.world.building;

import java.util.Optional;
import com.haruhifanclub.hstweaker.api.world.AbstractHSTWorld;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public final class BuildingWorld extends AbstractHSTWorld {

    public BuildingWorld() {
        super("building");
    }

    @Override
    public Optional<CommandNode<CommandSourceStack>> createCommandNode() {
        return Optional.of(BuildingWorldCommands.NODE);
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
            player.setGameMode(GameType.CREATIVE);
        }
    }

    @Override
    public void onPlayerLeave(ServerPlayer player, ServerLevel level) {
        if (!player.createCommandSourceStack().hasPermission(3)) {
            player.getInventory().clearContent();
            player.setExperienceLevels(0);
            player.setExperiencePoints(0);
            player.removeAllEffects();
            player.setGameMode(GameType.SURVIVAL);
        }
    }

    @Override
    public boolean onExplosionStart(Explosion explosion, ServerLevel level) {
        if (explosion.getSourceMob() instanceof ServerPlayer player) sendBarMessage(player, "no_explosion");
        return false;
    }

    @Override
    public boolean onRightClickBlock(ServerPlayer player, BlockHitResult hit, ServerLevel level) {
        if (hit.getType() == HitResult.Type.BLOCK) {
            var block = level.getBlockState(hit.getBlockPos());
            if (block.is(Blocks.ENDER_CHEST)) {
                sendBarMessage(player, "block_ender_chest");
                return false;
            }
        }
        return true;
    }

}
