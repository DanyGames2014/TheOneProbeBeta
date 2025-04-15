package net.danygames2014.whatsthis.apiimpl.elements;

import net.danygames2014.whatsthis.api.IElement;
import net.danygames2014.whatsthis.apiimpl.TheOneProbeImp;
import net.danygames2014.whatsthis.apiimpl.client.ElementTextRender;
import net.danygames2014.whatsthis.network.NetworkUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ElementText implements IElement {
    private final String text;

    // Constructor
    public ElementText(String text) {
        this.text = text;
    }

    // Networking
    public ElementText(DataInputStream stream) throws IOException {
        text = NetworkUtil.readStringUTF8(stream);
    }

    @Override
    public void toBytes(DataOutputStream stream) throws IOException {
        NetworkUtil.writeStringUTF8(stream, text);
    }

    // Rendering
    @Override
    public void render(int x, int y) {
        ElementTextRender.render(text, x, y);
    }

    // Styling
    @Override
    public int getWidth() {
        return ElementTextRender.getWidth(text);
    }

    @Override
    public int getHeight() {
        return 10;
    }

    // ID
    @Override
    public int getID() {
        return TheOneProbeImp.ELEMENT_TEXT;
    }
}
