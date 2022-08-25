package com.haruhifanclub.hstweaker.mixin.server;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.server.level.ServerPlayer;

@Mixin(value = ServerPlayer.class)
public class MixinServerPlayer {

    @Overwrite()
    public boolean shouldFilterMessageTo(ServerPlayer p_143422_) {
        return false;
    }

}
