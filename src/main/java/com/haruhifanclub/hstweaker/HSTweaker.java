package com.haruhifanclub.hstweaker;

import org.auioc.mcmod.arnicalib.utils.game.TextUtils;
import com.haruhifanclub.hstweaker.feature.world.HSTWorldEventDispatcher;
import com.haruhifanclub.hstweaker.server.event.HSTEventHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.IExtensionPoint.DisplayTest;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;

@Mod(HSTweaker.MOD_ID)
public class HSTweaker {

    public static final String MOD_ID = "hstweaker";
    public static final String MOD_NAME = "HSTweaker";

    public HSTweaker() {
        ModLoadingContext.get().registerExtensionPoint(DisplayTest.class, () -> new DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (s, b) -> true));

        MinecraftForge.EVENT_BUS.register(HSTEventHandler.class);
        MinecraftForge.EVENT_BUS.register(HSTWorldEventDispatcher.class);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static String i18n(String key) {
        return MOD_ID + "." + key;
    }

    public static final MutableComponent MESSAGE_PREFIX = TextUtils.literal("[HST] ").withStyle(ChatFormatting.AQUA);

}

