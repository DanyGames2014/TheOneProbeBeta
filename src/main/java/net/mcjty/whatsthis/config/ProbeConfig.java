package net.mcjty.whatsthis.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;
import net.mcjty.whatsthis.api.IProbeConfig;

import static net.mcjty.whatsthis.api.IProbeConfig.ConfigMode.EXTENDED;
import static net.mcjty.whatsthis.api.IProbeConfig.ConfigMode.NORMAL;

public class ProbeConfig {
    @ConfigEntry(name = "showHarvestLevel")
    public IProbeConfig.ConfigMode showHarvestLevel = NORMAL;

    @ConfigEntry(name = "showCanBeHarvested")
    public IProbeConfig.ConfigMode showCanBeHarvested = NORMAL;

    @ConfigEntry(name = "showModName")
    public IProbeConfig.ConfigMode showModName = NORMAL;

    @ConfigEntry(name = "showCropPercentage")
    public IProbeConfig.ConfigMode showCropPercentage = NORMAL;

    @ConfigEntry(name = "showChestContents =")
    public IProbeConfig.ConfigMode showChestContents = EXTENDED;

    @ConfigEntry(name = "showChestContentsDetailed =")
    public IProbeConfig.ConfigMode showChestContentsDetailed = EXTENDED;

    @ConfigEntry(name = "showRedstone")
    public IProbeConfig.ConfigMode showRedstone = NORMAL;

    @ConfigEntry(name = "showMobHealth")
    public IProbeConfig.ConfigMode showMobHealth = NORMAL;

    @ConfigEntry(name = "showMobGrowth")
    public IProbeConfig.ConfigMode showMobGrowth = NORMAL;

    //@ConfigEntry(name = "showMobPotionEffects")
    //public IProbeConfig.ConfigMode showMobPotionEffects = EXTENDED;

    @ConfigEntry(name = "showLeverSetting")
    public IProbeConfig.ConfigMode showLeverSetting = NORMAL;

    @ConfigEntry(name = "showTankSetting")
    public IProbeConfig.ConfigMode showTankSetting = EXTENDED;

    @ConfigEntry(name = "showMobSpawner")
    public IProbeConfig.ConfigMode showMobSpawner = NORMAL;

    @ConfigEntry(name = "showMobOwner")
    public IProbeConfig.ConfigMode showMobOwner = EXTENDED;
    
    @ConfigEntry(name = "showMusicBlock", description = "Show the note of a note block and the disc in a jukebox")
    public IProbeConfig.ConfigMode showMusicBlock = NORMAL;
    
    @ConfigEntry(name = "showSignText", description = "Show the text on a sign")
    public IProbeConfig.ConfigMode showSignText = EXTENDED;
}
