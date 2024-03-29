package com.haruhifanclub.hstweaker.mixin.server;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

@Mixin(value = ServerPlayer.class)
public class MixinServerPlayer {

    @Overwrite()
    public boolean shouldFilterMessageTo(ServerPlayer p_143422_) {
        return false;
    }

    @Redirect(
        method = "Lnet/minecraft/server/level/ServerPlayer;createEndPlatform(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;)V",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/level/block/Blocks;OBSIDIAN:Lnet/minecraft/world/level/block/Block;",
            opcode = Opcodes.GETSTATIC
        )
    )
    private Block redirect_createEndPlatform_getObsidianBlock() {
        return Blocks.BEDROCK;
    }

}
