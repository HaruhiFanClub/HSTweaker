package com.haruhifanclub.hstweaker.feature.world.building;

import static net.minecraft.commands.Commands.literal;
import org.auioc.mcmod.arnicalib.utils.game.CommandFeedbackHelper;
import org.auioc.mcmod.arnicalib.utils.game.LevelUtils;
import org.auioc.mcmod.arnicalib.utils.game.TextUtils;
import com.haruhifanclub.hstweaker.feature.world.HSTWorlds;
import com.haruhifanclub.hstweaker.server.command.HSTCommands;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;

public class BuildingWorldCommands {

    public static final CommandNode<CommandSourceStack> NODE =
        literal("buildingworld")
            .requires((source) -> source.getEntity() instanceof ServerPlayer)
            .then(
                literal("enter").executes(BuildingWorldCommands::enterWithConfirmation)
                    .then(literal("safe").executes(BuildingWorldCommands::enterWithConfirmation))
                    .then(literal("unsafe").executes(BuildingWorldCommands::enter))
            )
            .then(literal("exit").executes(BuildingWorldCommands::exit))
            .build();

    private static final CommandFeedbackHelper CFH = HSTCommands.createCFH(HSTWorlds.BUILDING::i18n);

    private static final SimpleCommandExceptionType ALREADY_IN = new SimpleCommandExceptionType(CFH.createMessage("enter.already_in"));
    private static final SimpleCommandExceptionType NOT_IN = new SimpleCommandExceptionType(CFH.createMessage("exit.not_in"));

    private static int enterWithConfirmation(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        var player = ctx.getSource().getPlayerOrException();

        if (HSTWorlds.BUILDING.is(player.getLevel())) throw ALREADY_IN.create();

        CFH.sendSuccess(
            ctx, "enter_with_confirmation",
            TextUtils.translatable(HSTWorlds.BUILDING.i18n("notice")),
            TextUtils.literal("[✔]")
                .setStyle(
                    Style.EMPTY
                        .withBold(true)
                        .withColor(ChatFormatting.GREEN)
                        .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/hstweaker buildingworld enter unsafe"))
                )
        );

        return Command.SINGLE_SUCCESS;
    }

    private static int enter(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        var player = ctx.getSource().getPlayerOrException();

        if (HSTWorlds.BUILDING.is(player.getLevel())) throw ALREADY_IN.create();

        player.changeDimension(HSTWorlds.BUILDING.get(), LevelUtils.createSimpleTeleporter(HSTWorlds.BUILDING.entryPoint));

        CFH.sendSuccess(ctx, "enter");

        return Command.SINGLE_SUCCESS;
    }

    private static int exit(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        var player = ctx.getSource().getPlayerOrException();

        if (!HSTWorlds.BUILDING.is(player.getLevel())) throw NOT_IN.create();

        var dim = LevelUtils.getLevel(player.getRespawnDimension());
        var pos = (player.getRespawnPosition() != null ? player.getRespawnPosition() : dim.getSharedSpawnPos());
        player.changeDimension(dim, LevelUtils.createSimpleTeleporter(pos));

        CFH.sendSuccess(ctx, "exit");

        return Command.SINGLE_SUCCESS;
    }

}
