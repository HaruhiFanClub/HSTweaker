package com.haruhifanclub.hstweaker;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.auioc.mcmod.arnicalib.utils.LogUtil;
import org.auioc.mcmod.arnicalib.utils.game.TextUtils;
import org.auioc.mcmod.arnicalib.utils.java.VersionUtils;
import com.haruhifanclub.hstweaker.server.event.HSTEventHandlers;
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

    public static final String MAIN_VERSION;
    public static final String FULL_VERSION;

    public static final Logger LOGGER = LogUtil.getLogger(MOD_NAME);
    private static final Marker CORE = LogUtil.getMarker("CORE");

    public HSTweaker() {
        ModLoadingContext.get().registerExtensionPoint(DisplayTest.class, () -> new DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (s, b) -> true));

        HSTEventHandlers.register(MinecraftForge.EVENT_BUS);
    }

    static {
        Pair<String, String> version = VersionUtils.getModVersion(HSTweaker.class);
        MAIN_VERSION = version.getLeft();
        FULL_VERSION = version.getRight();
        LOGGER.info(CORE, "Version: " + MAIN_VERSION + " (" + FULL_VERSION + ")");
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static String i18n(String key) {
        return MOD_ID + "." + key;
    }

    public static final MutableComponent MESSAGE_PREFIX = TextUtils.literal("[HST] ").withStyle(ChatFormatting.AQUA);

}

