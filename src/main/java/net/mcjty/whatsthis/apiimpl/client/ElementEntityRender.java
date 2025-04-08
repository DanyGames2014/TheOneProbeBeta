package net.mcjty.whatsthis.apiimpl.client;

import net.mcjty.whatsthis.api.IEntityStyle;
import net.mcjty.whatsthis.rendering.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;

public class ElementEntityRender {

    public static void renderPlayer(String entityName, Integer playerID, IEntityStyle style, int x, int y) {
//        Entity entity = Minecraft.getMinecraft().world.getEntityByID(playerID);
//        if (entity != null) {
//            renderEntity(style, x, y, entity);
//        }
    }

    public static void render(String entityName, NbtCompound entityNBT, IEntityStyle style, int x, int y) {
//        if (entityName != null && !entityName.isEmpty()) {
//            Entity entity = null;
//            if (entityNBT != null) {
//                entity = EntityList.createEntityFromNBT(entityNBT, Minecraft.getMinecraft().world);
//            } else {
//                String fixed = fixEntityId(entityName);
//                EntityEntry value = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(fixed));
//                if (value != null) {
//                    entity = value.newInstance(Minecraft.getMinecraft().world);
//                }
//            }
//            if (entity != null) {
//                renderEntity(style, x, y, entity);
//            }
//        }
    }



    private static void renderEntity(IEntityStyle style, int x, int y, Entity entity) {
        float height = entity.height;
        height = (float) ((height - 1) * .7 + 1);
        float s = style.getScale() * ((style.getHeight() * 14.0f / 25) / height);

        RenderHelper.renderEntity(entity, x, y, s);
    }

}
