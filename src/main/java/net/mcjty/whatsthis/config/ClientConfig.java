package net.mcjty.whatsthis.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class ClientConfig {
    @ConfigEntry(name = "leftX", minLength = -1, maxLength = 10000, comment = "The distance to the left side of the screen. Use -1 if you don't want to set this")
    public Integer leftX = 5;

    @ConfigEntry(name = "leftX", minLength = -1, maxLength = 10000, comment = "The distance to the right side of the screen. Use -1 if you don't want to set this")
    public Integer rightX = -1;

    @ConfigEntry(name = "leftX", minLength = -1, maxLength = 10000, comment = "The distance to the top side of the screen. Use -1 if you don't want to set this")
    public Integer topY = 5;

    @ConfigEntry(name = "leftX", minLength = -1, maxLength = 10000, comment = "The distance to the bottom side of the screen. Use -1 if you don't want to set this")
    public Integer bottomY = -1;
    
    @ConfigEntry(name = "boxBorderColor", comment = "Color of the border of the box (0 to disable)")
    public String boxBorderColor = Integer.toHexString(0xff999999);
    
    @ConfigEntry(name = "boxFillColor", comment = "Color of the box (0 to disable)")
    public String boxFillColor = Integer.toHexString(0x55006699);
    
    @ConfigEntry(name = "boxThickness", minLength = 0, maxLength = 20, comment = "Thickness of the border of the box (0 to disable)")
    public Integer boxThickness = 2;
    
    @ConfigEntry(name = "boxOffset", minLength = 0, maxLength = 20, comment = "How much the border should be offset (i.e. to create an 'outer' border)")
    public Integer boxOffset = 0;
    
    @ConfigEntry(name = "showLiquids", comment = "If true show liquid information when the probe hits liquid first")
    public Boolean showLiquids = true;
    
    @ConfigEntry(name = "isVisible", comment = "Toggle default probe visibility (client can override)")
    public Boolean isVisible = true;
    
    @ConfigEntry(name = "holdKeyToMakeVisible", comment = "If true then the probe hotkey must be held down to show the tooltip")
    public Boolean holdKeyToMakeVisible = false;
    
    @ConfigEntry(name = "compactEqualStacks", comment = "If true equal stacks will be compacted in the chest contents overlay")
    public Boolean compactEqualStacks = true;
    
    @ConfigEntry(name = "tooltipScale", minLength = 0, maxLength = 5, comment = "The scale of the tooltips, 1 is default, 2 is smaller")
    public Float tooltipScale = 1.0F;
    
    @ConfigEntry(name = "chestContentsBorderColor", comment = "Color of the border of the chest contents box (0 to disable)")
    public String chestContentsBorderColor = Integer.toHexString(0xff006699);
    
    @ConfigEntry(name = "showBreakProgress", minLength = 0, maxLength = 2, comment = "0 means don't show break progress, 1 is show as bar, 2 is show as text")
    public Integer showBreakProgress = 1;
    
    @ConfigEntry(name = "harvestStyleVanilla", comment = "true means shows harvestability with vanilla style icons")
    public Boolean harvestStyleVanilla = true;
    
    // TODO: Text Style Classes
    
    @ConfigEntry(name = "extendedInMain", comment = "If true the probe will automatically show extended information if it is in your main hand (so not required to sneak)")
    public Boolean extendedInMain = false;
}
