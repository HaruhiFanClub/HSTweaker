package com.haruhifanclub.hstweaker.server.command;

import static net.minecraft.commands.Commands.literal;
import org.auioc.mcmod.arnicalib.common.command.impl.VersionCommand;
import com.haruhifanclub.hstweaker.HSTweaker;
import com.haruhifanclub.hstweaker.feature.op.HSTOpCommand;
import com.haruhifanclub.hstweaker.feature.world.HSTWorldCommands;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;

public final class HSTCommands {

    public static final CommandNode<CommandSourceStack> NODE = literal(HSTweaker.MOD_ID).build();

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        VersionCommand.addVersionNode(NODE, HSTweaker.class);
        HSTOpCommand.register(NODE);
        HSTWorldCommands.register(NODE);

        dispatcher.getRoot().addChild(NODE);
        dispatcher.register(literal("hst").redirect(NODE));
    }

}
