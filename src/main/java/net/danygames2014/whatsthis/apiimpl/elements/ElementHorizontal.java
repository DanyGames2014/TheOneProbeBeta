package net.danygames2014.whatsthis.apiimpl.elements;

import net.danygames2014.whatsthis.api.ElementAlignment;
import net.danygames2014.whatsthis.api.IElement;
import net.danygames2014.whatsthis.apiimpl.TheOneProbeImp;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ElementHorizontal extends AbstractElementPanel {
    // Constructor
    public ElementHorizontal(Integer borderColor, int spacing, ElementAlignment alignment) {
        super(borderColor, spacing, alignment);
    }

    // Networking
    public ElementHorizontal(DataInputStream stream) {
        super(stream);
    }

    @Override
    public void toBytes(DataOutputStream stream) {
        super.toBytes(stream);
    }

    // Rendering
    @Override
    public void render(int x, int y) {
        super.render(x, y);
        if (borderColor != null) {
            x += 3;
            y += 3;
        }
        int totHeight = getHeight();
        for (IElement element : children) {
            int h = element.getHeight();
            int cy = y;
            switch (alignment) {
                case ALIGN_TOPLEFT:
                    break;
                case ALIGN_CENTER:
                    cy = y + (totHeight - h) / 2;
                    break;
                case ALIGN_BOTTOMRIGHT:
                    cy = y + totHeight - h;
                    break;
            }
            element.render(x, cy);
            x += element.getWidth() + spacing;
        }
    }

    // Styling
    private int getBorderSpacing() {
        return borderColor == null ? 0 : 6;
    }

    @Override
    public int getWidth() {
        int w = 0;
        for (IElement element : children) {
            w += element.getWidth();
        }
        return w + spacing * (children.size() - 1) + getBorderSpacing();
    }

    @Override
    public int getHeight() {
        int h = 0;
        for (IElement element : children) {
            int hh = element.getHeight();
            if (hh > h) {
                h = hh;
            }
        }
        return h + getBorderSpacing();
    }

    // ID
    @Override
    public int getID() {
        return TheOneProbeImp.ELEMENT_HORIZONTAL;
    }
}
