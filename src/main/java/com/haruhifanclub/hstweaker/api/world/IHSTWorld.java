package com.haruhifanclub.hstweaker.api.world;

import static com.haruhifanclub.hstweaker.HSTweaker.LOGGER;
import java.util.Optional;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public interface IHSTWorld {

    public static final Marker MARKER = LogUtil.getMarker(IHSTWorld.class);

    ServerLevel get();

    default Optional<CommandNode<CommandSourceStack>> createCommandNode() {
        return Optional.empty();
    }

    default boolean onEntityJoin(Entity entity, ServerLevel level) {
        if (entity instanceof ServerPlayer player) onPlayerJoin(player, level);
        return true;
    }

    default boolean onEntityLeave(Entity entity, ServerLevel level) {
        if (entity instanceof ServerPlayer player) onPlayerLeave(player, level);
        return true;
    }

    default boolean onLivingDamage(LivingEntity living, DamageSource source, ServerLevel level, LivingDamageEvent event) {
        if (living instanceof ServerPlayer player) return onPlayerDamage(player, source, level, event);
        return true;
    }

    default void onPlayerJoin(ServerPlayer player, ServerLevel level) {
        LOGGER.info(MARKER, "Player {} joined {}", player.getGameProfile().getName(), level.toString());
    }

    default void onPlayerLeave(ServerPlayer player, ServerLevel level) {
        LOGGER.info(MARKER, "Player {} left {}", player.getGameProfile().getName(), level.toString());
    }

    default boolean onPlayerDamage(ServerPlayer player, DamageSource source, ServerLevel level, LivingDamageEvent event) {
        return true;
    }

    default void onPlayerTravelledTo(ServerPlayer player, ServerLevel from) {}

    default void onPlayerTravelledFrom(ServerPlayer player, ServerLevel to) {}

    default void onPlayerLoggedIn(ServerPlayer player, ServerLevel level) {}

    default void onPlayerLoggedOut(ServerPlayer player, ServerLevel level) {}

    default void onPlayerRespawn(ServerPlayer player, ServerLevel level) {}

    default boolean onExplosionStart(Explosion explosion, ServerLevel level) {
        return true;
    }

    default boolean onRightClickBlock(ServerPlayer player, BlockHitResult hit, ServerLevel level) {
        return true;
    }

}
