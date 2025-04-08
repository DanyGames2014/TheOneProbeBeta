package net.mcjty.whatsthis.rendering;

import net.mcjty.whatsthis.WhatsThis;
import net.mcjty.whatsthis.network.ThrowableIdentity;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.platform.Lighting;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

public class RenderHelper {
    public static float rot = 0.0f;

    public static void enableLighting() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
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
        GL11.glDisable(GL11.GL_DEPTH_TEST);
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
        GL11.glTranslatef(xPos + 8, yPos + 24, 50F);
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
        GL11.glEnable(GL11.GL_DEPTH);
        GL11.glDisable(GL11.GL_COLOR_MATERIAL);
        GL13.glActiveTexture(GL13.GL_TEXTURE1); // lightmapTexUnit
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL13.glActiveTexture(GL13.GL_TEXTURE0); // defaultTexUnit
    }

    public static boolean renderObject(Minecraft mc, int x, int y, Object itm, boolean highlight) {
        if (itm instanceof Entity) {
            renderEntity((Entity) itm, x, y, 10);
            return true;
        }
        ItemRenderer itemRender = new ItemRenderer();
        return renderObject(mc, itemRender, x, y, itm, highlight, 200);
    }

    public static boolean renderObject(Minecraft mc, ItemRenderer itemRender, int x, int y, Object itm, boolean highlight, float lvl) {
        if (itm == null) {
            return renderItemStack(mc, itemRender, null, x, y, "", highlight);
        }
        if (itm instanceof Item) {
            return renderItemStack(mc, itemRender, new ItemStack((Item) itm, 1), x, y, "", highlight);
        }
        if (itm instanceof Block) {
            return renderItemStack(mc, itemRender, new ItemStack((Block) itm, 1), x, y, "", highlight);
        }
        if (itm instanceof ItemStack) {
            return renderItemStackWithCount(mc, itemRender, (ItemStack) itm, x, y, highlight);
        }
        return renderItemStack(mc, itemRender, new ItemStack(0, 0, 0), x, y, "", highlight);
    }

    public static boolean renderItemStackWithCount(Minecraft mc, ItemRenderer itemRender, ItemStack stack, int xo, int yo, boolean highlight) {
        if (stack.count <= 1) {
            return renderItemStack(mc, itemRender, stack, xo, yo, "", highlight);
        } else {
            return renderItemStack(mc, itemRender, stack, xo, yo, "" + stack.count, highlight);
        }
    }

    public static boolean renderItemStack(Minecraft minecraft, ItemRenderer itemRender, ItemStack stack, int x, int y, String txt, boolean highlight) {
        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        boolean rc = false;
        if (highlight) {
            GL11.glDisable(GL11.GL_LIGHTING);
            drawVerticalGradientRect(x, y, x + 16, y + 16, 0x80ffffff, 0xffffffff);
        }
        if (stack.getItem() != null && stack.count > 0) {
            rc = true;
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 32.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_LIGHTING);
            short short1 = 240;
            short short2 = 240;
            enableStandardItemLighting();
            GL13.glMultiTexCoord2f(GL13.GL_TEXTURE1, short1, short2);

            itemRender.renderGuiItem(minecraft.textRenderer, minecraft.textureManager, stack, x, y);
            itemRender.renderGuiItemDecoration(minecraft.textRenderer, minecraft.textureManager, stack, x, y);

            GL11.glPopMatrix();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glDisable(GL11.GL_LIGHTING);
        }

        return rc;
    }

    public static boolean renderItemStack(Minecraft minecraft, ItemRenderer itemRender, ItemStack stack, int x, int y, String txt) {
        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        boolean rc = true;
        if (stack.getItem() != null && stack.count > 0) {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 32.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_LIGHTING);
            short short1 = 240;
            short short2 = 240;
            enableStandardItemLighting();
            GL13.glMultiTexCoord2f(GL13.GL_TEXTURE1, short1, short2);

            try {
                itemRender.renderGuiItem(minecraft.textRenderer, minecraft.textureManager, stack, x, y);
                itemRender.renderGuiItemDecoration(minecraft.textRenderer, minecraft.textureManager, stack, x, y);
            } catch (Exception e) {
                ThrowableIdentity.registerThrowable(e);
                rc = false; // Report error
            }

            GL11.glPopMatrix();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glDisable(GL11.GL_LIGHTING);
        }

        return rc;
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
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator t = Tessellator.INSTANCE;
        t.startQuads();
        t.vertex((x), (y + height), zLevel,((u) * f), ((v + height) * f1));
        t.vertex((x + width), (y + height), zLevel,((u + width) * f), ((v + height) * f1));
        t.vertex((x + width), (y), zLevel,((u + width) * f), ((v) * f1));
        t.vertex((x), (y), zLevel,((u) * f), ((v) * f1));
        t.draw();
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public static void drawTexturedModalRect(int x, int y, int u, int v, int width, int height) {
        float zLevel = 0.01f;
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator t = Tessellator.INSTANCE;
        t.startQuads();
        t.vertex((x), (y + height), zLevel, ((u) * f), ((v + height) * f1));
        t.vertex((x + width), (y + height), zLevel, ((u + width) * f), ((v + height) * f1));
        t.vertex((x + width), (y), zLevel, ((u + width) * f), ((v) * f1));
        t.vertex((x), (y), zLevel, ((u) * f), (v) * f1);
        t.draw();
    }

    public static void renderBillboardQuadBright(double scale) {
//        int brightness = 240;
//        int b1 = brightness >> 16 & 65535;
//        int b2 = brightness & 65535;
//        GlStateManager.pushMatrix();
//        RenderHelper.rotateToPlayer();
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder buffer = tessellator.getBuffer();
//        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
//        buffer.pos(-scale, -scale, 0.0D).tex(0.0D, 0.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
//        buffer.pos(-scale, scale, 0.0D).tex(0.0D, 1.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
//        buffer.pos(scale, scale, 0.0D).tex(1.0D, 1.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
//        buffer.pos(scale, -scale, 0.0D).tex(1.0D, 0.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
//        tessellator.draw();
//        GlStateManager.popMatrix();
    }

    public static void renderBillboardQuad(double scale) {
//        GlStateManager.pushMatrix();
//
//        rotateToPlayer();
//
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder buffer = tessellator.getBuffer();
//        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
//        buffer.pos(-scale, -scale, 0).tex(0, 0).endVertex();
//        buffer.pos(-scale, +scale, 0).tex(0, 1).endVertex();
//
//        buffer.pos(+scale, +scale, 0).tex(1, 1).endVertex();
//
//        buffer.pos(+scale, -scale, 0).tex(1, 0).endVertex();
//
//        tessellator.draw();
//        GlStateManager.popMatrix();
    }

    public static void renderBillboardQuadWithRotation(float rot, double scale) {
//        GlStateManager.pushMatrix();
//
//        rotateToPlayer();
//
//        GlStateManager.rotate(rot, 0, 0, 1);
//
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder buffer = tessellator.getBuffer();
//        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
//        buffer.pos(-scale, -scale, 0).tex(0, 0).endVertex();
//        buffer.pos(-scale, +scale, 0).tex(0, 1).endVertex();
//
//        buffer.pos(+scale, +scale, 0).tex(1, 1).endVertex();
//
//        buffer.pos(+scale, -scale, 0).tex(1, 0).endVertex();
//
//        tessellator.draw();
//        GlStateManager.popMatrix();
    }

    public static void rotateToPlayer() {
//        GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
//        GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
    }

    /**
     * Draw a beam with some thickness.
     *
     * @param S
     * @param E
     * @param P
     * @param width
     */
    public static void drawBeam(Vector S, Vector E, Vector P, float width) {
//        Vector PS = Sub(S, P);
//        Vector SE = Sub(E, S);
//
//        Vector normal = Cross(PS, SE);
//        normal = normal.normalize();
//
//        Vector half = Mul(normal, width);
//        Vector p1 = Add(S, half);
//        Vector p2 = Sub(S, half);
//        Vector p3 = Add(E, half);
//        Vector p4 = Sub(E, half);
//
//        drawQuad(Tessellator.INSTANCE, p1, p3, p4, p2);
    }

    private static void drawQuad(Tessellator tessellator, Vector p1, Vector p2, Vector p3, Vector p4) {
//        int brightness = 240;
//        int b1 = brightness >> 16 & 65535;
//        int b2 = brightness & 65535;
//
//        BufferBuilder buffer = tessellator.getBuffer();
//        buffer.pos(p1.getX(), p1.getY(), p1.getZ()).tex(0.0D, 0.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
//        buffer.pos(p2.getX(), p2.getY(), p2.getZ()).tex(1.0D, 0.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
//        buffer.pos(p3.getX(), p3.getY(), p3.getZ()).tex(1.0D, 1.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
//        buffer.pos(p4.getX(), p4.getY(), p4.getZ()).tex(0.0D, 1.0D).lightmap(b1, b2).color(255, 255, 255, 128).endVertex();
    }

    /**
     * Renders the stack size and/or damage bar for the given ItemStack.
     */
//    public static void renderItemOverlayIntoGUI(FontRenderer fr, ItemStack stack, int xPosition, int yPosition, @Nullable String text,
//                                                int scaled) {
//        if (!stack.isEmpty()) {
//            if (stack.getCount() != 1 || text != null) {
//                String s = text == null ? String.valueOf(stack.getCount()) : text;
//                if (text == null && stack.getCount() < 1) {
//                    s = TextFormatting.RED + String.valueOf(stack.getCount());
//                }
//
//                GlStateManager.disableLighting();
//                GlStateManager.disableDepth();
//                GlStateManager.disableBlend();
//                if (scaled >= 2) {
//                    GlStateManager.pushMatrix();
//                    GlStateManager.scale(.5f, .5f, .5f);
//                    fr.drawStringWithShadow(s, ((xPosition + 19 - 2) * 2 - 1 - fr.getStringWidth(s)), yPosition * 2 + 24, 16777215);
//                    GlStateManager.popMatrix();
//                } else if (scaled == 1) {
//                    GlStateManager.pushMatrix();
//                    GlStateManager.scale(.75f, .75f, .75f);
//                    fr.drawStringWithShadow(s, ((xPosition - 2) * 1.34f + 24 - fr.getStringWidth(s)), yPosition * 1.34f + 14, 16777215);
//                    GlStateManager.popMatrix();
//                } else {
//                    fr.drawStringWithShadow(s, (xPosition + 19 - 2 - fr.getStringWidth(s)), (yPosition + 6 + 3), 16777215);
//                }
//                GlStateManager.enableLighting();
//                GlStateManager.enableDepth();
//                // Fixes opaque cooldown overlay a bit lower
//                // TODO: check if enabled blending still screws things up down the line.
//                GlStateManager.enableBlend();
//            }
//
//            if (stack.getItem().showDurabilityBar(stack)) {
//                double health = stack.getItem().getDurabilityForDisplay(stack);
//                int j = (int) Math.round(13.0D - health * 13.0D);
//                int i = (int) Math.round(255.0D - health * 255.0D);
//                GlStateManager.disableLighting();
//                GlStateManager.disableDepth();
//                GlStateManager.disableTexture2D();
//                GlStateManager.disableAlpha();
//                GlStateManager.disableBlend();
//                Tessellator tessellator = Tessellator.getInstance();
//                BufferBuilder vertexbuffer = tessellator.getBuffer();
//                draw(vertexbuffer, xPosition + 2, yPosition + 13, 13, 2, 0, 0, 0, 255);
//                draw(vertexbuffer, xPosition + 2, yPosition + 13, 12, 1, (255 - i) / 4, 64, 0, 255);
//                draw(vertexbuffer, xPosition + 2, yPosition + 13, j, 1, 255 - i, i, 0, 255);
//                GlStateManager.enableBlend();
//                GlStateManager.enableAlpha();
//                GlStateManager.enableTexture2D();
//                GlStateManager.enableLighting();
//                GlStateManager.enableDepth();
//            }
//
//            EntityPlayerSP entityplayersp = Minecraft.getMinecraft().player;
//            float f = entityplayersp == null ? 0.0F : entityplayersp.getCooldownTracker().getCooldown(stack.getItem(), Minecraft.getMinecraft().getRenderPartialTicks());
//
//            if (f > 0.0F) {
//                GlStateManager.disableLighting();
//                GlStateManager.disableDepth();
//                GlStateManager.disableTexture2D();
//                Tessellator tessellator1 = Tessellator.getInstance();
//                BufferBuilder vertexbuffer1 = tessellator1.getBuffer();
//                draw(vertexbuffer1, xPosition, yPosition + (int)Math.floor(16.0F * (1.0F - f)), 16, (int)Math.ceil(16.0F * f), 255, 255, 255, 127);
//                GlStateManager.enableTexture2D();
//                GlStateManager.enableLighting();
//                GlStateManager.enableDepth();
//            }
//        }
//    }

    /**
     * Draw with the WorldRenderer
     */
//    private static void draw(BufferBuilder renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha) {
//        renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
//        renderer.pos((x + 0), (y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
//        renderer.pos((x + 0), (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
//        renderer.pos((x + width), (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
//        renderer.pos((x + width), (y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
//        Tessellator.getInstance().draw();
//    }
    public static int renderText(Minecraft minecraft, int x, int y, String text) {
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_LIGHTING);
        enableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        int width = minecraft.textRenderer.getWidth(text);
        minecraft.textRenderer.drawWithShadow(text, x, y, 16777215);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_LIGHTING);

        return width;
    }

    public static class Vector {
        public final float x;
        public final float y;
        public final float z;

        public Vector(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public float getZ() {
            return z;
        }

        public float norm() {
            return (float) Math.sqrt(x * x + y * y + z * z);
        }

        public Vector normalize() {
            float n = norm();
            return new Vector(x / n, y / n, z / n);
        }
    }

    private static Vector Cross(Vector a, Vector b) {
        float x = a.y * b.z - a.z * b.y;
        float y = a.z * b.x - a.x * b.z;
        float z = a.x * b.y - a.y * b.x;
        return new Vector(x, y, z);
    }

    private static Vector Sub(Vector a, Vector b) {
        return new Vector(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    private static Vector Add(Vector a, Vector b) {
        return new Vector(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    private static Vector Mul(Vector a, float f) {
        return new Vector(a.x * f, a.y * f, a.z * f);
    }


}
