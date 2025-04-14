package net.mcjty.whatsthis.apiimpl.elements;

import net.mcjty.whatsthis.api.IElement;
import net.mcjty.whatsthis.api.IIconStyle;
import net.mcjty.whatsthis.apiimpl.TheOneProbeImp;
import net.mcjty.whatsthis.apiimpl.client.ElementIconRender;
import net.mcjty.whatsthis.apiimpl.styles.IconStyle;
import net.mcjty.whatsthis.network.NetworkTools;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ElementIcon implements IElement {

    private final Identifier icon;
    private final int u;
    private final int v;
    private final int w;
    private final int h;
    private final IIconStyle style;

    public ElementIcon(Identifier icon, int u, int v, int w, int h, IIconStyle style) {
        this.icon = icon;
        this.u = u;
        this.v = v;
        this.w = w;
        this.h = h;
        this.style = style;
    }

    public ElementIcon(DataInputStream buf) throws IOException {
        icon = Identifier.of(Namespace.of(NetworkTools.readString(buf)), NetworkTools.readString(buf));
        u = buf.readInt();
        v = buf.readInt();
        w = buf.readInt();
        h = buf.readInt();
        style = new IconStyle()
                .width(buf.readInt())
                .height(buf.readInt())
                .textureWidth(buf.readInt())
                .textureHeight(buf.readInt());
    }

    @Override
    public void render(int x, int y) {
        ElementIconRender.render(icon, x, y, w, h, u, v, style.getTextureWidth(), style.getTextureHeight());
    }

    @Override
    public int getWidth() {
        return style.getWidth();
    }

    @Override
    public int getHeight() {
        return style.getHeight();
    }

    @Override
    public void toBytes(DataOutputStream stream) throws IOException {
        NetworkTools.writeString(stream, icon.namespace.toString());
        NetworkTools.writeString(stream, icon.path);
        stream.writeInt(u);
        stream.writeInt(v);
        stream.writeInt(w);
        stream.writeInt(h);
        stream.writeInt(style.getWidth());
        stream.writeInt(style.getHeight());
        stream.writeInt(style.getTextureWidth());
        stream.writeInt(style.getTextureHeight());
    }

    @Override
    public int getID() {
        return TheOneProbeImp.ELEMENT_ICON;
    }
}
