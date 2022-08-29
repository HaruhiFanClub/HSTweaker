package com.haruhifanclub.hstweaker.feature.world.impl;

import java.util.Optional;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import org.auioc.mcmod.arnicalib.utils.game.PlayerUtils;
import com.haruhifanclub.hstweaker.api.world.AbstractHSTWorld;
import com.haruhifanclub.hstweaker.feature.world.HSTWorldCommands;
import com.haruhifanclub.hstweaker.feature.world.HSTWorlds;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameType;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public final class LobbyWorld extends AbstractHSTWorld {

    public static final Marker MARKER = LogUtil.getMarker(LobbyWorld.class).addParents(HSTWorlds.MARKER);

    public LobbyWorld() {
        super("lobby");
    }

    @Override
    public Optional<CommandNode<CommandSourceStack>> createCommandNode() {
        return Optional.of(HSTWorldCommands.createNode(this, false, 6000));
    }

    @Override
    public boolean onEntityJoin(Entity entity, ServerLevel level) {
        if (entity instanceof ItemEntity) return false;
        else return super.onEntityJoin(entity, level);
    }

    @Override
    public boolean onPlayerDamage(ServerPlayer player, DamageSource source, ServerLevel level, LivingDamageEvent event) {
        if (!source.isBypassInvul()) return false;
        else return true;
    }

    @Override
    public void onPlayerJoin(ServerPlayer player, ServerLevel level) {
        this.createEntryPlatform(level);
        if (!PlayerUtils.isOp(player)) {
            player.setGameMode(GameType.ADVENTURE);
        }
        super.onPlayerJoin(player, level);
    }

    @Override
    public void onPlayerLeave(ServerPlayer player, ServerLevel level) {
        if (!PlayerUtils.isOp(player)) {
            player.setGameMode(GameType.SURVIVAL);
        }
        super.onPlayerLeave(player, level);
    }

    @Override
    public boolean onExplosionStart(Explosion explosion, ServerLevel level) {
        if (explosion.getSourceMob() instanceof ServerPlayer player) this.msgh.sendGameInfo(player, "no_explosion");
        return false;
    }

}
