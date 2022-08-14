package com.haruhifanclub.hstweaker.feature.world;

import static net.minecraft.commands.Commands.literal;
import org.auioc.mcmod.arnicalib.utils.game.LevelUtils;
import org.auioc.mcmod.arnicalib.utils.game.TextUtils;
import com.haruhifanclub.hstweaker.HSTweaker;
import com.haruhifanclub.hstweaker.api.world.AbstractHSTWorld;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;

public class HSTWorldCommands {

    public static final CommandNode<CommandSourceStack> NODE = literal("world").build();

    public static void register(final CommandNode<CommandSourceStack> parent) {
        HSTWorlds.getAll().forEach((key, hstw) -> hstw.createCommandNode().ifPresent((node) -> NODE.addChild(node)));
        parent.addChild(NODE);
    }

    private static final DynamicCommandExceptionType ALREADY_IN = new DynamicCommandExceptionType((hstw) -> HSTWorlds.createMessageP("_already_in", ((AbstractHSTWorld) hstw).getName()));
    private static final DynamicCommandExceptionType NOT_IN = new DynamicCommandExceptionType((hstw) -> HSTWorlds.createMessageP("_not_in", ((AbstractHSTWorld) hstw).getName()));

    public static CommandNode<CommandSourceStack> createNode(final AbstractHSTWorld hstw, boolean safeEnter) {
        final var node = literal(hstw.sid).requires((source) -> source.getEntity() instanceof ServerPlayer).build();

        node.addChild(literal("exit").executes(createExitHandler(hstw)).build());

        if (safeEnter) {
            Command<CommandSourceStack> enterWithConfirmation = createSafeEnterHandler(hstw);
            node.addChild(
                literal("enter").executes(enterWithConfirmation)
                    .then(literal("safe").executes(enterWithConfirmation))
                    .then(literal("unsafe").executes(createEnterHandler(hstw))).build()
            );
        } else {
            node.addChild(literal("enter").executes(createEnterHandler(hstw)).build());
        }

        return node;
    }

    private static Command<CommandSourceStack> createExitHandler(final AbstractHSTWorld hstw) {
        return (ctx) -> {
            var player = ctx.getSource().getPlayerOrException();

            if (!hstw.is(player.getLevel())) throw NOT_IN.create(hstw);

            var dim = LevelUtils.getLevel(player.getRespawnDimension());
            var pos = (player.getRespawnPosition() != null ? player.getRespawnPosition() : dim.getSharedSpawnPos());
            player.changeDimension(dim, LevelUtils.createSimpleTeleporter(pos));

            ctx.getSource().sendSuccess(HSTWorlds.createMessageP("_exit", hstw.getName()), false);

            return Command.SINGLE_SUCCESS;
        };
    }

    private static Command<CommandSourceStack> createEnterHandler(final AbstractHSTWorld hstw) {
        return (ctx) -> {
            var player = ctx.getSource().getPlayerOrException();

            if (hstw.is(player.getLevel())) throw ALREADY_IN.create(hstw);

            player.changeDimension(hstw.get(), LevelUtils.createSimpleTeleporter(hstw.entryPoint));

            ctx.getSource().sendSuccess(HSTWorlds.createMessageP("_enter", hstw.getName()), false);

            return Command.SINGLE_SUCCESS;
        };
    }

    private static Command<CommandSourceStack> createSafeEnterHandler(final AbstractHSTWorld hstw) {
        return (ctx) -> {
            var player = ctx.getSource().getPlayerOrException();

            if (hstw.is(player.getLevel())) throw ALREADY_IN.create(hstw);


            ctx.getSource().sendSuccess(hstw.createMessageP("notice"), false);
            ctx.getSource().sendSuccess(
                TextUtils.translatable(
                    HSTweaker.i18n("world._confirmation"),
                    hstw.getName(),
                    TextUtils.literal("[✔]")
                        .setStyle(
                            Style.EMPTY
                                .withBold(true)
                                .withColor(ChatFormatting.GREEN)
                                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hstweaker world " + hstw.sid + " enter unsafe"))
                        )
                ).withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD), false
            );

            return Command.SINGLE_SUCCESS;
        };
    }

}
