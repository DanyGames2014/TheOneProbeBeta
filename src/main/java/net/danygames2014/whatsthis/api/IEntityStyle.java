package net.danygames2014.whatsthis.api;

/**
 * Style for the entity element.
 */
public interface IEntityStyle {
    /**
     * Change the width of the element. Default is 25
     */
    IEntityStyle width(int width);

    /**
     * Change the height of the element. Default is 25
     */
    IEntityStyle height(int height);

    /**
     * Change the scale of the entity inside the element. Default is 1.0 which
     * tries to fit as good as possible.
     */
    IEntityStyle scale(float scale);

    int getWidth();

    int getHeight();

    float getScale();
}
