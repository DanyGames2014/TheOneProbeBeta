package net.danygames2014.whatsthis.event;

import net.danygames2014.whatsthis.api.ICustomOverlayRenderer;
import net.danygames2014.whatsthis.rendering.OverlayRenderer;
import net.mine_diver.unsafeevents.Event;

public class CustomOverlayRendererRegistryEvent extends Event {
    public void register(ICustomOverlayRenderer renderer) {
        OverlayRenderer.customRenderers.add(renderer);
    }
}
