package com.haruhifanclub.mods.hstweaker.common.potion.base;

import java.util.function.Supplier;
import com.haruhifanclub.mods.hstweaker.common.potion.PotionRegistry;
import org.auioc.mods.ahutils.api.item.HPotion;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.RegistryObject;

public class HTPotion extends HPotion {

    public HTPotion(String name, MobEffect effect, int duration, int amplifier) {
        super(name, effect, duration, amplifier);
    }

    public static RegistryObject<Potion> register(String id, Supplier<? extends Potion> sup) {
        return PotionRegistry.POTIONS.register(id, sup);
    }

}
