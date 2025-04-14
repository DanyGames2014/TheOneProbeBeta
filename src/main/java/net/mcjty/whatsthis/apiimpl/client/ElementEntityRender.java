package net.mcjty.whatsthis.apiimpl.client;

import net.mcjty.whatsthis.Util;
import net.mcjty.whatsthis.api.IEntityStyle;
import net.mcjty.whatsthis.rendering.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.nbt.NbtCompound;

public class ElementEntityRender {

    public static void renderPlayer(String entityName, Integer playerID, IEntityStyle style, int x, int y) {
        Entity entity = Util.getEntity(Minecraft.INSTANCE.world, playerID);
        if (entity != null) {
            renderEntity(style, x, y, entity);
        }
    }

    public static void render(String entityName, NbtCompound entityNbt, IEntityStyle style, int x, int y) {
        if (entityName != null && !entityName.isEmpty()) {
            Entity entity;

            entity = EntityRegistry.create(entityName, Minecraft.INSTANCE.world);

            if (entityNbt != null) {
                entity.read(entityNbt);
            }

            if (entity != null) {
                renderEntity(style, x, y, entity);
            }
        }
    }


    private static void renderEntity(IEntityStyle style, int x, int y, Entity entity) {
        float height = entity.height;
        height = (float) ((height - 1) * .7 + 1);
        float s = style.getScale() * ((style.getHeight() * 14.0f / 25) / height);

        RenderHelper.renderEntity(entity, x, y, s);
    }

}
