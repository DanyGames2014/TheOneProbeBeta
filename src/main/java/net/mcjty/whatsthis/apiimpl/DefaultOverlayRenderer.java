package net.mcjty.whatsthis.apiimpl;

import net.mcjty.whatsthis.WhatsThis;
import net.mcjty.whatsthis.api.IOverlayRenderer;
import net.mcjty.whatsthis.api.IOverlayStyle;
import net.mcjty.whatsthis.api.IProbeInfo;
import net.mcjty.whatsthis.apiimpl.styles.DefaultOverlayStyle;
import net.mcjty.whatsthis.config.ConfigSetup;
import net.mcjty.whatsthis.rendering.OverlayRenderer;

public class DefaultOverlayRenderer implements IOverlayRenderer {

    @Override
    public IOverlayStyle createDefaultStyle() {
        return ((DefaultOverlayStyle) ConfigSetup.getDefaultOverlayStyle()).copy();
    }

    @Override
    public IProbeInfo createProbeInfo() {
        return WhatsThis.theOneProbeImp.create();
    }

    @Override
    public void render(IOverlayStyle style, IProbeInfo probeInfo) {
        OverlayRenderer.renderOverlay(style, probeInfo);
    }
}
