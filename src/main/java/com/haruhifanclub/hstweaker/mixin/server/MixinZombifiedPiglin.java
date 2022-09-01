package com.haruhifanclub.hstweaker.mixin.server;

import org.spongepowered.asm.mixin.Mixin;
import com.haruhifanclub.hstweaker.api.mixin.IMixinZombifiedPiglin;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.level.Level;

@Mixin(value = ZombifiedPiglin.class)
public class MixinZombifiedPiglin extends Zombie implements IMixinZombifiedPiglin {

    public MixinZombifiedPiglin(Level p_34274_) {
        super(p_34274_);
    }

    private boolean shouldDrop = true;

    @Override
    public void setNoDrop() {
        shouldDrop = false;
    }

    @Override
    public boolean shouldDropExperience() {
        return shouldDrop && super.shouldDropExperience();
    }

    @Override
    public boolean shouldDropLoot() {
        return shouldDrop && super.shouldDropLoot();
    }

}
