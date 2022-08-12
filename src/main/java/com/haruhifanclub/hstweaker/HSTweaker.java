package com.haruhifanclub.hstweaker;

import net.minecraft.resources.ResourceLocation;
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
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static String i18n(String key) {
        return MOD_ID + "." + key;
    }

}

