package net.mcjty.whatsthis.apiimpl;

import net.mcjty.whatsthis.api.IProbeConfig;
import net.mcjty.whatsthis.config.Config;

public class ProbeConfig implements IProbeConfig {
    @Override
    public int getTankMode() {
        return Config.MAIN_CONFIG.showTank;
    }

    @Override
    public int getRFMode() {
        return Config.MAIN_CONFIG.showRF;
    }

    @Override
    public ConfigMode getAnimalOwnerSetting() {
        return Config.PROBE_CONFIG.showMobOwner;
    }

    @Override
    public ConfigMode getShowMobSpawnerSetting() {
        return Config.PROBE_CONFIG.showMobSpawner;
    }

    @Override
    public ConfigMode getShowModName() {
        return Config.PROBE_CONFIG.showModName;
    }

    @Override
    public ConfigMode getShowHarvestLevel() {
        return Config.PROBE_CONFIG.showHarvestLevel;
    }

    @Override
    public ConfigMode getShowCanBeHarvested() {
        return Config.PROBE_CONFIG.showCanBeHarvested;
    }

    @Override
    public ConfigMode getShowCropPercentage() {
        return Config.PROBE_CONFIG.showCropPercentage;
    }

    @Override
    public ConfigMode getShowChestContents() {
        return Config.PROBE_CONFIG.showChestContents;
    }

    @Override
    public ConfigMode getShowChestContentsDetailed() {
        return Config.PROBE_CONFIG.showChestContentsDetailed;
    }

    @Override
    public ConfigMode getShowRedstone() {
        return Config.PROBE_CONFIG.showRedstone;
    }

    @Override
    public ConfigMode getShowMobHealth() {
        return Config.PROBE_CONFIG.showMobHealth;
    }

    @Override
    public ConfigMode getShowLeverSetting() {
        return Config.PROBE_CONFIG.showLeverSetting;
    }

    @Override
    public ConfigMode getShowTankSetting() {
        return Config.PROBE_CONFIG.showTankSetting;
    }

    @Override
    public ConfigMode getShowMusicBlock() {
        return Config.PROBE_CONFIG.showMusicBlock;
    }
}
