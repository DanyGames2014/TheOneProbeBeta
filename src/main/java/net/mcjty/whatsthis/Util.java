package net.mcjty.whatsthis;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class Util {
    public static Entity getEntity(World world, int entityId) {
        for (var entity : world.entities) {
            if (((Entity) entity).id == entityId) {
                return (Entity) entity;
            }
        }
        return null;
    }
}
