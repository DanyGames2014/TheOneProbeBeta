package net.danygames2014.whatsthis.api;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * An element in the probe gui.
 */
public interface IElement {

    /**
     * Render this element at the location given by the location
     */
    void render(int x, int y);

    /**
     * Get the width of this element
     */
    int getWidth();

    /**
     * Get the height of this element
     */
    int getHeight();

    /**
     * Persist this element to the given network buffer. This should be symmetrical to
     * what IElementFactory.createElement() expects.
     */
    void toBytes(DataOutputStream stream) throws IOException;

    /**
     * Get the identifier for this element (as returned by ITheOneProbe.registerElementFactory()
     */
    int getID();
}
