package net.danygames2014.whatsthis.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;

/**
 * You can implement IProbeInfoAccessor in your blocks or else you can use
 * this and register that to the ITheOneProbe. Note that TheOneProbe already
 * adds default providers which gives basic block information.
 */
public interface IProbeInfoProvider {

    /**
     * Return a unique ID (usually combined with the modid) to identify this provider.
     */
    String getID();

    /**
     * Add information for the probe info for the given block. This is always called
     * server side.
     * The given probeInfo object represents a vertical layout. So adding elements to that
     * will cause them to be grouped vertically.
     */
    void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState state, IProbeHitData data);
}
