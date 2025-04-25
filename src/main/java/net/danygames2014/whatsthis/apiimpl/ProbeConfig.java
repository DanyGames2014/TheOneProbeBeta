package net.danygames2014.whatsthis.apiimpl;

import net.danygames2014.whatsthis.api.IProbeConfig;
import net.danygames2014.whatsthis.config.Config;

public class ProbeConfig implements IProbeConfig {
    @Override
    public int getTankMode() {
        return Config.PROBE_CONFIG.showTank;
    }

    @Override
    public int getRFMode() {
        return Config.PROBE_CONFIG.showRF;
    }

    @Override
    public ConfigMode getAnimalOwnerSetting() {
        return Config.PROVIDER_CONFIG.showMobOwner;
    }

    @Override
    public ConfigMode getShowMobSpawnerSetting() {
        return Config.PROVIDER_CONFIG.showMobSpawner;
    }

    @Override
    public ConfigMode getShowModName() {
        return Config.PROVIDER_CONFIG.showModName;
    }

    @Override
    public ConfigMode getShowHarvestLevel() {
        return Config.PROVIDER_CONFIG.showHarvestLevel;
    }

    @Override
    public ConfigMode getShowCanBeHarvested() {
        return Config.PROVIDER_CONFIG.showCanBeHarvested;
    }

    @Override
    public ConfigMode getShowCropPercentage() {
        return Config.PROVIDER_CONFIG.showCropPercentage;
    }

    @Override
    public ConfigMode getShowChestContents() {
        return Config.PROVIDER_CONFIG.showChestContents;
    }

    @Override
    public ConfigMode getShowChestContentsDetailed() {
        return Config.PROVIDER_CONFIG.showChestContentsDetailed;
    }

    @Override
    public ConfigMode getShowRedstone() {
        return Config.PROVIDER_CONFIG.showRedstone;
    }

    @Override
    public ConfigMode getShowMobHealth() {
        return Config.PROVIDER_CONFIG.showMobHealth;
    }

    @Override
    public ConfigMode getShowLeverSetting() {
        return Config.PROVIDER_CONFIG.showLeverSetting;
    }

    @Override
    public ConfigMode getShowTankSetting() {
        return Config.PROVIDER_CONFIG.showTankSetting;
    }

    @Override
    public ConfigMode getShowMusicBlock() {
        return Config.PROVIDER_CONFIG.showMusicBlock;
    }

    @Override
    public ConfigMode getShowSignText() {
        return Config.PROVIDER_CONFIG.showSignText;
    }
}
