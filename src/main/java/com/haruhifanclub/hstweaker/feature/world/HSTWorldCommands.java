package com.haruhifanclub.hstweaker.feature.world;

import static net.minecraft.commands.Commands.literal;
import org.auioc.mcmod.arnicalib.api.game.command.BiCommandAction;
import org.auioc.mcmod.arnicalib.utils.game.LevelUtils;
import org.auioc.mcmod.arnicalib.utils.game.PlayerUtils;
import org.auioc.mcmod.arnicalib.utils.game.TextUtils;
import com.haruhifanclub.hstweaker.HSTweaker;
import com.haruhifanclub.hstweaker.api.world.AbstractHSTWorld;
import com.haruhifanclub.hstweaker.server.cooldown.HSTCooldowns;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;

public class HSTWorldCommands {

    public static final CommandNode<CommandSourceStack> NODE = literal("world").requires((source) -> source.getEntity() instanceof ServerPlayer).build();

    public static void register(final CommandNode<CommandSourceStack> parent) {
        HSTWorlds.getAll().forEach((key, hstw) -> hstw.createCommandNode().ifPresent((node) -> NODE.addChild(node)));
        parent.addChild(NODE);
    }

    private static final DynamicCommandExceptionType ALREADY_IN = new DynamicCommandExceptionType((hstw) -> HSTWorlds.createMessage("_already_in", ((AbstractHSTWorld) hstw).getDisplayName()));
    private static final DynamicCommandExceptionType NOT_IN = new DynamicCommandExceptionType((hstw) -> HSTWorlds.createMessage("_not_in", ((AbstractHSTWorld) hstw).getDisplayName()));
    private static final Dynamic2CommandExceptionType ON_ENTER_COOLDOWN =
        new Dynamic2CommandExceptionType((hstw, remaining) -> HSTWorlds.createMessage("_on_enter_cooldown", ((AbstractHSTWorld) hstw).getDisplayName(), remaining));

    public static CommandNode<CommandSourceStack> createNode(final AbstractHSTWorld hstw, boolean safeEnter, int enterCooldown) {
        final var node = literal(hstw.getPath()).build();

        node.addChild(literal("exit").executes(createExitHandler(hstw)).build());

        if (safeEnter) {
            Command<CommandSourceStack> enterWithConfirmation = createSafeEnterHandler(hstw);
            node.addChild(
                literal("enter").executes(enterWithConfirmation)
                    .then(literal("safe").executes(enterWithConfirmation))
                    .then(literal("unsafe").executes(createEnterHandler(hstw, enterCooldown))).build()
            );
        } else {
            node.addChild(literal("enter").executes(createEnterHandler(hstw, enterCooldown)).build());
        }

        return node;
    }

    public static CommandNode<CommandSourceStack> createNode(final AbstractHSTWorld hstw, boolean safeEnter) {
        return createNode(hstw, safeEnter, 0);
    }

    public static CommandNode<CommandSourceStack> createNode(final AbstractHSTWorld hstw) {
        return createNode(hstw, false, 0);
    }

    private static Command<CommandSourceStack> createExitHandler(final AbstractHSTWorld hstw) {
        return (ctx) -> {
            var player = ctx.getSource().getPlayerOrException();

            if (!hstw.is(player.getLevel())) throw NOT_IN.create(hstw);

            var dim = LevelUtils.getLevel(player.getRespawnDimension());
            var pos = (player.getRespawnPosition() != null ? player.getRespawnPosition() : dim.getSharedSpawnPos());
            player.changeDimension(dim, LevelUtils.createSimpleTeleporter(pos));

            ctx.getSource().sendSuccess(HSTWorlds.createMessage("_exit", hstw.getDisplayName()), false);

            return Command.SINGLE_SUCCESS;
        };
    }

    private static Command<CommandSourceStack> _createEnterHandler(final AbstractHSTWorld hstw, int enterCooldown, BiCommandAction.SingleSuccess<ServerPlayer, CommandSourceStack> action) {
        return (ctx) -> {
            var player = ctx.getSource().getPlayerOrException();

            if (hstw.is(player.getLevel())) throw ALREADY_IN.create(hstw);

            if (enterCooldown > 0 && !PlayerUtils.isOp(player)) {
                int r = getEnterCooldownRemaining(player, hstw);
                if (r > 0) throw ON_ENTER_COOLDOWN.create(hstw, (double) r / 20.0D);
            }

            action.accept(player, ctx.getSource());

            if (enterCooldown > 0 && !PlayerUtils.isOp(player)) {
                addEnterCooldown(player, hstw, enterCooldown);
            }

            return Command.SINGLE_SUCCESS;
        };
    }

    private static Command<CommandSourceStack> createEnterHandler(final AbstractHSTWorld hstw, int enterCooldown) {
        return _createEnterHandler(hstw, enterCooldown, (player, source) -> {
            player.changeDimension(hstw.get(), LevelUtils.createSimpleTeleporter(hstw.entryPoint));
            source.sendSuccess(HSTWorlds.createMessage("_enter", hstw.getDisplayName()), false);
        });
    }

    private static Command<CommandSourceStack> createSafeEnterHandler(final AbstractHSTWorld hstw) {
        return _createEnterHandler(hstw, 0, (player, source) -> {
            source.sendSuccess(hstw.msgh.create("notice", true), false);
            source.sendSuccess(
                TextUtils.translatable(
                    HSTweaker.i18n("world._confirmation"),
                    hstw.getDisplayName(),
                    TextUtils.literal("[✔]")
                        .setStyle(
                            Style.EMPTY
                                .withBold(true)
                                .withColor(ChatFormatting.GREEN)
                                .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hstweaker world " + hstw.getPath() + " enter unsafe"))
                        )
                ).withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD), false
            );
        });
    }

    private static void addEnterCooldown(ServerPlayer player, AbstractHSTWorld hstw, int ticks) {
        HSTCooldowns.add(getCooldownId(player, hstw), ticks);
    }

    private static int getEnterCooldownRemaining(ServerPlayer player, AbstractHSTWorld hstw) {
        return HSTCooldowns.getRemainingTicks(getCooldownId(player, hstw));
    }

    private static String getCooldownId(ServerPlayer player, AbstractHSTWorld hstw) {
        return "hstwCommandEnter." + hstw.getPath() + "." + player.getStringUUID();
    }

}
