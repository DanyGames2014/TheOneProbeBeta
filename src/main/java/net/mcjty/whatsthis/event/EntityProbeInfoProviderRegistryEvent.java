package net.mcjty.whatsthis.event;

import net.mcjty.whatsthis.WhatsThis;
import net.mcjty.whatsthis.api.IProbeInfoEntityProvider;
import net.mine_diver.unsafeevents.Event;

public class EntityProbeInfoProviderRegistryEvent extends Event {
    public void registerProvider(IProbeInfoEntityProvider provider) {
        WhatsThis.theOneProbeImp.registerEntityProvider(provider);
    }
}
