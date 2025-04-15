package net.mcjty.whatsthis.event;

import net.mcjty.whatsthis.WhatsThis;
import net.mcjty.whatsthis.api.IProbeInfoProvider;
import net.mine_diver.unsafeevents.Event;

public class BlockProbeInfoProviderRegistryEvent extends Event {
    public void registerProvider(IProbeInfoProvider provider) {
        WhatsThis.theOneProbeImp.registerProvider(provider);
    }
}
