package com.haruhifanclub.hstweaker.feature.itemban;

import org.auioc.mcmod.arnicalib.utils.game.MessageHelper;
import com.haruhifanclub.hstweaker.HSTweaker;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemBan {

    private static final TagKey<Item> BANNED_ITEM_TAG = TagKey.create(Registry.ITEM_REGISTRY, HSTweaker.id("banned"));
    protected static final MessageHelper MSGH = new MessageHelper(HSTweaker.MESSAGE_PREFIX, (key) -> HSTweaker.i18n("itemban." + key));

    protected static boolean isBanned(ServerPlayer player, ItemStack stack) {
        // if (PlayerUtils.isOp(player)) return false;
        if (stack.is(BANNED_ITEM_TAG)) {
            player.sendMessage(MSGH.create("ban", true, stack.getDisplayName()).withStyle(ChatFormatting.RED), Util.NIL_UUID);
            return true;
        }
        return false;
    }


}
