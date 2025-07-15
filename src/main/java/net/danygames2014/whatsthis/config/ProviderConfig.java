package net.danygames2014.whatsthis.config;

import net.danygames2014.whatsthis.api.IProbeConfig;
import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

import static net.danygames2014.whatsthis.api.IProbeConfig.ConfigMode.EXTENDED;
import static net.danygames2014.whatsthis.api.IProbeConfig.ConfigMode.NORMAL;

public class ProviderConfig {
    // Mod Name
    @ConfigEntry(name = "Show Mod Name")
    public IProbeConfig.ConfigMode showModName = NORMAL;

    // Harvestability
    @ConfigEntry(name = "Show Harvestability")
    public IProbeConfig.ConfigMode showCanBeHarvested = NORMAL;

    @ConfigEntry(name = "Show Harvest Level")
    public IProbeConfig.ConfigMode showHarvestLevel = NORMAL;

    // Crop Growth
    @ConfigEntry(name = "Show Crop Growth Percentage")
    public IProbeConfig.ConfigMode showCropPercentage = NORMAL;

    // Redstone
    @ConfigEntry(name = "Show Redstone Power Level")
    public IProbeConfig.ConfigMode showRedstone = NORMAL;

    @ConfigEntry(name = "Show Redstone Component Setting")
    public IProbeConfig.ConfigMode showLeverSetting = NORMAL;

    // Mob Info
    @ConfigEntry(name = "Show Mob Health")
    public IProbeConfig.ConfigMode showMobHealth = NORMAL;

    //@ConfigEntry(name = "Show Mob Health")
    //public IProbeConfig.ConfigMode showMobGrowth = NORMAL;

    //@ConfigEntry(name = "showMobPotionEffects")
    //public IProbeConfig.ConfigMode showMobPotionEffects = EXTENDED;

    @ConfigEntry(name = "Show Mob Owner")
    public IProbeConfig.ConfigMode showMobOwner = EXTENDED;

    @ConfigEntry(name = "Show Mob Spawner Entity")
    public IProbeConfig.ConfigMode showMobSpawner = NORMAL;

    // Tank Setting
    @ConfigEntry(name = "Show Tank Info")
    public IProbeConfig.ConfigMode showTankSetting = EXTENDED;

    // Music Block Info
    @ConfigEntry(name = "Show Music Block Info", description = "Show the note of a note block and the disc in a jukebox")
    public IProbeConfig.ConfigMode showMusicBlock = NORMAL;

    // Sign Text
    @ConfigEntry(name = "Show Sign Text", description = "Show the text on a sign")
    public IProbeConfig.ConfigMode showSignText = EXTENDED;

    // Inventory Contents
    @ConfigEntry(name = "Show Inventory Contents")
    public IProbeConfig.ConfigMode showChestContents = EXTENDED;

    @ConfigEntry(name = "Show Detailed Inventory Contents")
    public IProbeConfig.ConfigMode showChestContentsDetailed = EXTENDED;

    @ConfigEntry(name = "Sorted Block Providers", comment = "Order in which providers should be used")
    public String[] sortedProviders = new String[]{};

    @ConfigEntry(name = "Excluded Block Providers", comment = "Providers that should be excluded")
    public String[] excludedProviders = new String[]{};

    @ConfigEntry(name = "Sorted Entity Providers", comment = "Order in which entity providers should be used")
    public String[] sortedEntityProviders = new String[]{};

    @ConfigEntry(name = "Excluded Entity Providers", comment = "Entity providers that should be excluded")
    public String[] excludedEntityProviders = new String[]{};
}
