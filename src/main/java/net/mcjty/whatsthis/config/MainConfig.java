package net.mcjty.whatsthis.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;
import net.glasslauncher.mods.gcapi3.impl.ConfigRootEntry;
import net.glasslauncher.mods.gcapi3.impl.EventStorage;
import net.glasslauncher.mods.gcapi3.impl.GCCore;
import net.mcjty.whatsthis.api.NumberFormat;

public class MainConfig {
    @ConfigEntry(name = "loggingThrowableTimeout", minLength = 1, maxLength = 10000000, comment = "How much time (ms) to wait before reporting an exception again")
    public Integer loggingThrowableTimeout = 20000;

    @ConfigEntry(name = "needsProbe", minLength = 0, maxLength = 3, comment = "Is the probe needed to show the tooltip? 0 = no, 1 = yes, 2 = yes and clients cannot override, 3 = probe needed for extended info only")
    public Integer needsProbe = ConfigSetup.PROBE_NEEDEDFOREXTENDED;

    @ConfigEntry(name = "extendedInMain", comment = "If true the probe will automatically show extended information if it is in your main hand (so not required to sneak)")
    public Boolean extendedInMain = false;

    @ConfigEntry(name = "supportBaubles", comment = "If true there will be a bauble version of the probe if baubles is present")
    public Boolean supportBaubles = true;

    @ConfigEntry(name = "spawnNote", comment = "If true there will be a readme note for first-time players")
    public Boolean spawnNote = true;

    @ConfigEntry(name = "showRF", minLength = 0, maxLength = 2, comment = "How to display RF: 0 = do not show, 1 = show in a bar, 2 = show as text")
    public Integer showRF = 1;

    @ConfigEntry(name = "showTank", minLength = 0, maxLength = 2, comment = "How to display tank contents: 0 = do not show, 1 = show in a bar, 2 = show as text")
    public Integer showTank = 1;

    @ConfigEntry(name = "rfFormat", minLength = 0, maxLength = 2, comment = "Format for displaying RF: 0 = full, 1 = compact, 2 = comma separated")
    public NumberFormat rfFormat = NumberFormat.COMPACT;

    @ConfigEntry(name = "tankFormat", minLength = 0, maxLength = 2, comment = "Format for displaying tank contents: 0 = full, 1 = compact, 2 = comma separated")
    public NumberFormat tankFormat = NumberFormat.COMPACT;

    @ConfigEntry(name = "timeout", minLength = 100, maxLength = 100000, comment = "The amount of milliseconds to wait before updating probe information from the server (this is a client-side config)")
    public Integer timeout = 300;

    @ConfigEntry(name = "waitingForServerTimeout", minLength = -1, maxLength = 100000, comment = "The amount of milliseconds to wait before showing a 'fetch from server' info on the client (if the server is slow to respond) (-1 to disable this feature)")
    public Integer waitingForServerTimeout = 2000;

    @ConfigEntry(name = "maxPacketToServer", minLength = -1, maxLength = 32768, comment = "The maximum packet size to send an itemstack from client to server. Reduce this if you have issues with network lag caused by TOP")
    public Integer maxPacketToServer = 20000;

    @ConfigEntry(name = "probeDistance", minLength = 1, maxLength = 50, comment = "Distance at which the probe works")
    public Float probeDistance = 6F;

    @ConfigEntry(name = "showDebugInfo", comment = "If true show debug info with creative probe")
    public Boolean showDebugInfo = true;

    @ConfigEntry(name = "rfbarFilledColor", comment = "Color for the RF bar")
    public String rfbarFilledColor = Integer.toHexString(0xffdd0000);

    @ConfigEntry(name = "rfbarAlternateFilledColor", comment = "Alternate color for the RF bar")
    public String rfbarAlternateFilledColor = Integer.toHexString(0xff430000);

    @ConfigEntry(name = "rfbarBorderColor", comment = "Color for the RF bar border")
    public String rfbarBorderColor = Integer.toHexString(0xff555555);

    @ConfigEntry(name = "tankbarFilledColor", comment = "Color for the tank bar")
    public String tankbarFilledColor = Integer.toHexString(0xff0000dd);

    @ConfigEntry(name = "tankbarAlternateFilledColor", comment = "Alternate color for the tank bar")
    public String tankbarAlternateFilledColor = Integer.toHexString(0xff000043);

    @ConfigEntry(name = "tankbarBorderColor", comment = "Color for the tank bar border")
    public String tankbarBorderColor = Integer.toHexString(0xff555555);

    @ConfigEntry(name = "showItemDetailThreshold", minLength = 0, maxLength = 20, comment = "If the number of items in an inventory is lower or equal then this number then more info is shown")
    public Integer showItemDetailThreshold = 4;

    @ConfigEntry(name = "showSmallChestContentsWithoutSneaking", minLength = 0, maxLength = 1000, comment = "The maximum amount of slots (empty or not) to show without sneaking")
    public Integer showSmallChestContentsWithoutSneaking = 3;

    @ConfigEntry(name = "showContentsWithoutSneaking", maxLength = 6969, comment = "A list of blocks for which we automatically show chest contents even if not sneaking")
    public String[] showContentsWithoutSneaking = new String[]{"storagedrawers:basicDrawers", "storagedrawersextra:extra_drawers"};

    @ConfigEntry(name = "dontShowContentsUnlessSneaking", comment = "A list of blocks for which we don't show chest contents automatically except if sneaking")
    public String[] dontShowContentsUnlessSneaking = new String[]{};

    @ConfigEntry(name = "dontSendNBT", comment = "A list of blocks for which we don't send NBT over the network. This is mostly useful for blocks that have HUGE NBT in their pickblock (itemstack)")
    public String[] dontSendNBT = new String[]{};
}
