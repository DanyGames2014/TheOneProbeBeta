package net.danygames2014.whatsthis.rendering;

import net.danygames2014.whatsthis.WhatsThis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.platform.Lighting;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

import java.awt.*;

@SuppressWarnings({"DuplicatedCode", "JavaExistingMethodCanBeUsed", "UnusedReturnValue"})
public class RenderHelper {
    public static float rot = 0.0f;

    public static void enableLighting() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_LIGHTING);
        //wGL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void enableItemLighting() {
        enableLighting();
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glPushMatrix();
        GL11.glRotatef(120F, 1.0F, 0.0F, 0.0F);
        Lighting.turnOn();
        GL11.glPopMatrix();
    }

    public static void disableLighting() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        //GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public static void disableItemLighting() {
        disableLighting();
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        Lighting.turnOff();
    }

    public static void enableStandardItemLighting() {
        Lighting.turnOn();
    }

    public static void disableStandardItemLighting() {
        Lighting.turnOff();
    }

    public static void renderEntity(Entity entity, int xPos, int yPos, float scale) {
        GL11.glPushMatrix();
        GL11.glColor3f(1f, 1f, 1f);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef(xPos + 8, yPos + 32, 50F);
        GL11.glScalef(-scale, scale, scale);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
        enableStandardItemLighting();
        GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(rot, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(0.0F, 1.0F, 0.0F, 0.0F);
//        entity.renderYawOffset = entity.rotationYaw = entity.prevRotationYaw = entity.prevRotationYawHead = entity.rotationYawHead = 0;//this.rotateTurret;
        entity.pitch = 0.0F;
        GL11.glTranslatef(0.0F, entity.getEyeHeight(), 0.0F);
        EntityRenderDispatcher.INSTANCE.pitch = 180F;

        try {
            EntityRenderDispatcher.INSTANCE.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        } catch (Exception e) {
            WhatsThis.LOGGER.error("Error rendering entity!", e);
        }

        GL11.glPopMatrix();
        disableStandardItemLighting();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_COLOR_MATERIAL);
        GL13.glActiveTexture(GL13.GL_TEXTURE1); // lightmapTexUnit
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL13.glActiveTexture(GL13.GL_TEXTURE0); // defaultTexUnit
    }

    public static boolean renderItemStackWithCount(Minecraft mc, ItemRenderer itemRender, ItemStack stack, int xo, int yo, boolean highlight) {
        if (stack.count <= 1) {
            return renderItemStack(mc, itemRender, stack, xo, yo, "", highlight);
        } else {
            return renderItemStack(mc, itemRender, stack, xo, yo, "" + stack.count, highlight);
        }
    }

    public static boolean renderItemStack(Minecraft minecraft, ItemRenderer itemRenderer, ItemStack stack, int x, int y, String txt, boolean highlight) {
        if (stack == null || stack.getItem() == null) {
            return false;
        }

        if (highlight) {
            GL11.glDisable(GL11.GL_LIGHTING);
            drawVerticalGradientRect(x, y, x + 16, y + 16, 0x80ffffff, 0xffffffff);
        }

        enableItemLighting();
        itemRenderer.renderGuiItem(minecraft.textRenderer, minecraft.textureManager, stack, x, y);
        itemRenderer.renderGuiItemDecoration(minecraft.textRenderer, minecraft.textureManager, stack, x, y);
        disableItemLighting();
        GL11.glEnable(GL11.GL_BLEND);
        return true;
    }

    public static boolean renderItemStack(Minecraft minecraft, ItemRenderer itemRenderer, ItemStack stack, int x, int y, String txt) {
        return renderItemStack(minecraft, itemRenderer, stack, x, y, txt, false);
    }

    /**
     * Draws a rectangle with a vertical gradient between the specified colors.
     * x2 and y2 are not included.
     */
    public static void drawVerticalGradientRect(int startX, int startY, int endX, int endY, int startColor, int endColor) {
//        this.zLevel = 300.0F;
        float zLevel = 0.0f;

        float startColorA = (startColor >> 24 & 255) / 255.0F;
        float startColorR = (startColor >> 16 & 255) / 255.0F;
        float startColorG = (startColor >> 8 & 255) / 255.0F;
        float startColorB = (startColor & 255) / 255.0F;
        float endColorA = (endColor >> 24 & 255) / 255.0F;
        float endColorR = (endColor >> 16 & 255) / 255.0F;
        float endColorG = (endColor >> 8 & 255) / 255.0F;
        float endColorB = (endColor & 255) / 255.0F;

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA);

        GL11.glBlendFunc(770, 771);
        //OpenGlHelper.glBlendFunc(770, 771, 1, 0);

        GL11.glShadeModel(GL11.GL_SMOOTH);

        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.startQuads();
        tessellator.color(startColorR, startColorG, startColorB, startColorA);
        tessellator.vertex(endX, startY, zLevel);
        tessellator.vertex(startX, startY, zLevel);
        tessellator.color(endColorR, endColorG, endColorB, endColorA);
        tessellator.vertex(startX, endY, zLevel);
        tessellator.vertex(endX, endY, zLevel);
        tessellator.draw();

        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    /**
     * Draws a rectangle with a horizontal gradient between the specified colors.
     * x2 and y2 are not included.
     */
    public static void drawHorizontalGradientRect(int x1, int y1, int x2, int y2, int color1, int color2) {
//        float zLevel = 0.0f;
//
//        float f = (color1 >> 24 & 255) / 255.0F;
//        float f1 = (color1 >> 16 & 255) / 255.0F;
//        float f2 = (color1 >> 8 & 255) / 255.0F;
//        float f3 = (color1 & 255) / 255.0F;
//        float f4 = (color2 >> 24 & 255) / 255.0F;
//        float f5 = (color2 >> 16 & 255) / 255.0F;
//        float f6 = (color2 >> 8 & 255) / 255.0F;
//        float f7 = (color2 & 255) / 255.0F;
//        GlStateManager.disableTexture2D();
//        GlStateManager.enableBlend();
//        GlStateManager.disableAlpha();
//        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
//        GlStateManager.shadeModel(GL11.GL_SMOOTH);
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder buffer = tessellator.getBuffer();
//        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
//        buffer.pos(x1, y1, zLevel).color(f1, f2, f3, f).endVertex();
//        buffer.pos(x1, y2, zLevel).color(f1, f2, f3, f).endVertex();
//        buffer.pos(x2, y2, zLevel).color(f5, f6, f7, f4).endVertex();
//        buffer.pos(x2, y1, zLevel).color(f5, f6, f7, f4).endVertex();
//        tessellator.draw();
//        GlStateManager.shadeModel(GL11.GL_FLAT);
//        GlStateManager.disableBlend();
//        GlStateManager.enableAlpha();
//        GlStateManager.enableTexture2D();
    }

    public static void drawHorizontalLine(int x1, int y1, int x2, int color) {
        fill(x1, y1, x2, y1 + 1, color);
    }

    public static void drawVerticalLine(int x1, int y1, int y2, int color) {
        fill(x1, y1, x1 + 1, y2, color);
    }

    // Draw a small triangle. x,y is the coordinate of the left point
    public static void drawLeftTriangle(int x, int y, int color) {
        drawVerticalLine(x, y, y, color);
        drawVerticalLine(x + 1, y - 1, y + 1, color);
        drawVerticalLine(x + 2, y - 2, y + 2, color);
    }

    // Draw a small triangle. x,y is the coordinate of the right point
    public static void drawRightTriangle(int x, int y, int color) {
        drawVerticalLine(x, y, y, color);
        drawVerticalLine(x - 1, y - 1, y + 1, color);
        drawVerticalLine(x - 2, y - 2, y + 2, color);
    }

    // Draw a small triangle. x,y is the coordinate of the top point
    public static void drawUpTriangle(int x, int y, int color) {
        drawHorizontalLine(x, y, x, color);
        drawHorizontalLine(x - 1, y + 1, x + 1, color);
        drawHorizontalLine(x - 2, y + 2, x + 2, color);
    }

    // Draw a small triangle. x,y is the coordinate of the bottom point
    public static void drawDownTriangle(int x, int y, int color) {
        drawHorizontalLine(x, y, x, color);
        drawHorizontalLine(x - 1, y - 1, x + 1, color);
        drawHorizontalLine(x - 2, y - 2, x + 2, color);
    }

    /**
     * Draw a button box. x2 and y2 are not included.
     */
    public static void drawFlatButtonBox(int x1, int y1, int x2, int y2, int bright, int average, int dark) {
        drawBeveledBox(x1, y1, x2, y2, bright, dark, average);
    }

    /**
     * Draw a button box. x2 and y2 are not included.
     */
    public static void drawFlatButtonBoxGradient(int x1, int y1, int x2, int y2, int bright, int average1, int average2, int dark) {
        drawVerticalGradientRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, average2, average1);
        drawHorizontalLine(x1, y1, x2 - 1, bright);
        drawVerticalLine(x1, y1, y2 - 1, bright);
        drawVerticalLine(x2 - 1, y1, y2 - 1, dark);
        drawHorizontalLine(x1, y2 - 1, x2, dark);
    }

    /**
     * Draw a beveled box. x2 and y2 are not included.
     */
    public static void drawBeveledBox(int x1, int y1, int x2, int y2, int topleftcolor, int botrightcolor, int fillcolor) {
        if (fillcolor != -1) {
            fill(x1 + 1, y1 + 1, x2 - 1, y2 - 1, fillcolor);
        }
        drawHorizontalLine(x1, y1, x2 - 1, topleftcolor);
        drawVerticalLine(x1, y1, y2 - 1, topleftcolor);
        drawVerticalLine(x2 - 1, y1, y2 - 1, botrightcolor);
        drawHorizontalLine(x1, y2 - 1, x2, botrightcolor);
    }

    /**
     * Draw a thick beveled box. x2 and y2 are not included.
     */
    public static void drawThickBeveledBox(int x1, int y1, int x2, int y2, int thickness, int topleftcolor, int botrightcolor, int fillcolor) {
        if (fillcolor != -1) {
            fill(x1 + 1, y1 + 1, x2 - 1, y2 - 1, fillcolor);
        }
        fill(x1, y1, x2 - 1, y1 + thickness, topleftcolor);
        fill(x1, y1, x1 + thickness, y2 - 1, topleftcolor);
        fill(x2 - thickness, y1, x2, y2 - 1, botrightcolor);
        fill(x1, y2 - thickness, x2, y2, botrightcolor);
    }

    public static void fill(int x1, int y1, int x2, int y2, int color) {
        int var6;
        if (x1 < x2) {
            var6 = x1;
            x1 = x2;
            x2 = var6;
        }

        if (y1 < y2) {
            var6 = y1;
            y1 = y2;
            y2 = var6;
        }

        float var11 = (float) (color >> 24 & 255) / 255.0F;
        float var7 = (float) (color >> 16 & 255) / 255.0F;
        float var8 = (float) (color >> 8 & 255) / 255.0F;
        float var9 = (float) (color & 255) / 255.0F;
        Tessellator var10 = Tessellator.INSTANCE;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(var7, var8, var9, var11);
        var10.startQuads();
        var10.vertex(x1, y2, 0.0);
        var10.vertex(x2, y2, 0.0);
        var10.vertex(x2, y1, 0.0);
        var10.vertex(x1, y1, 0.0);
        var10.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public static void drawTexturedModalRect(int x, int y, int u, int v, int width, int height, int twidth, int theight) {
        float zLevel = 0.01f;
        float xScale = 1.0F / twidth;
        float yScale = 1.0F / theight;
        Tessellator t = Tessellator.INSTANCE;
        t.startQuads();
        t.vertex((x), (y + height), zLevel, ((u) * xScale), ((v + height) * yScale));
        t.vertex((x + width), (y + height), zLevel, ((u + width) * xScale), ((v + height) * yScale));
        t.vertex((x + width), (y), zLevel, ((u + width) * xScale), ((v) * yScale));
        t.vertex((x), (y), zLevel, ((u) * xScale), ((v) * yScale));
        t.draw();
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public static void drawTexturedModalRect(int x, int y, int u, int v, int width, int height) {
        float zLevel = 0.01f;
        float xScale = 0.00390625F;
        float yScale = 0.00390625F;
        Tessellator t = Tessellator.INSTANCE;
        t.startQuads();
        t.vertex((x), (y + height), zLevel, ((u) * xScale), ((v + height) * yScale));
        t.vertex((x + width), (y + height), zLevel, ((u + width) * xScale), ((v + height) * yScale));
        t.vertex((x + width), (y), zLevel, ((u + width) * xScale), ((v) * yScale));
        t.vertex((x), (y), zLevel, ((u) * xScale), (v) * yScale);
        t.draw();
    }

    public static int renderText(Minecraft minecraft, int x, int y, String text) {
        int width = ProbeTextRenderer.INSTANCE.getWidth(text);

        GL11.glDisable(GL11.GL_BLEND);
        renderStringAtPos(text, x + 1, y + 1, Color.WHITE, true);
        renderStringAtPos(text, x, y, Color.WHITE, false);
        GL11.glEnable(GL11.GL_BLEND);

        return width;
    }

    public static void renderStringAtPos(String text, int x, int y, Color color, boolean shadow) {
        int intColor = color.getRGB();

        if (shadow) {
            int shadowOffset = intColor & -16777216;
            intColor = (intColor & 16579836) >> 2;
            intColor += shadowOffset;
        }

        float red = (float) (intColor >> 16 & 255) / 255;
        float green = (float) (intColor >> 8 & 255) / 255;
        float blue = (float) (intColor & 255) / 255;
        float alpha = (float) (intColor >> 24 & 255) / 255;

        if (alpha == 0) {
            alpha = 1;
        }

        ProbeTextRenderer.INSTANCE.renderStringAtPos(text, x, y, new Color(red, green, blue, alpha), shadow);
    }

    public static void setupOverlayRendering(double sw, double sh) {
        GL11.glClear(256);
//        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, sw, sh, 0.0D, 1000.0D, 3000.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
    }
}
