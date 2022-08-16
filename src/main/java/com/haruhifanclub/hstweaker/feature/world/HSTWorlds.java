package com.haruhifanclub.hstweaker.feature.world;

import static com.haruhifanclub.hstweaker.HSTweaker.LOGGER;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import org.auioc.mcmod.arnicalib.utils.game.MessageHelper;
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

    public static final Marker MARKER = LogUtil.getMarker(HSTWorlds.class);
    public static final MessageHelper MSGH = new MessageHelper(HSTweaker.MESSAGE_PREFIX, (k) -> HSTweaker.i18n("world." + k));

    private static final HashMap<ResourceKey<Level>, AbstractHSTWorld> REGISTRY = new HashMap<ResourceKey<Level>, AbstractHSTWorld>();

    public static final AbstractHSTWorld BUILDING = register(BuildingWorld::new);
    public static final AbstractHSTWorld LOBBY = register(LobbyWorld::new);
    public static final AbstractHSTWorld OVERWORLD = register(Overworld::new);

    private static AbstractHSTWorld register(Supplier<AbstractHSTWorld> sup) {
        var c = sup.get();
        REGISTRY.put(c.key, c);
        logInfo("Register HSTWorld of level " + c.getLevelKey());
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

    public static MutableComponent createMessage(String key, Object... args) {
        return MSGH.create(key, args, true);
    }

    public static void logInfo(String message) {
        LOGGER.info(MARKER, message);
    }

    public static void logWarn(String message) {
        LOGGER.warn(MARKER, message);
    }

}
