package com.haruhifanclub.hstweaker.feature.world.lobby;

import org.auioc.mcmod.arnicalib.utils.game.LevelUtils;
import com.haruhifanclub.hstweaker.HSTweaker;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class LobbyWorld {

    public static final ResourceLocation LEVEL_ID = HSTweaker.id("lobby_world");
    public static final ResourceKey<Level> LEVEL_KEY = LevelUtils.createKey(LEVEL_ID);

    public static final BlockPos ENTRY_POINT = new BlockPos(0, 65, 0);

    public static ServerLevel getLevel() {
        return LevelUtils.getLevel(LEVEL_KEY);
    }

    public static boolean isThis(Level level) {
        return level.dimension().equals(LEVEL_KEY);
    }

    public static boolean isThis(ResourceKey<Level> dim) {
        return dim.equals(LEVEL_KEY);
    }

    public static void onEnter(ServerPlayer player, ServerLevel level) {
        level.setBlockAndUpdate(ENTRY_POINT.below(), Blocks.BEDROCK.defaultBlockState());
        if (player.createCommandSourceStack().hasPermission(3)) return;
        player.setGameMode(GameType.ADVENTURE);
    }

    public static void onExit(ServerPlayer player, ServerLevel level) {
        if (player.createCommandSourceStack().hasPermission(3)) return;
        player.setGameMode(GameType.SURVIVAL);
    }

    public static String i18n(String key) {
        return HSTweaker.i18n("lobbyworld." + key);
    }

}
