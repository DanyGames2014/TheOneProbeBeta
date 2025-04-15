package net.danygames2014.thatsthis.init;

import net.danygames2014.thatsthis.provider.TestProbeInfoProvider;
import net.danygames2014.whatsthis.event.BlockProbeInfoProviderRegistryEvent;
import net.mine_diver.unsafeevents.listener.EventListener;

public class ProbeInfoListener {
    @EventListener
    public void probeInfoRegistry(BlockProbeInfoProviderRegistryEvent event) {
        event.registerProvider(new TestProbeInfoProvider());
    }
}
