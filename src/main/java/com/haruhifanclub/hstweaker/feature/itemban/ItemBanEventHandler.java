package com.haruhifanclub.hstweaker.feature.itemban;

import org.auioc.mcmod.arnicalib.common.event.impl.ItemInventoryTickEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ItemBanEventHandler {

    @SubscribeEvent
    public static void onSelectedItemInventoryTick(final ItemInventoryTickEvent.Selected event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            var stack = event.getItemStack();
            if (ItemBan.isBanned(player, stack)) {
                stack.setCount(0);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onContainerClose(final PlayerContainerEvent.Close event) {
        if (event.getPlayer() instanceof ServerPlayer player) {
            if (event.getContainer() instanceof InventoryMenu) {
                Inventory inv = player.getInventory();
                for (int i = 0, s = inv.getContainerSize(); i < s; ++i) {
                    if (ItemBan.isBanned(player, inv.getItem(i))) {
                        inv.setItem(i, ItemStack.EMPTY);
                    }
                }
            }
        }
    }

}
