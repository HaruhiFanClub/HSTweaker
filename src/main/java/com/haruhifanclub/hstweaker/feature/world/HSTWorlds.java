package com.haruhifanclub.hstweaker.feature.world;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;
import com.haruhifanclub.hstweaker.api.world.AbstractHSTWorld;
import com.haruhifanclub.hstweaker.feature.world.building.BuildingWorld;
import com.haruhifanclub.hstweaker.feature.world.lobby.LobbyWorld;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public final class HSTWorlds {

    private static final HashMap<ResourceKey<Level>, AbstractHSTWorld> REGISTRY = new HashMap<ResourceKey<Level>, AbstractHSTWorld>();

    public static final AbstractHSTWorld BUILDING = register(BuildingWorld::new);
    public static final AbstractHSTWorld LOBBY = register(LobbyWorld::new);

    private static AbstractHSTWorld register(Supplier<AbstractHSTWorld> sup) {
        var c = sup.get();
        REGISTRY.put(c.key, c);
        return c;
    }

    public static boolean isHSTWorld(ResourceKey<Level> dim) {
        return REGISTRY.containsKey(dim);
    }

    public static boolean isHSTWorld(ServerLevel level) {
        return isHSTWorld(level.dimension());
    }

    public static Optional<AbstractHSTWorld> getHSTWorld(ResourceKey<Level> dim) {
        return Optional.ofNullable(REGISTRY.get(dim));
    }

    public static Optional<AbstractHSTWorld> getHSTWorld(ServerLevel level) {
        return getHSTWorld(level.dimension());
    }

}
