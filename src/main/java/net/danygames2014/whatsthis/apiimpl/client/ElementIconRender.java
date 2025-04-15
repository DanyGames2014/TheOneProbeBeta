package net.danygames2014.whatsthis.apiimpl.client;

import net.danygames2014.whatsthis.rendering.RenderHelper;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class ElementIconRender {

    public static void render(String icon, int x, int y, int w, int h, int u, int v, int txtw, int txth) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (icon == null) {
            return;
        }
        
        int textureId = Minecraft.INSTANCE.textureManager.getTextureId(icon);
        Minecraft.INSTANCE.textureManager.bindTexture(textureId);
        RenderHelper.drawTexturedModalRect(x, y, u, v, w, h, txtw, txth);
    }
}
