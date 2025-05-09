package net.danygames2014.whatsthis.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.modificationstation.stationapi.api.util.math.Direction;

import javax.annotation.Nullable;

/**
 * Access information about where the probe hit the block
 */
public interface IProbeHitData {

    BlockPos getPos();

    Vec3d getHitVec();

    Direction getSideHit();

    /**
     * Access the client-side result of getPickBlock() for the given block. That way
     * you don't have to call this server side because that can sometimes be
     * problematic
     *
     * @return the picked block or null
     */
    @Nullable
    ItemStack getPickBlock();
}
