package net.mcjty.whatsthis.api;

/**
 * Style for the progress bar.
 */
public interface IProgressStyle {
    /// The color that is used for the border of the progress bar
    IProgressStyle borderColor(int borderColor);

    /// The color that is used for the background of the progress bar
    IProgressStyle backgroundColor(int backgroundColor);

    /// The color that is used for the filled part of the progress bar
    IProgressStyle filledColor(int filledColor);

    /// If this is different from the filledColor then the fill color will alternate
    IProgressStyle alternateFilledColor(int alternateFilledColor);

    /// If true then text is shown inside the progress bar
    IProgressStyle showText(boolean showText);

    /// The number format to use for the text inside the progress bar
    IProgressStyle numberFormat(NumberFormat format);

    IProgressStyle prefix(String prefix);

    IProgressStyle suffix(String suffix);

    /// If the progressbar is a lifebar then this is the maximum width
    IProgressStyle width(int width);

    IProgressStyle height(int height);

    IProgressStyle lifeBar(boolean isLifeBar);

    IProgressStyle armorBar(boolean isArmorBar);

    int getBorderColor();

    int getBackgroundColor();

    int getFilledColor();

    int getAlternatefilledColor();

    boolean isShowText();

    NumberFormat getNumberFormat();

    String getPrefix();

    String getSuffix();

    int getWidth();

    int getHeight();

    boolean isLifeBar();

    boolean isArmorBar();
}
