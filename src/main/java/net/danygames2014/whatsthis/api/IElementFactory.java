package net.danygames2014.whatsthis.api;


import java.io.DataInputStream;
import java.io.IOException;

/**
 * A factory for elements
 */
public interface IElementFactory {

    /**
     * Create an element from a network buffer. This should be
     * symmetrical to what IElement.toBytes() creates.
     */
    IElement createElement(DataInputStream stream) throws IOException;
}
