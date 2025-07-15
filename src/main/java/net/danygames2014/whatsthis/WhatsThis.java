package net.danygames2014.whatsthis;

import net.danygames2014.whatsthis.api.IProbeInfoEntityProvider;
import net.danygames2014.whatsthis.api.IProbeInfoProvider;
import net.danygames2014.whatsthis.apiimpl.TheOneProbeImp;
import net.danygames2014.whatsthis.apiimpl.providers.block.BlockProbeInfoProvider;
import net.danygames2014.whatsthis.apiimpl.providers.block.DebugProbeInfoProvider;
import net.danygames2014.whatsthis.apiimpl.providers.block.DefaultProbeInfoProvider;
import net.danygames2014.whatsthis.apiimpl.providers.entity.DebugProbeInfoEntityProvider;
import net.danygames2014.whatsthis.apiimpl.providers.entity.DefaultProbeInfoEntityProvider;
import net.danygames2014.whatsthis.apiimpl.providers.entity.EntityProbeInfoProvider;
import net.danygames2014.whatsthis.compat.AccessoryApiCompat;
import net.danygames2014.whatsthis.config.Config;
import net.danygames2014.whatsthis.event.BlockProbeInfoProviderRegistryEvent;
import net.danygames2014.whatsthis.event.EntityProbeInfoProviderRegistryEvent;
import net.danygames2014.whatsthis.item.ProbeNoteItem;
import net.danygames2014.whatsthis.item.ProbeUtil;
import net.danygames2014.whatsthis.network.PacketGetEntityInfo;
import net.danygames2014.whatsthis.network.PacketGetInfo;
import net.danygames2014.whatsthis.network.PacketReturnEntityInfo;
import net.danygames2014.whatsthis.network.PacketReturnInfo;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.client.event.gui.screen.container.TooltipBuildEvent;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.event.network.packet.PacketRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.mod.entrypoint.EntrypointManager;
import net.modificationstation.stationapi.api.registry.PacketTypeRegistry;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Formatting;
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
    public static Item probe;
    public static Item creativeProbe;
    public static Item probeGoggles;

    public static boolean accessoryApiCompat = false;

    @EventListener
    public void probeTooltip(TooltipBuildEvent event) {
        if (event.itemStack.getStationNbt().contains(ProbeUtil.PROBETAG)) {
            event.tooltip.add(Formatting.AQUA + "Probe");
        }
    }

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        probeNote = new ProbeNoteItem(NAMESPACE.id("probe_note")).setTranslationKey(NAMESPACE, "probe_note");
        probe = new TemplateItem(NAMESPACE.id("probe")).setTranslationKey(NAMESPACE, "probe").setMaxCount(1);
        creativeProbe = new TemplateItem(NAMESPACE.id("creative_probe")).setTranslationKey(NAMESPACE, "creative_probe").setMaxCount(1);

        if (accessoryApiCompat) {
            AccessoryApiCompat.registerProbeAccessory();
        }
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
        FabricLoader.getInstance().getEntrypointContainers("whatsthis", Object.class).forEach(EntrypointManager::setup);

        TheOneProbeImp.registerElements();
        theOneProbeImp.registerProvider(new DefaultProbeInfoProvider());
        theOneProbeImp.registerProvider(new DebugProbeInfoProvider());
        theOneProbeImp.registerProvider(new BlockProbeInfoProvider());
        StationAPI.EVENT_BUS.post(new BlockProbeInfoProviderRegistryEvent());

        theOneProbeImp.registerEntityProvider(new DefaultProbeInfoEntityProvider());
        theOneProbeImp.registerEntityProvider(new DebugProbeInfoEntityProvider());
        theOneProbeImp.registerEntityProvider(new EntityProbeInfoProvider());
        StationAPI.EVENT_BUS.post(new EntityProbeInfoProviderRegistryEvent());

        setupModCompat();
    }

    @EventListener(phase = InitEvent.POST_INIT_PHASE)
    public void postInit(InitEvent event) {
        configureProviders();
        configureEntityProviders();
    }

    private void setupModCompat() {
        if (Config.PROBE_CONFIG.supportAccessoryApi) {
            accessoryApiCompat = FabricLoader.getInstance().isModLoaded("accessoryapi");
        }
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
