package com.haruhifanclub.hstweaker.feature.world.impl;

import static com.haruhifanclub.hstweaker.HSTweaker.LOGGER;
import java.util.List;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import org.auioc.mcmod.arnicalib.utils.game.EntityUtils;
import org.auioc.mcmod.arnicalib.utils.game.PlayerUtils;
import org.auioc.mcmod.arnicalib.utils.game.RandomTeleporter;
import com.haruhifanclub.hstweaker.HSTweaker;
import com.haruhifanclub.hstweaker.api.world.AbstractHSTWorld;
import com.haruhifanclub.hstweaker.feature.world.HSTWorlds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class Overworld extends AbstractHSTWorld {

    public static final Marker MARKER = LogUtil.getMarker(Overworld.class).addParents(HSTWorlds.MARKER);

    public Overworld() {
        super(Level.OVERWORLD);
    }

    @Override
    public void onPlayerLoggedIn(ServerPlayer player, ServerLevel level) {
        var playTime = player.getStats().getValue(Stats.CUSTOM.get(Stats.PLAY_TIME));
        if (playTime < 10) {
            LOGGER.info(MARKER, "{} first logged in", player.getGameProfile().getName());
            EntityUtils.teleportTo(player, HSTWorlds.LOBBY.key, HSTWorlds.LOBBY.entryPoint);
            // this.msgh.sendChatMessage(player, "first_login");
            giveFirstLoginItems(player, level);
        }
    }

    @Override
    public void onPlayerJoin(ServerPlayer player, ServerLevel level) {}

    @Override
    public void onPlayerLeave(ServerPlayer player, ServerLevel level) {}

    @Override
    public void onPlayerRespawn(ServerPlayer player, ServerLevel level) {
        randomRespawn(player);
    }

    @Override
    public void onPlayerTravelledTo(ServerPlayer player, ServerLevel from) {
        if (HSTWorlds.LOBBY.is(from)) randomRespawn(player);
    }

    private void randomRespawn(ServerPlayer player) {
        if (!PlayerUtils.isOp(player) && (player.getRespawnPosition() == null || !player.getRespawnDimension().equals(Level.OVERWORLD))) {
            RandomTeleporter.findSafePosition(player, player.blockPosition(), 256, true, 16)
                .ifPresentOrElse(
                    (pos) -> {
                        EntityUtils.teleportTo(player, pos);
                        LOGGER.info(MARKER, "Random respawn player {} at {}", player.getGameProfile().getName(), pos.toString());
                        this.msgh.sendGameInfo(player, "random_respawn");
                    },
                    () -> LOGGER.warn(MARKER, "Failed to find a safe random respawn position for player {}", player.getGameProfile().getName())
                );
        }
    }

    private static void giveFirstLoginItems(ServerPlayer player, ServerLevel level) {
        LootContext ctx = new LootContext.Builder(level)
            .withParameter(LootContextParams.THIS_ENTITY, player)
            .withParameter(LootContextParams.ORIGIN, player.position())
            .create(LootContextParamSets.GIFT);

        LootTable lootTable = level.getServer().getLootTables().get(HSTweaker.id("first_login"));
        List<ItemStack> list = lootTable.getRandomItems(ctx);
        for (var stack : list) {
            PlayerUtils.giveItem(player, stack);
        }
    }

}
