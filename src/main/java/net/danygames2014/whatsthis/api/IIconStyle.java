package net.danygames2014.whatsthis.api;

/**
 * Style for the icon element.
 */
public interface IIconStyle {
    /**
     * Change the width of the icon. Default is 16
     */
    IIconStyle width(int width);

    /**
     * Change the height of the icon. Default is 16
     */
    IIconStyle height(int height);

    int getWidth();

    int getHeight();

    /**
     * Change the total width of the texture on which the icon sits. Default is 256
     */
    IIconStyle textureWidth(int textureWidth);

    /**
     * Change the total height of the texture on which the icon sits. Default is 256
     */
    IIconStyle textureHeight(int textureHeight);

    int getTextureWidth();

    int getTextureHeight();
}
