package com.haruhifanclub.hstweaker.mixin.server;

import java.util.Collection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.haruhifanclub.hstweaker.server.event.impl.MsgCommandEvent;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.MsgCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;

@Mixin(value = MsgCommand.class)
public class MixinMsgCommand {

    // @org.spongepowered.asm.mixin.Debug(export = true, print = true)
    @Inject(
        method = "Lnet/minecraft/server/commands/MsgCommand;sendMessage(Lnet/minecraft/commands/CommandSourceStack;Ljava/util/Collection;Lnet/minecraft/network/chat/Component;)I",
        at = @At(value = "RETURN"),
        require = 1,
        allow = 1
    )
    private static void onSendMessage(CommandSourceStack p_138065_, Collection<ServerPlayer> p_138066_, Component p_138067_, CallbackInfoReturnable<Boolean> cir) {
        MinecraftForge.EVENT_BUS.post(new MsgCommandEvent(p_138065_, p_138066_, p_138067_));
    }

}
