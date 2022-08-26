package com.haruhifanclub.hstweaker.feature.op;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;
import static org.auioc.mcmod.arnicalib.utils.game.command.CommandHandlers.playerSelfWithMainHand;
import static org.auioc.mcmod.arnicalib.utils.game.command.CommandNodeUtils.getLastLiteral;
import java.util.ArrayList;
import org.apache.commons.lang3.function.FailableConsumer;
import org.auioc.mcmod.arnicalib.utils.game.MessageHelper;
import org.auioc.mcmod.arnicalib.utils.game.PlayerUtils;
import org.auioc.mcmod.arnicalib.utils.game.TextUtils;
import org.auioc.mcmod.arnicalib.utils.game.command.CommandSourceUtils;
import com.haruhifanclub.hstweaker.HSTweaker;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;

public class HSTOpCommand {

    protected static final MessageHelper MSGH = new MessageHelper(HSTweaker.MESSAGE_PREFIX, (key) -> HSTweaker.i18n("op." + key));

    public static final CommandNode<CommandSourceStack> NODE = literal("op").requires(CommandSourceUtils.PERMISSION_LEVEL_3)
        .then(literal("removeRespawnPosition").then(nodeWithPlayersArgument((player) -> player.setRespawnPosition(Level.OVERWORLD, null, 0.0F, false, false))))
        .then(
            literal("playTime")
                .then(
                    argument("players", EntityArgument.players())
                        .then(literal("get").executes(HSTOpCommand::getPlayTime))
                        .then(literal("set").then(argument("time", IntegerArgumentType.integer(0)).executes(HSTOpCommand::setPlayTime)))
                )
        )
        .then(
            literal("ability")
                .then(literal("invulnerable").executes(HSTOpCommand::switchAbility))
                .then(literal("mayfly").executes(HSTOpCommand::switchAbility))
                .then(literal("instabuild").executes(HSTOpCommand::switchAbility))
        )
        .then(gameModeNode())
        .then(
            literal("item")
                .then(literal("duplicate").executes((ctx) -> playerSelfWithMainHand(ctx, (player, stack) -> PlayerUtils.giveItem(player, stack.copy()))))
                .then(literal("fillStack").executes((ctx) -> playerSelfWithMainHand(ctx, (player, stack) -> stack.setCount(stack.getMaxStackSize()))))
                .then(literal("fix").executes((ctx) -> playerSelfWithMainHand(ctx, (player, stack) -> {
                    if (stack.isDamaged()) stack.setDamageValue(0);
                })))
        )
        .build();

    public static void register(final CommandNode<CommandSourceStack> parent) {
        parent.addChild(NODE);
    }


    private static int forEachPlayer(CommandContext<CommandSourceStack> ctx, FailableConsumer<ServerPlayer, CommandSyntaxException> action) throws CommandSyntaxException {
        var players = EntityArgument.getPlayers(ctx, "players");
        for (var player : players) action.accept(player);
        return players.size();
    }

    private static RequiredArgumentBuilder<CommandSourceStack, EntitySelector> nodeWithPlayersArgument(FailableConsumer<ServerPlayer, CommandSyntaxException> action) {
        return argument("players", EntityArgument.players()).executes((ctx) -> forEachPlayer(ctx, action));
    }

    private static int getPlayTime(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        var stat = Stats.CUSTOM.get(Stats.PLAY_TIME);
        var message = TextUtils.empty();
        int result = forEachPlayer(
            ctx,
            (player) -> message
                .append("\n  ")
                .append(player.getDisplayName())
                .append(": ")
                .append(stat.format(player.getStats().getValue(stat)))
        );
        if (result > 0) ctx.getSource().sendSuccess(MSGH.create("play_time.get", true).append(message), false);
        return result;
    }

    private static int setPlayTime(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        int time = IntegerArgumentType.getInteger(ctx, "time");
        var stat = Stats.CUSTOM.get(Stats.PLAY_TIME);
        var playerNames = new ArrayList<Component>();
        int result = forEachPlayer(
            ctx,
            (player) -> {
                player.getStats().setValue(player, stat, time);
                playerNames.add(player.getDisplayName());
            }
        );
        if (result > 0) ctx.getSource().sendSuccess(MSGH.create("play_time.set", new Object[] {TextUtils.join(playerNames), stat.format(time)}, true), false);
        return result;
    }

    private static int switchAbility(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        final var abilities = ctx.getSource().getPlayerOrException().getAbilities();

        switch (getLastLiteral(ctx.getNodes())) {
            case "invulnerable" -> abilities.invulnerable = !abilities.invulnerable;
            case "instabuild" -> abilities.instabuild = !abilities.instabuild;
            case "mayfly" -> abilities.mayfly = !abilities.mayfly;
        }

        return Command.SINGLE_SUCCESS;
    }

    private static LiteralArgumentBuilder<CommandSourceStack> gameModeNode() {
        var node = literal("gamemode");

        for (var mode : GameType.values()) {
            node.then(
                literal(mode.getName())
                    .executes(
                        (ctx) -> ctx.getSource().getPlayerOrException()
                            .setGameMode(GameType.byName(getLastLiteral(ctx.getNodes()))) ? 1 : 0
                    )
            );
        }

        return node;
    }

}
