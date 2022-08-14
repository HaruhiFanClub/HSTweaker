package com.haruhifanclub.hstweaker.feature.world;

import java.util.function.BiFunction;
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

    private static void handleCancelableEvent(Event event, Level level, BiFunction<AbstractHSTWorld, ServerLevel, Boolean> action) {
        if (level.isClientSide) return;
        var serverLevel = (ServerLevel) level;
        HSTWorlds.getHSTWorld(serverLevel).ifPresent((hstw) -> {
            if (!action.apply(hstw, serverLevel)) {
                event.setCanceled(true);
            }
        });
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
            HSTWorlds.getHSTWorld(from).ifPresent((hstw) -> hstw.onPlayerTravelFrom(player, LevelUtils.getLevel(to)));
            HSTWorlds.getHSTWorld(to).ifPresent((hstw) -> hstw.onPlayerTravelTo(player, LevelUtils.getLevel(from)));
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            var level = (ServerLevel) player.getLevel();
            HSTWorlds.getHSTWorld(level).ifPresent((hstw) -> hstw.onPlayerLoggedIn(player, level));
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(final PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            var level = (ServerLevel) player.getLevel();
            HSTWorlds.getHSTWorld(level).ifPresent((hstw) -> hstw.onPlayerLoggedOut(player, level));
        }
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
