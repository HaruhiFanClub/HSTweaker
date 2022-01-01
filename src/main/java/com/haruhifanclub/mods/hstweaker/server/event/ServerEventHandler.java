package com.haruhifanclub.mods.hstweaker.server.event;

import org.auioc.mods.ahutils.server.event.impl.LivingEatAddEffectEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ServerEventHandler {

    @SubscribeEvent
    public static void onEatAddEffect(final LivingEatAddEffectEvent event) {
        Item food = event.getFood().getItem();
        if ((food == Items.ROTTEN_FLESH || food == Items.POISONOUS_POTATO) && event.getEffects().size() == 0) {
            event.getEffects().add(new MobEffectInstance(MobEffects.LUCK, 200, 0, false, false, true));
        }
    }

}
