package net.mcjty.whatsthis.apiimpl;

import net.mcjty.whatsthis.api.IProbeHitEntityData;
import net.minecraft.util.math.Vec3d;

public class ProbeHitEntityData implements IProbeHitEntityData {

    private final Vec3d hitVec;

    public ProbeHitEntityData(Vec3d hitVec) {
        this.hitVec = hitVec;
    }

    @Override
    public Vec3d getHitVec() {
        return hitVec;
    }
}
