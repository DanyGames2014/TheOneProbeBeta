package net.mcjty.whatsthis.apiimpl.providers;

import net.mcjty.whatsthis.WhatsThis;
import net.mcjty.whatsthis.api.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class EntityProbeInfoProvider implements IProbeInfoEntityProvider {

    @Override
    public String getID() {
        return WhatsThis.NAMESPACE.id("entity_entity").toString();
    }

    @Override
    public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, Entity entity, IProbeHitEntityData data) {
        if (entity instanceof IProbeInfoEntityAccessor probeInfoEntityAccessor) {
            probeInfoEntityAccessor.addProbeInfo(mode, probeInfo, player, world, entity, data);
        }
    }
}
