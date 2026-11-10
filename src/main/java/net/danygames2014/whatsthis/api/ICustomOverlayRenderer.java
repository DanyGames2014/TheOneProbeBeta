package net.danygames2014.whatsthis.api;

import net.danygames2014.whatsthis.rendering.OverlayRenderer;

/**
 * This is for implementing custom overlay rendering other than blocks and entities.
 * <p>You are responsible for maintaining your own cache and requesting information from 
 * the server including the necessary packets.
 * <p>I recommend looking at the existing implementations in {@link OverlayRenderer}
 */
public interface ICustomOverlayRenderer {
    boolean render(ProbeMode mode, double scaledWidth, double scaledHeight);

    /**
     * In this method you should clean up your cached information.
     */
    void cleanup();
}
