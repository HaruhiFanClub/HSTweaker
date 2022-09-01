package com.haruhifanclub.hstweaker.mixin.server;

import java.util.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import com.haruhifanclub.hstweaker.api.mixin.IMixinZombifiedPiglin;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(value = NetherPortalBlock.class)
public class MixinNetherPortalBlock {

    // @org.spongepowered.asm.mixin.Debug(export = true, print = true)
    @Inject(
        method = "Lnet/minecraft/world/level/block/NetherPortalBlock;randomTick(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Ljava/util/Random;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;setPortalCooldown()V",
            ordinal = 0
        ),
        locals = LocalCapture.CAPTURE_FAILHARD,
        require = 1,
        allow = 1
    )
    private void randomTick(BlockState p_54937_, ServerLevel p_54938_, BlockPos p_54939_, Random p_54940_, CallbackInfo ci, Entity entity) {
        ((IMixinZombifiedPiglin) ((ZombifiedPiglin) entity)).setNoDrop();
    }

}
