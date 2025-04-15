package net.danygames2014.whatsthis.apiimpl.styles;

import net.danygames2014.whatsthis.api.IIconStyle;

public class IconStyle implements IIconStyle {
    private int width = 16;
    private int height = 16;
    private int txtw = 256;
    private int txth = 256;

    @Override
    public IIconStyle width(int width) {
        this.width = width;
        return this;
    }

    @Override
    public IIconStyle height(int height) {
        this.height = height;
        return this;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public IIconStyle textureWidth(int textureWidth) {
        txtw = textureWidth;
        return this;
    }

    @Override
    public IIconStyle textureHeight(int textureHeight) {
        txth = textureHeight;
        return this;
    }

    @Override
    public int getTextureWidth() {
        return txtw;
    }

    @Override
    public int getTextureHeight() {
        return txth;
    }
}
