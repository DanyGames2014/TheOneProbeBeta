package net.danygames2014.whatsthis.event;

import net.danygames2014.whatsthis.WhatsThis;
import net.danygames2014.whatsthis.api.IElementFactory;
import net.mine_diver.unsafeevents.Event;

public class ProbeElementsRegistryEvent extends Event {
    public int registerElement(IElementFactory factory) {
        return WhatsThis.theOneProbeImp.registerElementFactory(factory);
    }
}
