package net.mcjty.whatsthis.config;

import net.glasslauncher.mods.gcapi3.api.ConfigRoot;

public class Config {
    @ConfigRoot(value = "config", visibleName = "Config")
    public static final MainConfig MAIN_CONFIG = new MainConfig();
    
    @ConfigRoot(value = "provider", visibleName = "Provider Config")
    public static final ProviderConfig PROVIDER_CONFIG = new ProviderConfig();
    
    @ConfigRoot(value = "client", visibleName = "Client Config")
    public static final ClientConfig CLIENT_CONFIG = new ClientConfig();

    public static int parseColor(String col) {
        try {
            return (int) Long.parseLong(col, 16);
        } catch (NumberFormatException e) {
            System.out.println("Config.parseColor");
            return 0;
        }
    }
}
