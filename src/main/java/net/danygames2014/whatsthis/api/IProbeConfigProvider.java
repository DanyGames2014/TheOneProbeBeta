package net.danygames2014.whatsthis.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;

/**
 * Implement this interface if you want to override the default
 * probe config for some of your blocks or entities.
 */
public interface IProbeConfigProvider {

    /**
     * Possibly override the config for this entity. You can make modifications to the given 'config' which starts
     * from default.
     */
    void getProbeConfig(IProbeConfig config, PlayerEntity player, World world, Entity entity, IProbeHitEntityData data);

    /**
     * Possibly override the config for this block. You can make modifications to the given 'config' which starts
     * from default.
     */
    void getProbeConfig(IProbeConfig config, PlayerEntity player, World world, BlockState blockState, IProbeHitData data);

}
