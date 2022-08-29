package com.haruhifanclub.hstweaker.feature.itemban;

import static com.haruhifanclub.hstweaker.HSTweaker.LOGGER;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import org.auioc.mcmod.arnicalib.utils.game.MessageHelper;
import org.auioc.mcmod.arnicalib.utils.game.PlayerUtils;
import com.haruhifanclub.hstweaker.HSTweaker;
import net.minecraft.Util;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemBan {

    public static final Marker MARKER = LogUtil.getMarker(ItemBan.class);
    protected static final MessageHelper MSGH = new MessageHelper(HSTweaker.MESSAGE_PREFIX, (key) -> HSTweaker.i18n("itemban." + key));

    protected static final List<ItemPredicate> BANNED_ITEMS = new ArrayList<ItemPredicate>();

    protected static boolean shouldBan(ServerPlayer player, ItemStack stack) {
        if (PlayerUtils.isOp(player)) return false;
        for (var predicate : BANNED_ITEMS) {
            if (predicate.matches(stack)) {
                player.sendMessage(MSGH.create("ban", new Object[] {stack.getDisplayName()}, true), Util.NIL_UUID);
                LOGGER.warn(
                    MARKER,
                    "Player {}({}) has banned item {}{}",
                    player.getGameProfile().getName(), player.getStringUUID(), ForgeRegistries.ITEMS.getKey(stack.getItem()).toString(), stack.hasTag() ? stack.getTag().toString() : ""
                );
                return true;
            }
        }
        return false;
    }

}
