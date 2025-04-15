package net.danygames2014.whatsthis.apiimpl.elements;

import net.danygames2014.whatsthis.api.IElement;
import net.danygames2014.whatsthis.api.IItemStyle;
import net.danygames2014.whatsthis.apiimpl.TheOneProbeImp;
import net.danygames2014.whatsthis.apiimpl.client.ElementItemStackRender;
import net.danygames2014.whatsthis.apiimpl.styles.ItemStyle;
import net.danygames2014.whatsthis.network.NetworkUtil;
import net.minecraft.item.ItemStack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ElementItemStack implements IElement {
    private final ItemStack stack;
    private final IItemStyle style;

    // Constructor
    public ElementItemStack(ItemStack stack, IItemStyle style) {
        this.stack = stack;
        this.style = style;
    }

    // Networking
    public ElementItemStack(DataInputStream stream) throws IOException {
        if (stream.readBoolean()) {
            stack = NetworkUtil.readItemStack(stream);
        } else {
            stack = null;
        }
        style = new ItemStyle()
                .width(stream.readInt())
                .height(stream.readInt());
    }

    @Override
    public void toBytes(DataOutputStream stream) throws IOException {
        if (stack != null) {
            stream.writeBoolean(true);
            NetworkUtil.writeItemStack(stream, stack);
        } else {
            stream.writeBoolean(false);
        }
        stream.writeInt(style.getWidth());
        stream.writeInt(style.getHeight());
    }

    // Rendering
    @Override
    public void render(int x, int y) {
        ElementItemStackRender.render(stack, style, x, y);
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
        return TheOneProbeImp.ELEMENT_ITEM;
    }
}
