package net.danygames2014.whatsthis.apiimpl.elements;

import net.danygames2014.whatsthis.api.IElement;
import net.danygames2014.whatsthis.api.IProgressStyle;
import net.danygames2014.whatsthis.api.NumberFormat;
import net.danygames2014.whatsthis.apiimpl.TheOneProbeImp;
import net.danygames2014.whatsthis.apiimpl.client.ElementProgressRender;
import net.danygames2014.whatsthis.apiimpl.styles.ProgressStyle;
import net.danygames2014.whatsthis.network.NetworkUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class ElementProgress implements IElement {
    private final long current;
    private final long max;
    private final IProgressStyle style;

    private static final DecimalFormat dfCommas = new DecimalFormat("###,###");

    // Constructor
    public ElementProgress(long current, long max, IProgressStyle style) {
        this.current = current;
        this.max = max;
        this.style = style;
    }
    
    // Networking
    public ElementProgress(DataInputStream stream) throws IOException {
        current = stream.readLong();
        max = stream.readLong();
        style = new ProgressStyle()
                .width(stream.readInt())
                .height(stream.readInt())
                .prefix(NetworkUtil.readStringUTF8(stream))
                .suffix(NetworkUtil.readStringUTF8(stream))
                .borderColor(stream.readInt())
                .filledColor(stream.readInt())
                .alternateFilledColor(stream.readInt())
                .backgroundColor(stream.readInt())
                .showText(stream.readBoolean())
                .numberFormat(NumberFormat.values()[stream.readByte()])
                .lifeBar(stream.readBoolean())
                .armorBar(stream.readBoolean());
    }

    @Override
    public void toBytes(DataOutputStream stream) throws IOException {
        stream.writeLong(current);
        stream.writeLong(max);
        stream.writeInt(style.getWidth());
        stream.writeInt(style.getHeight());
        NetworkUtil.writeStringUTF8(stream, style.getPrefix());
        NetworkUtil.writeStringUTF8(stream, style.getSuffix());
        stream.writeInt(style.getBorderColor());
        stream.writeInt(style.getFilledColor());
        stream.writeInt(style.getAlternatefilledColor());
        stream.writeInt(style.getBackgroundColor());
        stream.writeBoolean(style.isShowText());
        stream.writeByte(style.getNumberFormat().ordinal());
        stream.writeBoolean(style.isLifeBar());
        stream.writeBoolean(style.isArmorBar());
    }

    // Rendering
    @Override
    public void render(int x, int y) {
        ElementProgressRender.render(style, current, max, x, y, getWidth(), getHeight());
    }

    // Styling
    @Override
    public int getWidth() {
        if (style.isLifeBar()) {
            if (current * 4 >= style.getWidth()) {
                return 100;
            } else {
                return (int) (current * 4 + 2);
            }
        }
        return style.getWidth();
    }

    @Override
    public int getHeight() {
        return style.getHeight();
    }

    // ID
    @Override
    public int getID() {
        return TheOneProbeImp.ELEMENT_PROGRESS;
    }

    // Util
    /**
     * If the suffix starts with 'm' we can possibly drop that
     */
    public static String format(long in, NumberFormat style, String suffix) {
        switch (style) {
            case FULL:
                return Long.toString(in) + suffix;
            case COMPACT: {
                int unit = 1000;
                if (in < unit) {
                    return Long.toString(in) + " " + suffix;
                }
                int exp = (int) (Math.log(in) / Math.log(unit));
                char pre;
                if (suffix.startsWith("m")) {
                    suffix = suffix.substring(1);
                    if (exp - 2 >= 0) {
                        pre = "kMGTPE".charAt(exp - 2);
                        return String.format("%.1f %s", in / Math.pow(unit, exp), pre) + suffix;
                    } else {
                        return String.format("%.1f %s", in / Math.pow(unit, exp), suffix);
                    }
                } else {
                    pre = "kMGTPE".charAt(exp - 1);
                    return String.format("%.1f %s", in / Math.pow(unit, exp), pre) + suffix;
                }
            }
            case COMMAS:
                return dfCommas.format(in) + suffix;
            case NONE:
                return suffix;
        }
        return Long.toString(in);
    }
}
