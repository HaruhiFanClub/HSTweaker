package com.haruhifanclub.hstweaker.feature.antiduplication;

import org.auioc.mcmod.arnicalib.common.event.impl.PistonCheckPushableEvent;
import com.haruhifanclub.hstweaker.HSTweaker;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AntiDuplicationEventHandler {

    private static final TagKey<Block> NO_PUSHABLE_TAG = TagKey.create(Registry.BLOCK_REGISTRY, HSTweaker.id("no_pushable"));

    @SubscribeEvent
    public static void onPistonCheckPushable(final PistonCheckPushableEvent event) {
        if (event.getWorld().isClientSide()) return;
        if (event.getState().is(NO_PUSHABLE_TAG)) {
            event.setCanceled(true);
        }
    }

}
