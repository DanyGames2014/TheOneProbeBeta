package net.danygames2014.whatsthis.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

/**
 * Implement this interface if you want a custom display for your own entities instead of the standard
 * display.
 */
public interface IEntityDisplayOverride {

    /**
     * This function returns true if you handled the probe info yourselves and TOP doesn't have to add its
     * own info.
     */
    boolean overrideStandardInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, Entity entity, IProbeHitEntityData data);

}
