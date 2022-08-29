package com.haruhifanclub.hstweaker.feature.itemban;

import java.util.Map;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public class BannedItemsLoader extends SimpleJsonResourceReloadListener {

    public BannedItemsLoader() {
        super(new GsonBuilder().create(), "banned_items");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller filter) {
        ItemBan.BANNED_ITEMS.clear();
        map.forEach((id, json) -> ItemBan.BANNED_ITEMS.add(ItemPredicate.fromJson(json)));
    }

}
