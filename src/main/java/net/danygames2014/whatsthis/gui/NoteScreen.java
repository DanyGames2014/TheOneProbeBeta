package net.danygames2014.whatsthis.gui;

import net.danygames2014.whatsthis.config.Config;
import net.danygames2014.whatsthis.config.ConfigSetup;
import net.danygames2014.whatsthis.rendering.RenderHelper;
import net.minecraft.client.gui.screen.Screen;
import net.modificationstation.stationapi.api.util.Formatting;

import static net.danygames2014.whatsthis.config.ConfigSetup.*;

public class NoteScreen extends Screen {
    private static final int WIDTH = 256;
    private static final int HEIGHT = 160;

    private static final int BUTTON_WIDTH = 70;
    private static final int BUTTON_MARGIN = 80;
    public static final int BUTTON_HEIGHT = 16;

    private int guiLeft;
    private int guiTop;

    private static final String background = "assets/whatsthis/stationapi/textures/gui/note.png";
    private static int backgroundId = 0;

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void init() {
        super.init();
        guiLeft = (this.width - WIDTH) / 2;
        guiTop = (this.height - HEIGHT) / 2;
        backgroundId = minecraft.textureManager.getTextureId(background);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        minecraft.textureManager.bindTexture(backgroundId);
        drawTexture(guiLeft, guiTop, 0, 0, WIDTH, HEIGHT);
        int x = guiLeft + 5;
        int y = guiTop + 8;
        RenderHelper.renderText(minecraft, x, y, "Things you should know about" + Formatting.GOLD + " The One Probe");
        y += 10;
        y += 10;

        RenderHelper.renderText(minecraft, x, y, "This mod can show a tooltip on screen");
        y += 10;
        RenderHelper.renderText(minecraft, x, y, "when you look at a block or an entity");
        y += 10;

        y += 10;
        switch (Config.PROBE_CONFIG.needsProbe) {
            case PROBE_NEEDED:
                RenderHelper.renderText(minecraft, x, y, "In this pack the probe is configured to be");
                y += 10;
                RenderHelper.renderText(minecraft, x, y, "required in order to see the tooltip");
                y += 10;
                y += 16;
                y = setInConfig(x, y);
                break;
            case PROBE_NOTNEEDED:
                RenderHelper.renderText(minecraft, x, y, "In this pack the probe is configured to be not");
                y += 10;
                RenderHelper.renderText(minecraft, x, y, "required in order to see the tooltip");
                y += 10;
                y += 16;
                y = setInConfig(x, y);
                break;
            case PROBE_NEEDEDFOREXTENDED:
                RenderHelper.renderText(minecraft, x, y, "In this pack the probe is configured to be");
                y += 10;
                RenderHelper.renderText(minecraft, x, y, "required to see extended information (when");
                y += 10;
                RenderHelper.renderText(minecraft, x, y, "sneaking) but not for basic information");
                y += 10;
                y += 6;
                y = setInConfig(x, y);
                break;
            case PROBE_NEEDEDHARD:
                RenderHelper.renderText(minecraft, x, y, "In this pack the probe is configured to be");
                y += 10;
                RenderHelper.renderText(minecraft, x, y, "required in order to see the tooltip");
                y += 10;
                RenderHelper.renderText(minecraft, x, y, "This is set server side");
                y += 10;
                break;
        }

        y += 10;

        RenderHelper.renderText(minecraft, x, y, "Check out the 'Mod Options... for many client'");
        y += 10;
        RenderHelper.renderText(minecraft, x, y, "side configuration settings or sneak-right click");
        y += 10;
        RenderHelper.renderText(minecraft, x, y, "this note for more user-friendly setup");
        y += 10;
    }

    private int hitX;
    private int hitY;

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        mouseX += guiLeft;
        mouseY += guiTop;
        if (mouseY >= hitY && mouseY < hitY + BUTTON_HEIGHT) {
            if (mouseX >= hitX && mouseX < hitX + BUTTON_WIDTH) {
                ConfigSetup.setProbeNeeded(PROBE_NEEDED);
            } else if (mouseX >= hitX + BUTTON_MARGIN && mouseX < hitX + BUTTON_WIDTH + BUTTON_MARGIN) {
                ConfigSetup.setProbeNeeded(PROBE_NOTNEEDED);
            } else if (mouseX >= hitX + BUTTON_MARGIN * 2 && mouseX < hitX + BUTTON_WIDTH + BUTTON_MARGIN * 2) {
                ConfigSetup.setProbeNeeded(PROBE_NEEDEDFOREXTENDED);
            }
        }
    }

    private int setInConfig(int x, int y) {
        RenderHelper.renderText(minecraft, x, y, Formatting.GREEN + "You can change this here:");
        y += 10;

        hitY = y + guiTop;
        hitX = x + guiLeft;
        fill(x, y, x + BUTTON_WIDTH, y + BUTTON_HEIGHT, 0xff000000);
        RenderHelper.renderText(minecraft, x + 3, y + 4, "Needed");
        x += BUTTON_MARGIN;

        fill(x, y, x + BUTTON_WIDTH, y + BUTTON_HEIGHT, 0xff000000);
        RenderHelper.renderText(minecraft, x + 3, y + 4, "Not needed");
        x += BUTTON_MARGIN;

        fill(x, y, x + BUTTON_WIDTH, y + BUTTON_HEIGHT, 0xff000000);
        RenderHelper.renderText(minecraft, x + 3, y + 4, "Extended");
        x += BUTTON_MARGIN;

        y += BUTTON_HEIGHT - 4;
        return y;
    }

}
