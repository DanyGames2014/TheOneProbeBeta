package net.mcjty.whatsthis.apiimpl.styles;

import net.mcjty.whatsthis.api.IProgressStyle;
import net.mcjty.whatsthis.api.NumberFormat;

/**
 * Style for the progress bar.
 */
public class ProgressStyle implements IProgressStyle {
    private int borderColor = 0xffffffff;
    private int backgroundColor = 0xff000000;
    private int filledColor = 0xffaaaaaa;
    private int alternatefilledColor = 0xffaaaaaa;
    private boolean showText = true;
    private String prefix = "";
    private String suffix = "";
    private int width = 100;
    private int height = 12;
    private boolean lifeBar = false;
    private boolean armorBar = false;

    private NumberFormat numberFormat = NumberFormat.FULL;

    /// The color that is used for the border of the progress bar
    @Override
    public ProgressStyle borderColor(int borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    /// The color that is used for the background of the progress bar
    @Override
    public ProgressStyle backgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    /// The color that is used for the filled part of the progress bar
    @Override
    public ProgressStyle filledColor(int filledColor) {
        this.filledColor = filledColor;
        return this;
    }

    /// If this is different from the filledColor then the fill color will alternate
    @Override
    public ProgressStyle alternateFilledColor(int alternateFilledColor) {
        alternatefilledColor = alternateFilledColor;
        return this;
    }

    /// If true then text is shown inside the progress bar
    @Override
    public ProgressStyle showText(boolean showText) {
        this.showText = showText;
        return this;
    }

    /// The number format to use for the text inside the progress bar
    @Override
    public ProgressStyle numberFormat(NumberFormat format) {
        numberFormat = format;
        return this;
    }

    @Override
    public ProgressStyle prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    @Override
    public ProgressStyle suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    @Override
    public ProgressStyle width(int width) {
        this.width = width;
        return this;
    }

    @Override
    public ProgressStyle height(int height) {
        this.height = height;
        return this;
    }

    @Override
    public IProgressStyle lifeBar(boolean isLifeBar) {
        this.lifeBar = isLifeBar;
        return this;
    }

    @Override
    public IProgressStyle armorBar(boolean isArmorBar) {
        this.armorBar = isArmorBar;
        return this;
    }

    @Override
    public int getBorderColor() {
        return borderColor;
    }

    @Override
    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public int getFilledColor() {
        return filledColor;
    }

    @Override
    public int getAlternatefilledColor() {
        return alternatefilledColor;
    }

    @Override
    public boolean isShowText() {
        return showText;
    }

    @Override
    public NumberFormat getNumberFormat() {
        return numberFormat;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public String getSuffix() {
        return suffix;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isLifeBar() {
        return lifeBar;
    }

    @Override
    public boolean isArmorBar() {
        return armorBar;
    }
}
