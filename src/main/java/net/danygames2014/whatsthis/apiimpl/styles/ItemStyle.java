package net.danygames2014.whatsthis.apiimpl.styles;

import net.danygames2014.whatsthis.api.IItemStyle;

public class ItemStyle implements IItemStyle {
    private int width = 20;
    private int height = 20;

    @Override
    public IItemStyle width(int width) {
        this.width = width;
        return this;
    }

    @Override
    public IItemStyle height(int height) {
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
}
