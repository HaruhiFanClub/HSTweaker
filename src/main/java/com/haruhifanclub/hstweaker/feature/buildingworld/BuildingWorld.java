package com.haruhifanclub.hstweaker.feature.buildingworld;

import org.auioc.mcmod.arnicalib.utils.game.LevelUtils;
import com.haruhifanclub.hstweaker.HSTweaker;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public final class BuildingWorld {

    public static final ResourceLocation LEVEL_ID = HSTweaker.id("building_world");
    public static final ResourceKey<Level> LEVEL_KEY = LevelUtils.createKey(LEVEL_ID);

    public static ServerLevel getLevel() {
        return LevelUtils.getLevel(LEVEL_KEY);
    }

    public static boolean isThis(Level level) {
        return level.dimension().equals(LEVEL_KEY);
    }

    public static boolean isThis(ResourceKey<Level> dim) {
        return dim.equals(LEVEL_KEY);
    }

    public static String i18n(String key) {
        return HSTweaker.i18n("buildingworld." + key);
    }

}
