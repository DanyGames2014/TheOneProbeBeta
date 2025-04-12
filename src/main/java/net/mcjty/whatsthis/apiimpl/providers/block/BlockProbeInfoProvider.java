package net.mcjty.whatsthis.apiimpl.providers.block;

import net.mcjty.whatsthis.WhatsThis;
import net.mcjty.whatsthis.api.*;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;

public class BlockProbeInfoProvider implements IProbeInfoProvider {

    @Override
    public String getID() {
        return WhatsThis.NAMESPACE.id("block").toString();
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState blockState, IProbeHitData data) {
        Block block = blockState.getBlock();
        if (block instanceof IProbeInfoAccessor probeInfoAccessor) {
            probeInfoAccessor.addProbeInfo(mode, probeInfo, player, world, blockState, data);
        }
    }
}
