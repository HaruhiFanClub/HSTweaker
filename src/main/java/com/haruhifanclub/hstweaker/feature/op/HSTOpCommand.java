package com.haruhifanclub.hstweaker.feature.op;

import static net.minecraft.commands.Commands.literal;
import org.auioc.mcmod.arnicalib.utils.game.CommandUtils;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;

public class HSTOpCommand {

    public static final CommandNode<CommandSourceStack> NODE = literal("op").requires(CommandUtils.PERMISSION_LEVEL_3).build();

    public static void register(final CommandNode<CommandSourceStack> parent) {
        parent.addChild(NODE);
    }

}
