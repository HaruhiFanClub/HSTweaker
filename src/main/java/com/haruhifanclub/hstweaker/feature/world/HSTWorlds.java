package com.haruhifanclub.hstweaker.feature.world;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;
import org.auioc.mcmod.arnicalib.utils.game.TextUtils;
import com.haruhifanclub.hstweaker.HSTweaker;
import com.haruhifanclub.hstweaker.api.world.AbstractHSTWorld;
import com.haruhifanclub.hstweaker.feature.world.impl.BuildingWorld;
import com.haruhifanclub.hstweaker.feature.world.impl.LobbyWorld;
import com.haruhifanclub.hstweaker.feature.world.impl.Overworld;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public final class HSTWorlds {

    private static final HashMap<ResourceKey<Level>, AbstractHSTWorld> REGISTRY = new HashMap<ResourceKey<Level>, AbstractHSTWorld>();

    public static final AbstractHSTWorld BUILDING = register(BuildingWorld::new);
    public static final AbstractHSTWorld LOBBY = register(LobbyWorld::new);
    public static final AbstractHSTWorld OVERWORLD = register(Overworld::new);

    private static AbstractHSTWorld register(Supplier<AbstractHSTWorld> sup) {
        var c = sup.get();
        REGISTRY.put(c.key, c);
        return c;
    }

    public static HashMap<ResourceKey<Level>, AbstractHSTWorld> getAll() {
        return REGISTRY;
    }

    public static boolean is(ResourceKey<Level> dim) {
        return REGISTRY.containsKey(dim);
    }

    public static boolean is(ServerLevel level) {
        return is(level.dimension());
    }

    public static Optional<AbstractHSTWorld> get(ResourceKey<Level> dim) {
        return Optional.ofNullable(REGISTRY.get(dim));
    }

    public static Optional<AbstractHSTWorld> get(ServerLevel level) {
        return get(level.dimension());
    }

    public static MutableComponent createMessage(String key, boolean prefix, Object args) {
        return TextUtils.empty().append(prefix ? HSTweaker.MESSAGE_PREFIX : TextUtils.empty()).append(TextUtils.translatable(HSTweaker.i18n("world." + key), args));
    }

    public static MutableComponent createMessageP(String key) {
        return createMessage(key, true, TextUtils.NO_ARGS);
    }

    public static MutableComponent createMessageP(String key, Object args) {
        return createMessage(key, true, args);
    }

    public static MutableComponent createMessageN(String key) {
        return createMessage(key, false, TextUtils.NO_ARGS);
    }

    public static MutableComponent createMessageN(String key, Object args) {
        return createMessage(key, false, args);
    }

}
