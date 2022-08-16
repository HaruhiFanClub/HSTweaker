package com.haruhifanclub.hstweaker.api.world;

import org.auioc.mcmod.arnicalib.utils.game.LevelUtils;
import org.auioc.mcmod.arnicalib.utils.game.MessageHelper;
import com.haruhifanclub.hstweaker.HSTweaker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractHSTWorld implements IHSTWorld {

    public static final BlockPos DEFAULT_ENTRY_POINT = new BlockPos(0, 65, 0);

    public final ResourceKey<Level> key;
    public final BlockPos entryPoint;
    public final MessageHelper msgh;

    public AbstractHSTWorld(ResourceKey<Level> key, BlockPos entryPoint) {
        this.key = key;
        this.entryPoint = entryPoint;
        var messageKey = "world." + this.getPath() + ".";
        this.msgh = new MessageHelper(HSTweaker.MESSAGE_PREFIX, (k) -> HSTweaker.i18n(messageKey + k));
    }

    public AbstractHSTWorld(String name, BlockPos entryPoint) {
        this(LevelUtils.createKey(HSTweaker.id(name)), entryPoint);
    }

    public AbstractHSTWorld(String name) {
        this(name, DEFAULT_ENTRY_POINT);
    }

    public AbstractHSTWorld(ResourceKey<Level> key) {
        this(key, DEFAULT_ENTRY_POINT);
    }

    @Override
    public ServerLevel get() {
        return LevelUtils.getLevel(this.key);
    }

    public boolean is(ResourceKey<Level> dim) {
        return dim.equals(this.key);
    }

    public boolean is(Level level) {
        return level.dimension().equals(this.key);
    }

    protected void createEntryPlatform(ServerLevel level) {
        MutableBlockPos pos = this.entryPoint.mutable();
        for (int x = -2; x <= 2; ++x) {
            for (int z = -2; z <= 2; ++z) {
                for (int y = -1; y < 3; ++y) {
                    BlockState block = y > -1 ? Blocks.AIR.defaultBlockState() : ((x == 0 && z == 0) ? Blocks.BEDROCK.defaultBlockState() : Blocks.STONE.defaultBlockState());
                    level.setBlockAndUpdate(pos.set(this.entryPoint).move(x, y, z), block);
                }
            }
        }
    }

    public ResourceLocation getLevelKey() {
        return this.key.location();
    }

    public MutableComponent getDisplayName() {
        return this.msgh.create("name", false);
    }

    public String getPath() {
        return this.getLevelKey().getPath().replace("/", ".");
    }

}
