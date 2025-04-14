package net.mcjty.whatsthis.apiimpl.elements;

import net.mcjty.whatsthis.api.IElement;
import net.mcjty.whatsthis.api.IItemStyle;
import net.mcjty.whatsthis.apiimpl.TheOneProbeImp;
import net.mcjty.whatsthis.apiimpl.client.ElementItemStackRender;
import net.mcjty.whatsthis.apiimpl.styles.ItemStyle;
import net.mcjty.whatsthis.network.NetworkTools;
import net.minecraft.item.ItemStack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ElementItemStack implements IElement {

    private final ItemStack stack;
    private final IItemStyle style;

    public ElementItemStack(ItemStack stack, IItemStyle style) {
        this.stack = stack;
        this.style = style;
    }

    public ElementItemStack(DataInputStream stream) throws IOException {
        if (stream.readBoolean()) {
            stack = NetworkTools.readItemStack(stream);
        } else {
            stack = null;
        }
        style = new ItemStyle()
                .width(stream.readInt())
                .height(stream.readInt());
    }

    @Override
    public void render(int x, int y) {
        ElementItemStackRender.render(stack, style, x, y);
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
        if (stack != null) {
            stream.writeBoolean(true);
            NetworkTools.writeItemStack(stream, stack);
        } else {
            stream.writeBoolean(false);
        }
        stream.writeInt(style.getWidth());
        stream.writeInt(style.getHeight());
    }

    @Override
    public int getID() {
        return TheOneProbeImp.ELEMENT_ITEM;
    }
}
