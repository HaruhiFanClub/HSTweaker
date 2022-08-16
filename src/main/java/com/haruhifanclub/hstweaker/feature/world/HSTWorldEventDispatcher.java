package com.haruhifanclub.hstweaker.feature.world;

import java.util.function.BiFunction;
import org.apache.logging.log4j.util.TriConsumer;
import org.auioc.mcmod.arnicalib.utils.game.LevelUtils;
import com.haruhifanclub.hstweaker.api.world.AbstractHSTWorld;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HSTWorldEventDispatcher {

    static {
        HSTWorlds.logInfo("Register world event dispatcher");
    }

    private static void handleCancelableEvent(Event event, Level level, BiFunction<AbstractHSTWorld, ServerLevel, Boolean> action) {
        if (level.isClientSide) return;
        var serverLevel = (ServerLevel) level;
        HSTWorlds.get(serverLevel).ifPresent((hstw) -> {
            if (!action.apply(hstw, serverLevel)) {
                event.setCanceled(true);
            }
        });
    }

    private static void handlePlayerEvent(PlayerEvent event, TriConsumer<AbstractHSTWorld, ServerPlayer, ServerLevel> action) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            var level = (ServerLevel) player.getLevel();
            HSTWorlds.get(level).ifPresent((hstw) -> action.accept(hstw, player, level));
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(final EntityJoinWorldEvent event) {
        handleCancelableEvent(
            event, event.getWorld(), (hstw, level) -> hstw.onEntityJoin(event.getEntity(), level)
        );
    }

    @SubscribeEvent
    public static void onEntityLeaveWorld(final EntityLeaveWorldEvent event) {
        handleCancelableEvent(
            event, event.getWorld(), (hstw, level) -> hstw.onEntityLeave(event.getEntity(), level)
        );
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(final PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            var from = event.getFrom();
            var to = event.getTo();

            var hstw1 = HSTWorlds.get(to);
            if (hstw1.isPresent()) {
                hstw1.get().onPlayerTravelledTo(player, LevelUtils.getLevel(from));
            } else {
                HSTWorlds.get(from).ifPresent((hstw) -> hstw.onPlayerTravelledFrom(player, LevelUtils.getLevel(to)));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
        handlePlayerEvent(event, (hstw, player, level) -> hstw.onPlayerLoggedIn(player, level));
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(final PlayerEvent.PlayerLoggedOutEvent event) {
        handlePlayerEvent(event, (hstw, player, level) -> hstw.onPlayerLoggedOut(player, level));
    }

    @SubscribeEvent
    public static void onPlayerRespwan(final PlayerEvent.PlayerRespawnEvent event) {
        handlePlayerEvent(event, (hstw, player, level) -> hstw.onPlayerRespawn(player, level));
    }

    @SubscribeEvent
    public static void onExplosionStart(final ExplosionEvent.Start event) {
        handleCancelableEvent(
            event, event.getWorld(), (hstw, level) -> hstw.onExplosionStart(event.getExplosion(), level)
        );
    }

    @SubscribeEvent
    public static void onRightClickBlock(final PlayerInteractEvent.RightClickBlock event) {
        handleCancelableEvent(
            event, event.getWorld(), (hstw, level) -> hstw.onRightClickBlock((ServerPlayer) event.getPlayer(), event.getHitVec(), level)
        );
    }

}
