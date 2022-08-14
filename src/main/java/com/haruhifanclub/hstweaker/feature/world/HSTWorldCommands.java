package com.haruhifanclub.hstweaker.feature.world;

import static net.minecraft.commands.Commands.literal;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;

public class HSTWorldCommands {

    public static final CommandNode<CommandSourceStack> NODE = literal("world").build();

    public static void register(CommandNode<CommandSourceStack> parent) {
        HSTWorlds.getAll().forEach((key, hstw) -> hstw.createCommandNode().ifPresent((node) -> NODE.addChild(node)));
        parent.addChild(NODE);
    }

}
