package com.haruhifanclub.hstweaker.feature.world.impl;

import java.util.Optional;
import org.auioc.mcmod.arnicalib.utils.game.PlayerUtils;
import com.haruhifanclub.hstweaker.api.world.AbstractHSTWorld;
import com.haruhifanclub.hstweaker.feature.world.HSTWorldCommands;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameType;

public final class LobbyWorld extends AbstractHSTWorld {

    public LobbyWorld() {
        super("lobby");
    }

    @Override
    public Optional<CommandNode<CommandSourceStack>> createCommandNode() {
        return Optional.of(HSTWorldCommands.createNode(this, false));
    }

    @Override
    public boolean onEntityJoin(Entity entity, ServerLevel level) {
        if (entity instanceof ItemEntity) return false;
        else return super.onEntityJoin(entity, level);
    }

    @Override
    public void onPlayerJoin(ServerPlayer player, ServerLevel level) {
        this.createEntryPlatform(level);
        if (!PlayerUtils.isOp(player)) {
            player.setGameMode(GameType.ADVENTURE);
        }
    }

    @Override
    public void onPlayerLeave(ServerPlayer player, ServerLevel level) {
        if (!PlayerUtils.isOp(player)) {
            player.setGameMode(GameType.SURVIVAL);
        }
    }

    @Override
    public boolean onExplosionStart(Explosion explosion, ServerLevel level) {
        if (explosion.getSourceMob() instanceof ServerPlayer player) sendBarMessage(player, "no_explosion");
        return false;
    }

}
