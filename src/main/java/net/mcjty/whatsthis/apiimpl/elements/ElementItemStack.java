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

    private final ItemStack itemStack;
    private final IItemStyle style;

    public ElementItemStack(ItemStack itemStack, IItemStyle style) {
        this.itemStack = itemStack;
        this.style = style;
    }

    public ElementItemStack(DataInputStream buf) throws IOException {
        if (buf.readBoolean()) {
            itemStack = NetworkTools.readItemStack(buf);
        } else {
            itemStack = null;
        }
        style = new ItemStyle()
                .width(buf.readInt())
                .height(buf.readInt());
    }

    @Override
    public void render(int x, int y) {
        ElementItemStackRender.render(itemStack, style, x, y);
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
    public void toBytes(DataOutputStream buf) throws IOException {
        if (itemStack != null) {
            buf.writeBoolean(true);
            NetworkTools.writeItemStack(buf, itemStack);
        } else {
            buf.writeBoolean(false);
        }
        buf.writeInt(style.getWidth());
        buf.writeInt(style.getHeight());
    }

    @Override
    public int getID() {
        return TheOneProbeImp.ELEMENT_ITEM;
    }
}
