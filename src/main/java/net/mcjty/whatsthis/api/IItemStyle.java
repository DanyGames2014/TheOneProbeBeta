package net.mcjty.whatsthis.api;

/**
 * Style for the item element.
 */
public interface IItemStyle {
    IItemStyle width(int width);

    IItemStyle height(int height);

    int getWidth();

    int getHeight();
}
