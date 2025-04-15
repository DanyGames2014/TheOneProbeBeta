package net.danygames2014.whatsthis.apiimpl.elements;

import net.danygames2014.whatsthis.api.IElement;
import net.danygames2014.whatsthis.api.IIconStyle;
import net.danygames2014.whatsthis.apiimpl.TheOneProbeImp;
import net.danygames2014.whatsthis.apiimpl.client.ElementIconRender;
import net.danygames2014.whatsthis.apiimpl.styles.IconStyle;
import net.danygames2014.whatsthis.network.NetworkUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ElementIcon implements IElement {
    private final String icon;
    private final int u;
    private final int v;
    private final int w;
    private final int h;
    private final IIconStyle style;

    // Constructor
    public ElementIcon(String icon, int u, int v, int w, int h, IIconStyle style) {
        this.icon = icon;
        this.u = u;
        this.v = v;
        this.w = w;
        this.h = h;
        this.style = style;
    }

    // Networking
    public ElementIcon(DataInputStream stream) throws IOException {
        icon = NetworkUtil.readString(stream);
        u = stream.readInt();
        v = stream.readInt();
        w = stream.readInt();
        h = stream.readInt();
        style = new IconStyle()
                .width(stream.readInt())
                .height(stream.readInt())
                .textureWidth(stream.readInt())
                .textureHeight(stream.readInt());
    }

    @Override
    public void toBytes(DataOutputStream stream) throws IOException {
        NetworkUtil.writeString(stream, icon);
        stream.writeInt(u);
        stream.writeInt(v);
        stream.writeInt(w);
        stream.writeInt(h);
        stream.writeInt(style.getWidth());
        stream.writeInt(style.getHeight());
        stream.writeInt(style.getTextureWidth());
        stream.writeInt(style.getTextureHeight());
    }

    // Rendering
    @Override
    public void render(int x, int y) {
        ElementIconRender.render(icon, x, y, w, h, u, v, style.getTextureWidth(), style.getTextureHeight());
    }

    // Styling
    @Override
    public int getWidth() {
        return style.getWidth();
    }

    @Override
    public int getHeight() {
        return style.getHeight();
    }

    // ID
    @Override
    public int getID() {
        return TheOneProbeImp.ELEMENT_ICON;
    }
}
