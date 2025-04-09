package net.mcjty.whatsthis;

import net.mcjty.whatsthis.api.IProbeInfoEntityProvider;
import net.mcjty.whatsthis.api.IProbeInfoProvider;
import net.mcjty.whatsthis.apiimpl.TheOneProbeImp;
import net.mcjty.whatsthis.apiimpl.elements.*;
import net.mcjty.whatsthis.apiimpl.providers.*;
import net.mcjty.whatsthis.config.Config;
import net.mcjty.whatsthis.config.ConfigSetup;
import net.mcjty.whatsthis.items.ProbeNote;
import net.mcjty.whatsthis.network.PacketGetEntityInfo;
import net.mcjty.whatsthis.network.PacketGetInfo;
import net.mcjty.whatsthis.network.PacketReturnEntityInfo;
import net.mcjty.whatsthis.network.PacketReturnInfo;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.event.mod.PreInitEvent;
import net.modificationstation.stationapi.api.event.network.packet.PacketRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.api.registry.PacketTypeRegistry;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WhatsThis {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE = Null.get();

    @Entrypoint.Logger
    public static Logger LOGGER = Null.get();

    public static TheOneProbeImp theOneProbeImp = new TheOneProbeImp();

    public static Item probeNote;

    // TODO: BH Creative Support

    public Logger getLogger() {
        return LOGGER;
    }

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        probeNote = new ProbeNote(NAMESPACE.id("probe_note")).setTranslationKey(NAMESPACE, "probe_note");
    }

    @EventListener
    public void registerPackets(PacketRegisterEvent event) {
        Registry.register(PacketTypeRegistry.INSTANCE, NAMESPACE.id("get_info"), PacketGetInfo.TYPE);
        Registry.register(PacketTypeRegistry.INSTANCE, NAMESPACE.id("get_entity_info"), PacketGetEntityInfo.TYPE);
        Registry.register(PacketTypeRegistry.INSTANCE, NAMESPACE.id("return_info"), PacketReturnInfo.TYPE);
        Registry.register(PacketTypeRegistry.INSTANCE, NAMESPACE.id("return_entity_info"), PacketReturnEntityInfo.TYPE);
    }

    @EventListener(phase = InitEvent.PRE_INIT_PHASE)
    public void preInit(InitEvent event) {
        TheOneProbeImp.registerElements();
        theOneProbeImp.registerProvider(new DefaultProbeInfoProvider());
        theOneProbeImp.registerProvider(new DebugProbeInfoProvider());
        theOneProbeImp.registerProvider(new BlockProbeInfoProvider());
        theOneProbeImp.registerEntityProvider(new DefaultProbeInfoEntityProvider());
        theOneProbeImp.registerEntityProvider(new DebugProbeInfoEntityProvider());
        theOneProbeImp.registerEntityProvider(new EntityProbeInfoEntityProvider());

        setupModCompat();
    }

    @EventListener(phase = InitEvent.POST_INIT_PHASE)
    public void postInit(InitEvent event) {
        configureProviders();
        configureEntityProviders();
    }

    private void setupModCompat() {
//        tesla = Loader.isModLoaded("tesla");
//        if (tesla) {
//            logger.log(Level.INFO, "The One Probe Detected TESLA: enabling support");
//        }
//
//        redstoneflux = Loader.isModLoaded("redstoneflux");
//        if (redstoneflux) {
//            logger.log(Level.INFO, "The One Probe Detected RedstoneFlux: enabling support");
//        }
//
//        baubles = Loader.isModLoaded("Baubles") || Loader.isModLoaded("baubles");
//        if (baubles) {
//            if (ConfigSetup.supportBaubles) {
//                logger.log(Level.INFO, "The One Probe Detected Baubles: enabling support");
//            } else {
//                logger.log(Level.INFO, "The One Probe Detected Baubles but support disabled in config");
//                baubles = false;
//            }
//        }
    }

    private void configureProviders() {
        List<IProbeInfoProvider> providers = WhatsThis.theOneProbeImp.getProviders();
        String[] defaultValues = new String[providers.size()];
        int i = 0;
        for (IProbeInfoProvider provider : providers) {
            defaultValues[i++] = provider.getID();
        }

        String[] sortedProviders = defaultValues; //ConfigSetup.mainConfig.getStringList("sortedProviders", ConfigSetup.CATEGORY_PROVIDERS, defaultValues, "Order in which providers should be used");
        String[] excludedProviders = Config.PROVIDER_CONFIG.excludedProviders;//ConfigSetup.mainConfig.getStringList("excludedProviders", ConfigSetup.CATEGORY_PROVIDERS, new String[] {}, "Providers that should be excluded");
        Set<String> excluded = new HashSet<>();
        Collections.addAll(excluded, excludedProviders);

        WhatsThis.theOneProbeImp.configureProviders(sortedProviders, excluded);
    }

    private void configureEntityProviders() {
        List<IProbeInfoEntityProvider> providers = WhatsThis.theOneProbeImp.getEntityProviders();
        String[] defaultValues = new String[providers.size()];
        int i = 0;
        for (IProbeInfoEntityProvider provider : providers) {
            defaultValues[i++] = provider.getID();
        }

        String[] sortedProviders = defaultValues; //ConfigSetup.mainConfig.getStringList("sortedEntityProviders", ConfigSetup.CATEGORY_PROVIDERS, defaultValues, "Order in which entity providers should be used");
        String[] excludedProviders = Config.PROVIDER_CONFIG.excludedEntityProviders;//ConfigSetup.mainConfig.getStringList("excludedEntityProviders", ConfigSetup.CATEGORY_PROVIDERS, new String[] {}, "Entity providers that should be excluded");
        Set<String> excluded = new HashSet<>();
        Collections.addAll(excluded, excludedProviders);

        WhatsThis.theOneProbeImp.configureEntityProviders(sortedProviders, excluded);
    }
}
