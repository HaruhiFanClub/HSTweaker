package com.haruhifanclub.hstweaker.feature.op;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;
import org.apache.commons.lang3.function.FailableConsumer;
import org.auioc.mcmod.arnicalib.utils.game.CommandUtils;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.Level;

public class HSTOpCommand {

    public static final CommandNode<CommandSourceStack> NODE = literal("op").requires(CommandUtils.PERMISSION_LEVEL_3)
        .then(literal("removeRespawnPosition").then(nodeWithPlayersArgument((player) -> player.setRespawnPosition(Level.OVERWORLD, null, 0.0F, false, false))))
        .then(literal("resetPlayTime").then(nodeWithPlayersArgument((player) -> player.getStats().setValue(player, Stats.CUSTOM.get(Stats.PLAY_TIME), 0))))
        .build();

    public static void register(final CommandNode<CommandSourceStack> parent) {
        parent.addChild(NODE);
    }

    private static RequiredArgumentBuilder<CommandSourceStack, EntitySelector> nodeWithPlayersArgument(FailableConsumer<ServerPlayer, CommandSyntaxException> action) {
        return argument("players", EntityArgument.players())
            .executes((ctx) -> {
                var players = EntityArgument.getPlayers(ctx, "players");
                for (var player : players) action.accept(player);
                return players.size();
            });
    }

}
