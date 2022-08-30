package com.haruhifanclub.hstweaker.server.event.impl;

import java.util.Collection;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

public class MsgCommandEvent extends Event {

    private final CommandSourceStack source;
    private final List<ServerPlayer> targets;
    private final Component message;

    public MsgCommandEvent(CommandSourceStack source, Collection<ServerPlayer> targets, Component message) {
        this.source = source;
        this.targets = (List<ServerPlayer>) targets;
        this.message = message;
    }

    public CommandSourceStack getSource() {
        return this.source;
    }

    public List<ServerPlayer> getTargets() {
        return this.targets;
    }

    public Component getMessage() {
        return this.message;
    }

}
