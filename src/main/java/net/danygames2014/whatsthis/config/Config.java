package net.danygames2014.whatsthis.config;

import com.google.common.collect.ImmutableMap;
import net.glasslauncher.mods.gcapi3.api.ConfigEntry;
import net.glasslauncher.mods.gcapi3.api.ConfigFactoryProvider;
import net.glasslauncher.mods.gcapi3.api.ConfigRoot;
import net.glasslauncher.mods.gcapi3.impl.SeptFunction;
import net.glasslauncher.mods.gcapi3.impl.factory.DefaultFactoryProvider;
import net.glasslauncher.mods.gcapi3.impl.object.ConfigEntryHandler;
import net.glasslauncher.mods.gcapi3.impl.object.entry.EnumConfigEntryHandler;
import net.danygames2014.whatsthis.api.IProbeConfig;
import net.danygames2014.whatsthis.api.NumberFormat;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.function.Function;

public class Config implements ConfigFactoryProvider {
    @ConfigRoot(value = "config", visibleName = "Config")
    public static final MainConfig MAIN_CONFIG = new MainConfig();

    @ConfigRoot(value = "provider", visibleName = "Provider Config")
    public static final ProviderConfig PROVIDER_CONFIG = new ProviderConfig();

    @ConfigRoot(value = "client", visibleName = "Client Config")
    public static final ClientConfig CLIENT_CONFIG = new ClientConfig();

    @ConfigRoot(value = "probe", visibleName = "Probe Config")
    public static final ProbeConfig PROBE_CONFIG = new ProbeConfig();

    public static int parseColor(String col) {
        try {
            return (int) Long.parseLong(col, 16);
        } catch (NumberFormatException e) {
            System.out.println("Config.parseColor");
            return 0;
        }
    }

    @Override
    public void provideLoadFactories(ImmutableMap.Builder<Type, SeptFunction<String, ConfigEntry, Field, Object, Boolean, Object, Object, ConfigEntryHandler<?>>> immutableBuilder) {
        immutableBuilder.put(NumberFormat.class, ((id, configEntry, parentField, parentObject, isMultiplayerSynced, enumOrOrdinal, defaultEnum) -> new EnumConfigEntryHandler<NumberFormat>(id, configEntry, parentField, parentObject, isMultiplayerSynced, DefaultFactoryProvider.enumOrOrdinalToOrdinal(enumOrOrdinal), ((NumberFormat) defaultEnum).ordinal(), NumberFormat.class)));
        immutableBuilder.put(IProbeConfig.ConfigMode.class, ((id, configEntry, parentField, parentObject, isMultiplayerSynced, enumOrOrdinal, defaultEnum) -> new EnumConfigEntryHandler<IProbeConfig.ConfigMode>(id, configEntry, parentField, parentObject, isMultiplayerSynced, DefaultFactoryProvider.enumOrOrdinalToOrdinal(enumOrOrdinal), ((IProbeConfig.ConfigMode) defaultEnum).ordinal(), IProbeConfig.ConfigMode.class)));

    }

    @Override
    public void provideSaveFactories(ImmutableMap.Builder<Type, Function<Object, Object>> immutableBuilder) {
        immutableBuilder.put(NumberFormat.class, object -> object);
        immutableBuilder.put(IProbeConfig.ConfigMode.class, object -> object);
    }
}
