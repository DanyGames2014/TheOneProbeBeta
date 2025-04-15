package net.danygames2014.whatsthis.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class ProviderConfig {
    @ConfigEntry(name = "sortedProviders", comment = "Order in which providers should be used")
    public String[] sortedProviders = new String[]{};

    @ConfigEntry(name = "excludedProviders", comment = "Providers that should be excluded")
    public String[] excludedProviders = new String[]{};

    @ConfigEntry(name = "sortedEntityProviders", comment = "Order in which entity providers should be used")
    public String[] sortedEntityProviders = new String[]{};

    @ConfigEntry(name = "excludedEntityProviders", comment = "Entity providers that should be excluded")
    public String[] excludedEntityProviders = new String[]{};
}
