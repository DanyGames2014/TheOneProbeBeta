package net.danygames2014.whatsthis.apiimpl.styles;

import net.danygames2014.whatsthis.api.IEntityStyle;

public class EntityStyle implements IEntityStyle {
    private int width = 25;
    private int height = 25;
    private float scale = 1.0f;

    @Override
    public IEntityStyle width(int width) {
        this.width = width;
        return this;
    }

    @Override
    public IEntityStyle height(int height) {
        this.height = height;
        return this;
    }

    @Override
    public IEntityStyle scale(float scale) {
        this.scale = scale;
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
    public float getScale() {
        return scale;
    }
}
