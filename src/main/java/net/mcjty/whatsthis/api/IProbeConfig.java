package net.mcjty.whatsthis.api;

/**
 * This inerface represents the default config for The One Probe.
 */
public interface IProbeConfig {

    enum ConfigMode {
        NOT,            // Don't show
        NORMAL,         // Show
        EXTENDED        // Show only when sneaking
    }

    /**
     * Control how RF should be shown
     * 0 = not, 1 = show as bar, 2 = show as text
     */
    int getRFMode();

    /**
     * Control how Liquid should be shown
     * 0 = not, 1 = show as bar, 2 = show as text
     */
    int getTankMode();

    /**
     * Lever setting is also used for other technical information like the
     * comparator mode and repeater delay
     */
    ConfigMode getShowLeverSetting();

    ConfigMode getAnimalOwnerSetting();

    ConfigMode getShowMobSpawnerSetting();

    ConfigMode getShowTankSetting();

    ConfigMode getShowModName();

    ConfigMode getShowHarvestLevel();

    ConfigMode getShowCanBeHarvested();

    ConfigMode getShowCropPercentage();

    ConfigMode getShowChestContents();

    // This controls when detailed chest info is shown in case the amount of items is below showItemDetailThresshold
    ConfigMode getShowChestContentsDetailed();

    ConfigMode getShowRedstone();

    ConfigMode getShowMobHealth();
}
