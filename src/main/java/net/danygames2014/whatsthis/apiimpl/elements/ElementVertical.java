package net.danygames2014.whatsthis.apiimpl.elements;

import net.danygames2014.whatsthis.api.ElementAlignment;
import net.danygames2014.whatsthis.api.IElement;
import net.danygames2014.whatsthis.apiimpl.TheOneProbeImp;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ElementVertical extends AbstractElementPanel {
    public static final int SPACING = 2;

    // Constructor
    public ElementVertical(Integer borderColor, int spacing, ElementAlignment alignment) {
        super(borderColor, spacing, alignment);
    }

    // Networking
    public ElementVertical(DataInputStream stream) {
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
        int totWidth = getWidth();
        for (IElement element : children) {
            int w = element.getWidth();
            int cx = x;
            switch (alignment) {
                case ALIGN_TOPLEFT:
                    break;
                case ALIGN_CENTER:
                    cx = x + (totWidth - w) / 2;
                    break;
                case ALIGN_BOTTOMRIGHT:
                    cx = x + totWidth - w;
                    break;
            }
            element.render(cx, y);
            y += element.getHeight() + spacing;
        }
    }

    // Styling
    private int getBorderSpacing() {
        return borderColor == null ? 0 : 6;
    }

    @Override
    public int getHeight() {
        int h = 0;
        for (IElement element : children) {
            h += element.getHeight();
        }
        return h + spacing * (children.size() - 1) + getBorderSpacing();
    }

    @Override
    public int getWidth() {
        int w = 0;
        for (IElement element : children) {
            int ww = element.getWidth();
            if (ww > w) {
                w = ww;
            }
        }
        return w + getBorderSpacing();
    }

    // ID
    @Override
    public int getID() {
        return TheOneProbeImp.ELEMENT_VERTICAL;
    }
}
