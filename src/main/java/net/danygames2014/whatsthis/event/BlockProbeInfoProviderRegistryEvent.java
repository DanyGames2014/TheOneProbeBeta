package net.danygames2014.whatsthis.event;

import net.danygames2014.whatsthis.WhatsThis;
import net.danygames2014.whatsthis.api.IProbeInfoProvider;
import net.mine_diver.unsafeevents.Event;

public class BlockProbeInfoProviderRegistryEvent extends Event {
    public void registerProvider(IProbeInfoProvider provider) {
        WhatsThis.theOneProbeImp.registerProvider(provider);
    }
}
