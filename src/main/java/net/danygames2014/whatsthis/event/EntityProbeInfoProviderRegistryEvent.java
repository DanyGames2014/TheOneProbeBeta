package net.danygames2014.whatsthis.event;

import net.danygames2014.whatsthis.WhatsThis;
import net.danygames2014.whatsthis.api.IProbeInfoEntityProvider;
import net.mine_diver.unsafeevents.Event;

public class EntityProbeInfoProviderRegistryEvent extends Event {
    public void registerProvider(IProbeInfoEntityProvider provider) {
        WhatsThis.theOneProbeImp.registerEntityProvider(provider);
    }
}
