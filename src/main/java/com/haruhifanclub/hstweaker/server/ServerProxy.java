package com.haruhifanclub.hstweaker.server;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.server.ServerLifecycleHooks;

public final class ServerProxy {

    public static MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    public static ServerLevel getLevel(ResourceKey<Level> key) {
        return getServer().getLevel(key);
    }

}
