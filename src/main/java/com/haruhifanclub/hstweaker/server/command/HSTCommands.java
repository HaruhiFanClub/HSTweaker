package com.haruhifanclub.hstweaker.server.command;

import static net.minecraft.commands.Commands.literal;
import org.auioc.mcmod.arnicalib.utils.game.CommandFeedbackHelper;
import org.auioc.mcmod.arnicalib.utils.game.TextUtils;
import com.haruhifanclub.hstweaker.HSTweaker;
import com.haruhifanclub.hstweaker.feature.buildingworld.BuildingWorldCommands;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.MutableComponent;

public final class HSTCommands {

    public static final CommandNode<CommandSourceStack> NODE = literal(HSTweaker.MOD_ID).build();

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        NODE.addChild(BuildingWorldCommands.NODE);

        dispatcher.getRoot().addChild(NODE);
    }

    public static final MutableComponent CFH_PREFIX = TextUtils.getStringText("[HST] ").withStyle(ChatFormatting.AQUA);

    public static CommandFeedbackHelper createCFH(String keyPrefix) {
        return new CommandFeedbackHelper(CFH_PREFIX, (key) -> HSTweaker.i18n(keyPrefix + "." + key));
    }

}
