package com.haruhifanclub.hstweaker.api.mixin;

public interface IMixinZombifiedPiglin {

    boolean shouldDropExperience();

    boolean shouldDropLoot();

    void setNoDrop();

}
