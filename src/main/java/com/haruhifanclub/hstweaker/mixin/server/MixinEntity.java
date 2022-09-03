package com.haruhifanclub.hstweaker.mixin.server;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

@Mixin(value = Entity.class)
public class MixinEntity {

    @Overwrite()
    public boolean canChangeDimensions() {
        return ((Entity) ((Object) this)) instanceof Player;
    }

}
