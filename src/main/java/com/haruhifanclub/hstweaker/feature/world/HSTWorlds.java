package com.haruhifanclub.hstweaker.feature.world;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import org.auioc.mcmod.arnicalib.utils.game.LevelUtils;
import org.auioc.mcmod.arnicalib.utils.game.TextUtils;
import com.haruhifanclub.hstweaker.HSTweaker;
import com.haruhifanclub.hstweaker.feature.world.building.BuildingWorld;
import com.haruhifanclub.hstweaker.feature.world.lobby.LobbyWorld;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public enum HSTWorlds {

    BUILDING("building_world", new BlockPos(0, 65, 0), BuildingWorld::onJoin, BuildingWorld::onLeave), //
    LOBBY("building_world", new BlockPos(0, 65, 0), LobbyWorld::onJoin, LobbyWorld::onLeave);

    public final String sid;
    public final ResourceLocation id;
    public final ResourceKey<Level> key;
    public final BlockPos entryPoint;
    public final BiFunction<Entity, ServerLevel, Boolean> onJoin;
    public final BiFunction<Entity, ServerLevel, Boolean> onLeave;

    HSTWorlds(String id, BlockPos entryPoint, BiFunction<Entity, ServerLevel, Boolean> onJoin, BiFunction<Entity, ServerLevel, Boolean> onLeave) {
        this.sid = id;
        this.id = HSTweaker.id(id);
        this.key = LevelUtils.createKey(this.id);
        this.entryPoint = entryPoint;
        this.onJoin = onJoin;
        this.onLeave = onLeave;
    }

    HSTWorlds(String id, BlockPos entryPoint) {
        this(id, entryPoint, (e, l) -> true, (e, l) -> true);
    }

    HSTWorlds(String id) {
        this(id, new BlockPos(0, 0, 0), (e, l) -> true, (e, l) -> true);
    }

    public ServerLevel get() {
        return LevelUtils.getLevel(this.key);
    }

    public boolean is(ResourceKey<Level> dim) {
        return dim.equals(this.key);
    }

    public boolean is(Level level) {
        return level.dimension().equals(this.key);
    }

    public String i18n(String key) {
        return HSTweaker.i18n(this.sid + "." + key);
    }

    public void warn(ServerPlayer player, String key) {
        player.sendMessage(TextUtils.translatable(this.i18n(key)).withStyle(ChatFormatting.RED), ChatType.GAME_INFO, Util.NIL_UUID);
    }


    private static final HashMap<ResourceKey<Level>, HSTWorlds> MAP = new HashMap<ResourceKey<Level>, HSTWorlds>() {
        {
            for (HSTWorlds e : EnumSet.allOf(HSTWorlds.class)) put(e.key, e);
        }
    };

    public static boolean isHSTWorld(ServerLevel level) {
        return MAP.containsKey(level.dimension());
    }

    @Nullable
    public static HSTWorlds getHSTWorld(ServerLevel level) {
        return MAP.get(level.dimension());
    }

    public static boolean onJoin(Entity entity, ServerLevel level) {
        var hstw = getHSTWorld(level);
        if (hstw != null) return hstw.onJoin.apply(entity, level);
        return true;
    }

    public static boolean onLeave(Entity entity, ServerLevel level) {
        var hstw = getHSTWorld(level);
        if (hstw != null) return hstw.onLeave.apply(entity, level);
        return true;
    }

}
