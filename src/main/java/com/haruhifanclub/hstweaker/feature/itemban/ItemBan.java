package com.haruhifanclub.hstweaker.feature.itemban;

import static com.haruhifanclub.hstweaker.HSTweaker.LOGGER;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import org.auioc.mcmod.arnicalib.utils.game.MessageHelper;
import org.auioc.mcmod.arnicalib.utils.game.PlayerUtils;
import com.haruhifanclub.hstweaker.HSTweaker;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemBan {

    public static final Marker MARKER = LogUtil.getMarker(ItemBan.class);

    private static final TagKey<Item> BANNED_ITEM_TAG = TagKey.create(Registry.ITEM_REGISTRY, HSTweaker.id("banned"));
    protected static final MessageHelper MSGH = new MessageHelper(HSTweaker.MESSAGE_PREFIX, (key) -> HSTweaker.i18n("itemban." + key));

    protected static boolean shouldBan(ServerPlayer player, ItemStack stack) {
        if (PlayerUtils.isOp(player)) return false;
        if (stack.is(BANNED_ITEM_TAG)) {
            player.sendMessage(MSGH.create("ban", new Object[] {stack.getDisplayName()}, true), Util.NIL_UUID);
            LOGGER.warn(MARKER, "Player {} has banned item {}", player.getGameProfile().getName(), stack.getDescriptionId());
            return true;
        }
        return false;
    }


}
