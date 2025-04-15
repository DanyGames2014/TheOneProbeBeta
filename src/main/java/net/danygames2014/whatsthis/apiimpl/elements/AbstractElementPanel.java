package net.danygames2014.whatsthis.apiimpl.elements;

import net.danygames2014.whatsthis.api.*;
import net.danygames2014.whatsthis.apiimpl.ProbeInfo;
import net.danygames2014.whatsthis.apiimpl.styles.*;
import net.danygames2014.whatsthis.rendering.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractElementPanel implements IElement, IProbeInfo {
    protected List<IElement> children = new ArrayList<>();
    protected Integer borderColor;
    protected int spacing;
    protected ElementAlignment alignment;

    // Constructor
    public AbstractElementPanel(Integer borderColor, int spacing, ElementAlignment alignment) {
        this.borderColor = borderColor;
        this.spacing = spacing;
        this.alignment = alignment;
    }

    // Networking
    public AbstractElementPanel(DataInputStream stream) {
        try {
            children = ProbeInfo.createElements(stream);
            if (stream.readBoolean()) {
                borderColor = stream.readInt();
            }
            spacing = stream.readShort();
            alignment = ElementAlignment.values()[stream.readShort()];
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void toBytes(DataOutputStream stream) {
        try {
            ProbeInfo.writeElements(children, stream);
            if (borderColor != null) {
                stream.writeBoolean(true);
                stream.writeInt(borderColor);
            } else {
                stream.writeBoolean(false);
            }
            stream.writeShort((short) spacing);
            stream.writeShort((short) alignment.ordinal());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Rendering
    @Override
    public void render(int x, int y) {
        if (borderColor != null) {
            int w = getWidth();
            int h = getHeight();
            RenderHelper.drawHorizontalLine(x, y, x + w - 1, borderColor);
            RenderHelper.drawHorizontalLine(x, y + h - 1, x + w - 1, borderColor);
            RenderHelper.drawVerticalLine(x, y, y + h - 1, borderColor);
            RenderHelper.drawVerticalLine(x + w - 1, y, y + h, borderColor);
        }
    }

    // Elements
    @Override
    public IProbeInfo icon(String icon, int u, int v, int w, int h) {
        return icon(icon, u, v, w, h, new IconStyle());
    }

    @Override
    public IProbeInfo icon(String icon, int u, int v, int w, int h, IIconStyle style) {
        children.add(new ElementIcon(icon, u, v, w, h, style));
        return this;
    }

    @Override
    public IProbeInfo text(String text) {
        children.add(new ElementText(text));
        return this;
    }

    @Override
    public IProbeInfo text(String text, ITextStyle style) {
        children.add(new ElementText(text));
        return this;
    }

    @Override
    public IProbeInfo itemLabel(ItemStack stack, ITextStyle style) {
        children.add(new ElementItemLabel(stack));
        return this;
    }

    @Override
    public IProbeInfo itemLabel(ItemStack stack) {
        children.add(new ElementItemLabel(stack));
        return this;
    }

    @Override
    public IProbeInfo entity(String entityName, IEntityStyle style) {
        children.add(new ElementEntity(entityName, style));
        return this;
    }

    @Override
    public IProbeInfo entity(String entityName) {
        return entity(entityName, new EntityStyle());
    }

    @Override
    public IProbeInfo entity(Entity entity, IEntityStyle style) {
        children.add(new ElementEntity(entity, style));
        return this;
    }

    @Override
    public IProbeInfo entity(Entity entity) {
        return entity(entity, new EntityStyle());
    }

    @Override
    public IProbeInfo item(ItemStack stack, IItemStyle style) {
        children.add(new ElementItemStack(stack, style));
        return this;
    }

    @Override
    public IProbeInfo item(ItemStack stack) {
        return item(stack, new ItemStyle());
    }

    @Override
    public IProbeInfo progress(int current, int max) {
        return progress(current, max, new ProgressStyle());
    }

    @Override
    public IProbeInfo progress(int current, int max, IProgressStyle style) {
        children.add(new ElementProgress(current, max, style));
        return this;
    }

    @Override
    public IProbeInfo progress(long current, long max) {
        return progress(current, max, new ProgressStyle());
    }

    @Override
    public IProbeInfo progress(long current, long max, IProgressStyle style) {
        children.add(new ElementProgress(current, max, style));
        return this;
    }

    @Override
    public IProbeInfo horizontal(ILayoutStyle style) {
        ElementHorizontal e = new ElementHorizontal(style.getBorderColor(), style.getSpacing(), style.getAlignment());
        children.add(e);
        return e;
    }

    @Override
    public IProbeInfo horizontal() {
        ElementHorizontal e = new ElementHorizontal(null, spacing, ElementAlignment.ALIGN_TOPLEFT);
        children.add(e);
        return e;
    }

    @Override
    public IProbeInfo vertical(ILayoutStyle style) {
        ElementVertical e = new ElementVertical(style.getBorderColor(), style.getSpacing(), style.getAlignment());
        children.add(e);
        return e;
    }

    @Override
    public IProbeInfo vertical() {
        ElementVertical e = new ElementVertical(null, ElementVertical.SPACING, ElementAlignment.ALIGN_TOPLEFT);
        children.add(e);
        return e;
    }

    @Override
    public IProbeInfo element(IElement element) {
        children.add(element);
        return this;
    }

    // Styling
    @Override
    public ILayoutStyle defaultLayoutStyle() {
        return new LayoutStyle();
    }

    @Override
    public IProgressStyle defaultProgressStyle() {
        return new ProgressStyle();
    }

    @Override
    public ITextStyle defaultTextStyle() {
        return new TextStyle();
    }

    @Override
    public IItemStyle defaultItemStyle() {
        return new ItemStyle();
    }

    @Override
    public IEntityStyle defaultEntityStyle() {
        return new EntityStyle();
    }

    @Override
    public IIconStyle defaultIconStyle() {
        return new IconStyle();
    }
}
