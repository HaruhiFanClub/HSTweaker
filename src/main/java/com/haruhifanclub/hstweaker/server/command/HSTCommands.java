package com.haruhifanclub.hstweaker.server.command;

import static net.minecraft.commands.Commands.literal;
import java.util.function.Function;
import org.auioc.mcmod.arnicalib.utils.game.CommandFeedbackHelper;
import com.haruhifanclub.hstweaker.HSTweaker;
import com.haruhifanclub.hstweaker.feature.buildingworld.BuildingWorldCommands;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;

public final class HSTCommands {

    public static final CommandNode<CommandSourceStack> NODE = literal(HSTweaker.MOD_ID).build();

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        NODE.addChild(BuildingWorldCommands.NODE);

        dispatcher.getRoot().addChild(NODE);
        dispatcher.register(literal("hst").redirect(NODE));
    }

    public static CommandFeedbackHelper createCFH(String keyPrefix) {
        return new CommandFeedbackHelper("HST", (key) -> HSTweaker.i18n(keyPrefix + "." + key));
    }

    public static CommandFeedbackHelper createCFH(Function<String, String> i18n) {
        return new CommandFeedbackHelper("HST", i18n);
    }

}
