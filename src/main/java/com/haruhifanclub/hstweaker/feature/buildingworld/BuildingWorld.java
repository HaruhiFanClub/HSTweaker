package com.haruhifanclub.hstweaker.feature.buildingworld;

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

public final class BuildingWorld {

    public static final ResourceLocation LEVEL_ID = HSTweaker.id("building_world");
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

    public static void onEnter(ServerPlayer player) {
        getLevel().setBlockAndUpdate(ENTRY_POINT.below(), Blocks.BEDROCK.defaultBlockState());
        if (player.createCommandSourceStack().hasPermission(3)) return;
        player.setGameMode(GameType.CREATIVE);
    }

    public static void onExit(ServerPlayer player) {
        if (player.createCommandSourceStack().hasPermission(3)) return;
        player.getInventory().clearContent();
        player.setExperienceLevels(0);
        player.setExperiencePoints(0);
        player.removeAllEffects();
        player.setGameMode(GameType.SURVIVAL);
    }

    public static String i18n(String key) {
        return HSTweaker.i18n("buildingworld." + key);
    }

}
