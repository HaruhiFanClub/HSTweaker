package com.haruhifanclub.hstweaker.feature.op;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;
import org.auioc.mcmod.arnicalib.utils.game.CommandUtils;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.level.Level;

public class HSTOpCommand {

    public static final CommandNode<CommandSourceStack> NODE = literal("op").requires(CommandUtils.PERMISSION_LEVEL_3)
        .then(
            literal("removeRespawnPosition")
                .then(
                    argument("players", EntityArgument.players())
                        .executes((ctx) -> {
                            var players = EntityArgument.getPlayers(ctx, "players");
                            for (var player : players) player.setRespawnPosition(Level.OVERWORLD, null, 0.0F, false, false);
                            return players.size();
                        })
                )
        )
        .build();

    public static void register(final CommandNode<CommandSourceStack> parent) {
        parent.addChild(NODE);
    }

}
