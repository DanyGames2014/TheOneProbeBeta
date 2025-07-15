package net.danygames2014.whatsthis.config;

import net.danygames2014.whatsthis.api.NumberFormat;
import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class ProbeConfig {
    // General Probe Settings
    @ConfigEntry(name = "Probe Requirement", minValue = 0, maxValue = 3, comment = "Is the probe needed to show the tooltip? 0 = no, 1 = yes, 2 = yes and clients cannot override, 3 = probe needed for extended info only")
    public Integer needsProbe = ConfigSetup.PROBE_NEEDEDFOREXTENDED;

    @ConfigEntry(name = "Probe Distance", minValue = 1, maxValue = 50, comment = "Distance at which the probe works")
    public Float probeDistance = 6F;

    @ConfigEntry(name = "Show Debug Info", comment = "If true show debug info with creative probe")
    public Boolean showDebugInfo = true;

    // Note Settings    
    @ConfigEntry(name = "Get Note on Spawn", comment = "If true there will be a readme note for first-time players")
    public Boolean spawnNote = true;

    // Compat Settings
    @ConfigEntry(name = "Accessory API Support", comment = "If true there will be a bauble version of the probe if baubles is present")
    public Boolean supportAccessoryApi = true;

    // Networking Settings
    @ConfigEntry(name = "Probe Refresh Rate", minValue = 100, maxValue = 100000, comment = "The amount of milliseconds to wait before updating probe information from the server (this is a client-side config)")
    public Integer timeout = 300;

    @ConfigEntry(name = "Server Waiting Timeout", minValue = -1, maxValue = 100000, comment = "The amount of milliseconds to wait before showing a 'fetch from server' info on the client (if the server is slow to respond) (-1 to disable this feature)")
    public Integer waitingForServerTimeout = 2000;

    // Logging Throwable Timeout
    @ConfigEntry(name = "Logging Throwable Timeout", minValue = 1, maxValue = 10000000, comment = "How much time (ms) to wait before reporting an exception again")
    public Integer loggingThrowableTimeout = 20000;

    // RF Display Settings
    @ConfigEntry(name = "RF Display Style", minValue = 0, maxValue = 2, comment = "How to display RF: 0 = do not show, 1 = show in a bar, 2 = show as text")
    public Integer showRF = 1;

    @ConfigEntry(name = "RF Number Format", minValue = 0, maxValue = 2, comment = "Format for displaying RF: 0 = full, 1 = compact, 2 = comma separated")
    public NumberFormat rfFormat = NumberFormat.COMPACT;

    @ConfigEntry(name = "RF Bar Filled Color", comment = "Color for the RF bar")
    public String rfbarFilledColor = Integer.toHexString(0xffdd0000);

    @ConfigEntry(name = "RF Bar Alternate Filled Color", comment = "Alternate color for the RF bar")
    public String rfbarAlternateFilledColor = Integer.toHexString(0xff430000);

    @ConfigEntry(name = "RF Bar Border Color", comment = "Color for the RF bar border")
    public String rfbarBorderColor = Integer.toHexString(0xff555555);

    // Tank Display Settings
    @ConfigEntry(name = "Fluid Tank Display Style", minValue = 0, maxValue = 2, comment = "How to display tank contents: 0 = do not show, 1 = show in a bar, 2 = show as text")
    public Integer showTank = 1;

    @ConfigEntry(name = "Fluid Tank Number Format", minValue = 0, maxValue = 2, comment = "Format for displaying tank contents: 0 = full, 1 = compact, 2 = comma separated")
    public NumberFormat tankFormat = NumberFormat.COMPACT;

    @ConfigEntry(name = "Fluid Tank Bar Filled Color", comment = "Color for the tank bar")
    public String tankbarFilledColor = Integer.toHexString(0xff0000dd);

    @ConfigEntry(name = "Fluid Tank Alternate Filled Color", comment = "Alternate color for the tank bar")
    public String tankbarAlternateFilledColor = Integer.toHexString(0xff000043);

    @ConfigEntry(name = "Fluid Tank Bar Border Color", comment = "Color for the tank bar border")
    public String tankbarBorderColor = Integer.toHexString(0xff555555);

    // Inventory Settings
    @ConfigEntry(name = "Item Detail Threshold", minValue = 0, maxValue = 20, comment = "If the number of items in an inventory is lower or equal then this number then more info is shown")
    public Integer showItemDetailThreshold = 4;

    @ConfigEntry(name = "showSmallChestContentsWithoutSneaking", minValue = 0, maxValue = 1000, comment = "The maximum amount of slots (empty or not) to show without sneaking")
    public Integer showSmallChestContentsWithoutSneaking = 3;

    @ConfigEntry(name = "showContentsWithoutSneaking", maxArrayLength = 128, comment = "A list of blocks for which we automatically show chest contents even if not sneaking")
    public String[] showContentsWithoutSneaking = new String[]{"minecraft:chest"};

    @ConfigEntry(name = "dontShowContentsUnlessSneaking", comment = "A list of blocks for which we don't show chest contents automatically except if sneaking")
    public String[] dontShowContentsUnlessSneaking = new String[]{};

    @ConfigEntry(name = "dontSendNBT", comment = "A list of blocks for which we don't send NBT over the network. This is mostly useful for blocks that have HUGE NBT in their pickblock (itemstack)")
    public String[] dontSendNBT = new String[]{};
}
