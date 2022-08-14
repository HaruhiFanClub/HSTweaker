package com.haruhifanclub.hstweaker.api.world;

import org.auioc.mcmod.arnicalib.utils.game.LevelUtils;
import org.auioc.mcmod.arnicalib.utils.game.TextUtils;
import com.haruhifanclub.hstweaker.HSTweaker;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public abstract class AbstractHSTWorld implements IHSTWorld {

    public static final BlockPos DEFAULT_ENTRY_POINT = new BlockPos(0, 65, 0);

    public final String sid;
    public final ResourceLocation id;
    public final ResourceKey<Level> key;
    public final BlockPos entryPoint;

    public AbstractHSTWorld(String sid, BlockPos entryPoint) {
        this.sid = sid;
        this.id = HSTweaker.id(sid);
        this.key = LevelUtils.createKey(this.id);
        this.entryPoint = entryPoint;
    }

    public AbstractHSTWorld(String id) {
        this(id, DEFAULT_ENTRY_POINT);
    }

    public AbstractHSTWorld(ResourceKey<Level> key, BlockPos entryPoint) {
        this.key = key;
        this.id = key.location();
        this.sid = this.id.getPath();
        this.entryPoint = entryPoint;
    }

    public AbstractHSTWorld(ResourceKey<Level> key) {
        this(key, DEFAULT_ENTRY_POINT);
    }

    @Override
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
        return HSTweaker.i18n("world." + this.sid + "." + key);
    }

    public void warn(ServerPlayer player, String key) {
        player.sendMessage(TextUtils.translatable(this.i18n(key)).withStyle(ChatFormatting.RED), ChatType.GAME_INFO, Util.NIL_UUID);
    }

}
