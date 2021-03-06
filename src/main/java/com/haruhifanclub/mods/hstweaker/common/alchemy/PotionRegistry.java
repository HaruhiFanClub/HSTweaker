package com.haruhifanclub.mods.hstweaker.common.alchemy;

import com.haruhifanclub.mods.hstweaker.HSTweaker;
import com.haruhifanclub.mods.hstweaker.common.alchemy.impl.LuckPotion;
import com.haruhifanclub.mods.hstweaker.common.alchemy.impl.UnluckPotion;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PotionRegistry {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, HSTweaker.MOD_ID);

    @SubscribeEvent
    public static void onSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(PotionRegistry::registerBrewingRecipes);
    }

    public static final RegistryObject<Potion> UNLUCK = UnluckPotion.Common.register();
    public static final RegistryObject<Potion> LONG_UNLUCK = UnluckPotion.Long.register();
    public static final RegistryObject<Potion> STRONG_UNLUCK = UnluckPotion.Strong.register();
    public static final RegistryObject<Potion> LONG_LUCK = LuckPotion.Long.register();
    public static final RegistryObject<Potion> STRONG_LUCK = LuckPotion.Strong.register();

    private static void registerBrewingRecipes() {
        UnluckPotion.Common.registerBrewingRecipe();
        UnluckPotion.Long.registerBrewingRecipe();
        UnluckPotion.Strong.registerBrewingRecipe();
        LuckPotion.Long.registerBrewingRecipe();
        LuckPotion.Strong.registerBrewingRecipe();
    }

}
