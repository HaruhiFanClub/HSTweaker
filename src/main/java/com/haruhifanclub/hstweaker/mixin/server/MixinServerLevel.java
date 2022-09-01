package com.haruhifanclub.hstweaker.mixin.server;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

@Mixin(value = ServerLevel.class)
public class MixinServerLevel {

    @Redirect(
        method = "Lnet/minecraft/server/level/ServerLevel;lambda$makeObsidianPlatform$21(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;)V",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/level/block/Blocks;OBSIDIAN:Lnet/minecraft/world/level/block/Block;",
            opcode = Opcodes.GETSTATIC
        )
    )
    private static Block redirect_makeObsidianPlatform_getObsidianBlock() {
        return Blocks.BEDROCK;
    }

}
