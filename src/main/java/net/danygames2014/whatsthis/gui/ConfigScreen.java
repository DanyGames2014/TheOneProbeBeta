package net.danygames2014.whatsthis.gui;

import net.danygames2014.whatsthis.Util;
import net.danygames2014.whatsthis.WhatsThis;
import net.danygames2014.whatsthis.api.IOverlayStyle;
import net.danygames2014.whatsthis.api.TextStyleClass;
import net.danygames2014.whatsthis.apiimpl.ProbeInfo;
import net.danygames2014.whatsthis.config.Config;
import net.danygames2014.whatsthis.config.ConfigSetup;
import net.danygames2014.whatsthis.rendering.RenderHelper;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Formatting;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.danygames2014.whatsthis.api.TextStyleClass.*;

public class ConfigScreen extends Screen {
    private static final int WIDTH = 230;
    private static final int HEIGHT = 230;

    private int guiLeft;
    private int guiTop;

    private static final String background = "assets/whatsthis/stationapi/textures/gui/config.png";
    private static final String scene = "assets/whatsthis/stationapi/textures/gui/scene.png";

    private static int backgroundId = 0;
    private static int sceneId = 0;

    private static final List<Preset> presets = new ArrayList<>();

    private List<HitBox> hitboxes = Collections.emptyList();

    static {
        presets.add(new Preset("Default", 0xff999999, 0x55006699, 2, 0));
        presets.add(new Preset("WAILA", 0xff4503d0, 0xff000000, 1, 1));
        presets.add(new Preset("Jade", 0xff323331, 0xff20261a, 1, 1));
        presets.add(new Preset("Full transparent", 0x00000000, 0x00000000, 0, 0));
        presets.add(new Preset("Black & White", 0xffffffff, 0xff000000, 2, 0,
                Pair.of(MODNAME, "white,italic"),
                Pair.of(NAME, "white,bold"),
                Pair.of(INFO, "white"),
                Pair.of(INFOIMP, "white,bold"),
                Pair.of(WARNING, "white"),
                Pair.of(ERROR, "white,underline"),
                Pair.of(OBSOLETE, "white,strikethrough"),
                Pair.of(LABEL, "white,underline"),
                Pair.of(OK, "white"),
                Pair.of(PROGRESS, "white")
        ));
        presets.add(new Preset("Soft Pastels", 0xffe0bbff, 0x00000000, 1, 1,
                Pair.of(MODNAME, "aqua")
        ));
        presets.add(new Preset("Ocean Blue", 0xff003366, 0x556699cc, 2, 0,
                Pair.of(TextStyleClass.MODNAME, "cyan"),
                Pair.of(TextStyleClass.NAME, "light_blue,bold"),
                Pair.of(TextStyleClass.INFO, "white"),
                Pair.of(TextStyleClass.INFOIMP, "white,bold"),
                Pair.of(TextStyleClass.WARNING, "yellow,bold"),
                Pair.of(TextStyleClass.ERROR, "red,bold"),
                Pair.of(TextStyleClass.OBSOLETE, "gray,bold,italic"),
                Pair.of(TextStyleClass.LABEL, "aqua,bold"),
                Pair.of(TextStyleClass.OK, "green,bold"),
                Pair.of(TextStyleClass.PROGRESS, "white,bold")
        ));
    }


    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void init() {
        super.init();
        guiLeft = (this.width - WIDTH - WIDTH) / 2;
        guiTop = (this.height - HEIGHT) / 2;

        backgroundId = minecraft.textureManager.getTextureId(background);
        sceneId = minecraft.textureManager.getTextureId(scene);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        minecraft.textureManager.bindTexture(backgroundId);
        drawTexture(guiLeft + WIDTH, guiTop, 0, 0, WIDTH, HEIGHT);
        minecraft.textureManager.bindTexture(sceneId);
        drawTexture(guiLeft, guiTop, 0, 0, WIDTH, HEIGHT);

        renderProbe();

        int x = WIDTH + guiLeft + 10;
        int y = guiTop + 10;
        RenderHelper.renderText(minecraft, x, y, Formatting.GOLD + "Placement:");
        y += 12;
        RenderHelper.renderText(minecraft, x + 10, y, "Click on corner in screenshot");
        y += 10;
        RenderHelper.renderText(minecraft, x + 10, y, "to move tooltip there");
        y += 20;

        hitboxes = new ArrayList<>();
        RenderHelper.renderText(minecraft, x, y, Formatting.GOLD + "Presets:");
        y += 12;
        for (Preset preset : presets) {
            y = addPreset(x, y, preset);
        }
        y += 10;

        RenderHelper.renderText(minecraft, x, y, Formatting.GOLD + "Scale:");
        y += 12;
        addButton(x + 10, y, 30, 14, " --", () -> {
            ConfigSetup.setTooltipScale(Config.CLIENT_CONFIG.tooltipScale + 0.2F);
        });
        x += 36;
        addButton(x + 10, y, 30, 14, "  -", () -> {
            ConfigSetup.setTooltipScale(Config.CLIENT_CONFIG.tooltipScale + 0.1F);
        });
        x += 36;
        addButton(x + 10, y, 30, 14, "  0", () -> {
            ConfigSetup.setTooltipScale(1.0F);
        });
        x += 36;
        addButton(x + 10, y, 30, 14, "  +", () -> {
            ConfigSetup.setTooltipScale(Config.CLIENT_CONFIG.tooltipScale - 0.1F);
        });
        x += 36;
        addButton(x + 10, y, 30, 14, "  ++", () -> {
            ConfigSetup.setTooltipScale(Config.CLIENT_CONFIG.tooltipScale - 0.2F);
        });


        int margin = 90;
        hitboxes.add(new HitBox(0, 0, margin, margin, () -> {
            ConfigSetup.setPos(5, 5, -1, -1);
        }));
        hitboxes.add(new HitBox(margin, 0, WIDTH - margin, margin, () -> {
            ConfigSetup.setPos(-1, 5, -1, -1);
        }));
        hitboxes.add(new HitBox(WIDTH - margin, 0, WIDTH, margin, () -> {
            ConfigSetup.setPos(-1, 5, 5, -1);
        }));
        hitboxes.add(new HitBox(0, margin, margin, HEIGHT - margin, () -> {
            ConfigSetup.setPos(5, -1, -1, -1);
        }));
        hitboxes.add(new HitBox(margin, margin, WIDTH - margin, HEIGHT - margin, () -> {
            ConfigSetup.setPos(-1, -1, -1, -1);
        }));
        hitboxes.add(new HitBox(WIDTH - margin, margin, WIDTH, HEIGHT - margin, () -> {
            ConfigSetup.setPos(-1, -1, 5, -1);
        }));
        hitboxes.add(new HitBox(0, HEIGHT - margin, margin, HEIGHT, () -> {
            ConfigSetup.setPos(5, -1, -1, 5);
        }));
        hitboxes.add(new HitBox(margin, HEIGHT - margin, WIDTH - margin, HEIGHT, () -> {
            ConfigSetup.setPos(-1, -1, -1, 20);
        }));
        hitboxes.add(new HitBox(WIDTH - margin, HEIGHT - margin, WIDTH, HEIGHT, () -> {
            ConfigSetup.setPos(-1, -1, 5, 5);
        }));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            for (HitBox box : hitboxes) {
                if (box.isHit(mouseX - guiLeft, mouseY - guiTop)) {
                    box.call();
                }
            }
        }
    }

    private void applyPreset(Preset preset) {
        ConfigSetup.setBoxStyle(preset.getBoxThickness(), preset.getBoxBorderColor(), preset.getBoxFillColor(), preset.getBoxOffset());
        ConfigSetup.setTextStyle(ConfigSetup.defaultTextStyleClasses, preset.getTextStyleClasses());
    }

    private int addPreset(int x, int y, Preset preset) {
        fill(x + 10, y - 1, x + 10 + WIDTH - 50, y + 10, 0xff000000);
        RenderHelper.renderText(minecraft, x + 20, y, preset.getName());
        hitboxes.add(new HitBox(x + 10 - guiLeft, y - 1 - guiTop, x + 10 + WIDTH - 50 - guiLeft, y + 10 - guiTop, () -> {
            applyPreset(preset);
        }));
        y += 14;
        return y;
    }

    private void addButton(int x, int y, int width, int height, String text, Runnable runnable) {
        fill(x, y, x + width - 1, y + height - 1, 0xff000000);
        RenderHelper.renderText(minecraft, x + 3, y + 3, text);
        hitboxes.add(new HitBox(x - guiLeft, y - guiTop, x + width - 1 - guiLeft, y + height - 1 - guiTop, runnable));
    }

    private void renderProbe() {
        Block block = Block.LOG;
        String modid = Util.getModName(block);
        ProbeInfo probeInfo = WhatsThis.theOneProbeImp.create();
        ItemStack pickBlock = new ItemStack(block);
        probeInfo.horizontal()
                .item(pickBlock)
                .vertical()
                .text(NAME + pickBlock.getItem().getTranslatedName())
                .text(MODNAME + modid);
        probeInfo.text(LABEL + "Fuel: " + INFO + "5 volts");
        probeInfo.text(LABEL + "Error: " + ERROR + "Oups!");

        renderElements(probeInfo, ConfigSetup.getDefaultOverlayStyle());
    }

    private void renderElements(ProbeInfo probeInfo, IOverlayStyle style) {
        GL11.glPushMatrix();
        GL11.glScalef(1 / Config.CLIENT_CONFIG.tooltipScale, 1 / Config.CLIENT_CONFIG.tooltipScale, 1 / Config.CLIENT_CONFIG.tooltipScale);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);

        int w = probeInfo.getWidth();
        int h = probeInfo.getHeight();

        int offset = style.getBorderOffset();
        int thick = style.getBorderThickness();
        int margin = 0;
        if (thick > 0) {
            w += (offset + thick + 3) * 2;
            h += (offset + thick + 3) * 2;
            margin = offset + thick + 3;
        }

        int x = calculateXPosition(style, w);
        int y = calculateYPosition(style, h);

        x += guiLeft;
        y += guiTop;

        double factor = (Config.CLIENT_CONFIG.tooltipScale - 1) * 1.4 + 1;
        x *= factor;
        y *= factor;

        if (thick > 0) {
            int x2 = x + w - 1;
            int y2 = y + h - 1;
            if (offset > 0) {
                RenderHelper.drawThickBeveledBox(x, y, x2, y2, thick, style.getBoxColor(), style.getBoxColor(), style.getBoxColor());
            }
            RenderHelper.drawThickBeveledBox(x + offset, y + offset, x2 - offset, y2 - offset, thick, style.getBorderColor(), style.getBorderColor(), style.getBoxColor());
        }

        if (!minecraft.paused) {
            RenderHelper.rot += .5f;
        }

        probeInfo.render(x + margin, y + margin);

        GL11.glPopMatrix();
    }

    /**
     * Calculates the x position for the overlay based on the given style and width.
     *
     * @param style The {@link IOverlayStyle} object defining the style of the overlay.
     * @param width The width of the overlay.
     * @return The calculated x position.
     */
    private int calculateXPosition(IOverlayStyle style, int width) {
        if (style.getLeftX() != -1) {
            return style.getLeftX();
        } else if (style.getRightX() != -1) {
            return WIDTH - width - style.getRightX();
        } else {
            return (WIDTH - width) / 2;
        }
    }

    /**
     * Calculates the y position for the overlay based on the given style and height.
     *
     * @param style  The {@link IOverlayStyle} object defining the style of the overlay.
     * @param height The height of the overlay.
     * @return The calculated y position.
     */
    private int calculateYPosition(IOverlayStyle style, int height) {
        if (style.getTopY() != -1) {
            return style.getTopY();
        } else if (style.getBottomY() != -1) {
            return HEIGHT - height - style.getBottomY();
        } else {
            return (HEIGHT - height) / 2;
        }
    }

}
