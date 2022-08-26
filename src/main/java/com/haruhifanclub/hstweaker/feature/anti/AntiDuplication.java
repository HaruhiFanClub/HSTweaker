package com.haruhifanclub.hstweaker.feature.anti;

import org.auioc.mcmod.arnicalib.common.event.impl.PistonCheckPushableEvent;
import org.auioc.mcmod.arnicalib.utils.game.TagCreator;
import com.haruhifanclub.hstweaker.HSTweaker;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AntiDuplication {

    private static final TagKey<Block> NO_PUSHABLE_TAG = TagCreator.block(HSTweaker.id("no_pushable"));

    @SubscribeEvent
    public static void onPistonCheckPushable(final PistonCheckPushableEvent event) {
        if (event.getWorld().isClientSide()) return;
        if (event.getState().is(NO_PUSHABLE_TAG)) {
            event.setCanceled(true);
        }
    }

}
