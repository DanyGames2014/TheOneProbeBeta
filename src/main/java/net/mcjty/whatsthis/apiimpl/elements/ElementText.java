package net.mcjty.whatsthis.apiimpl.elements;

import net.mcjty.whatsthis.api.IElement;
import net.mcjty.whatsthis.apiimpl.TheOneProbeImp;
import net.mcjty.whatsthis.apiimpl.client.ElementTextRender;
import net.mcjty.whatsthis.network.NetworkTools;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ElementText implements IElement {

    private final String text;

    public ElementText(String text) {
        this.text = text;
    }

    public ElementText(DataInputStream stream) throws IOException {
        text = NetworkTools.readStringUTF8(stream);
    }

    @Override
    public void render(int x, int y) {
        ElementTextRender.render(text, x, y);
    }

    @Override
    public int getWidth() {
        return ElementTextRender.getWidth(text);
    }

    @Override
    public int getHeight() {
        return 10;
    }

    @Override
    public void toBytes(DataOutputStream stream) throws IOException {
        NetworkTools.writeStringUTF8(stream, text);
    }

    @Override
    public int getID() {
        return TheOneProbeImp.ELEMENT_TEXT;
    }
}
