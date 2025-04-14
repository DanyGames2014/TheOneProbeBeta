package net.mcjty.whatsthis.apiimpl.elements;

import net.mcjty.whatsthis.api.IElement;
import net.mcjty.whatsthis.apiimpl.TheOneProbeImp;
import net.mcjty.whatsthis.apiimpl.client.ElementTextRender;
import net.mcjty.whatsthis.network.NetworkTools;
import net.minecraft.item.ItemStack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ElementItemLabel implements IElement {

    private final ItemStack itemStack;

    public ElementItemLabel(ItemStack stack) {
        this.itemStack = stack;
    }

    public ElementItemLabel(DataInputStream stream) throws IOException {
        if (stream.readBoolean()) {
            itemStack = NetworkTools.readItemStack(stream);
        } else {
            itemStack = null;
        }
    }

    @Override
    public void render(int x, int y) {
        if (itemStack != null && itemStack.count > 0) {
            String text = itemStack.getItem().getTranslatedName();
            ElementTextRender.render(text, x, y);
        }
    }

    @Override
    public int getWidth() {
        if (itemStack != null && itemStack.count > 0) {
            String text = itemStack.getItem().getTranslatedName();
            return ElementTextRender.getWidth(text);
        } else {
            return 10;
        }
    }

    @Override
    public int getHeight() {
        return 10;
    }

    @Override
    public void toBytes(DataOutputStream stream) throws IOException {
        if (itemStack != null && itemStack.count > 0) {
            stream.writeBoolean(true);
            NetworkTools.writeItemStack(stream, itemStack);
        } else {
            stream.writeBoolean(false);
        }
    }

    @Override
    public int getID() {
        return TheOneProbeImp.ELEMENT_ITEMLABEL;
    }
}
