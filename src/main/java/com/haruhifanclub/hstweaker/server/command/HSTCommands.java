package com.haruhifanclub.hstweaker.server.command;

import static net.minecraft.commands.Commands.literal;
import com.haruhifanclub.hstweaker.HSTweaker;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;

public final class HSTCommands {

    public static final CommandNode<CommandSourceStack> NODE = literal(HSTweaker.MOD_ID).build();

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {


        dispatcher.getRoot().addChild(NODE);
    }

}
