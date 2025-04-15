package net.mcjty.whatsthis.apiimpl.elements;

import net.mcjty.whatsthis.api.IElement;
import net.mcjty.whatsthis.apiimpl.TheOneProbeImp;
import net.mcjty.whatsthis.apiimpl.client.ElementTextRender;
import net.mcjty.whatsthis.network.NetworkUtil;
import net.minecraft.item.ItemStack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ElementItemLabel implements IElement {
    private final ItemStack stack;

    // Constructor
    public ElementItemLabel(ItemStack stack) {
        this.stack = stack;
    }

    // Networking
    public ElementItemLabel(DataInputStream stream) throws IOException {
        if (stream.readBoolean()) {
            stack = NetworkUtil.readItemStack(stream);
        } else {
            stack = null;
        }
    }

    @Override
    public void toBytes(DataOutputStream stream) throws IOException {
        if (stack != null && stack.count > 0) {
            stream.writeBoolean(true);
            NetworkUtil.writeItemStack(stream, stack);
        } else {
            stream.writeBoolean(false);
        }
    }

    // Rendering
    @Override
    public void render(int x, int y) {
        if (stack != null && stack.count > 0) {
            String text = stack.getItem().getTranslatedName();
            ElementTextRender.render(text, x, y);
        }
    }

    // Styling
    @Override
    public int getWidth() {
        if (stack != null && stack.count > 0) {
            String text = stack.getItem().getTranslatedName();
            return ElementTextRender.getWidth(text);
        } else {
            return 10;
        }
    }

    @Override
    public int getHeight() {
        return 10;
    }

    // ID
    @Override
    public int getID() {
        return TheOneProbeImp.ELEMENT_ITEMLABEL;
    }
}
