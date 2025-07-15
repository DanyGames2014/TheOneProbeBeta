package net.danygames2014.whatsthis.config;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class ClientConfig {
    @ConfigEntry(name = "isVisible", comment = "Toggle default probe visibility (client can override)")
    public Boolean isVisible = true;

    @ConfigEntry(name = "holdKeyToMakeVisible", comment = "If true then the probe hotkey must be held down to show the tooltip")
    public Boolean holdKeyToMakeVisible = false;

    @ConfigEntry(name = "showLiquids", comment = "If true show liquid information when the probe hits liquid first")
    public Boolean showLiquids = true;

    @ConfigEntry(name = "extendedInMain", comment = "If true the probe will automatically show extended information if it is in your main hand (so not required to sneak)")
    public Boolean extendedInMain = false;

    @ConfigEntry(name = "showBreakProgress", minValue = 0, maxValue = 2, comment = "0 means don't show break progress, 1 is show as bar, 2 is show as text")
    public Integer showBreakProgress = 1;

    @ConfigEntry(name = "harvestStyleVanilla", comment = "true means shows harvestability with vanilla style icons")
    public Boolean harvestStyleVanilla = true;

    @ConfigEntry(name = "compactEqualStacks", comment = "If true equal stacks will be compacted in the chest contents overlay")
    public Boolean compactEqualStacks = true;

    // Tooltip Scale & Position
    @ConfigEntry(name = "tooltipScale", minValue = 0, maxValue = 5, comment = "The scale of the tooltips, 1 is default, 2 is smaller")
    public Float tooltipScale = 1.0F;

    @ConfigEntry(name = "leftX", minValue = -1, maxValue = 10000, comment = "The distance to the left side of the screen. Use -1 if you don't want to set this")
    public Integer leftX = -1;

    @ConfigEntry(name = "rightX", minValue = -1, maxValue = 10000, comment = "The distance to the right side of the screen. Use -1 if you don't want to set this")
    public Integer rightX = -1;

    @ConfigEntry(name = "topY", minValue = -1, maxValue = 10000, comment = "The distance to the top side of the screen. Use -1 if you don't want to set this")
    public Integer topY = 5;

    @ConfigEntry(name = "bottomY", minValue = -1, maxValue = 10000, comment = "The distance to the bottom side of the screen. Use -1 if you don't want to set this")
    public Integer bottomY = -1;

    // Box Styles
    @ConfigEntry(name = "boxOffset", minValue = 0, maxValue = 20, comment = "How much the border should be offset (i.e. to create an 'outer' border)")
    public Integer boxOffset = 0;

    @ConfigEntry(name = "boxThickness", minValue = 0, maxValue = 20, comment = "Thickness of the border of the box (0 to disable)")
    public Integer boxThickness = 2;

    @ConfigEntry(name = "boxBorderColor", comment = "Color of the border of the box (0 to disable)")
    public String boxBorderColor = Integer.toHexString(0xff999999);

    @ConfigEntry(name = "boxFillColor", comment = "Color of the box (0 to disable)")
    public String boxFillColor = Integer.toHexString(0x55006699);

    @ConfigEntry(name = "chestContentsBorderColor", comment = "Color of the border of the chest contents box (0 to disable)")
    public String chestContentsBorderColor = Integer.toHexString(0xff006699);

    // Text Styles
    @ConfigEntry(name = "textStyleName", comment = "Text style. Use a comma delimited list with colors like: 'red', 'green', 'blue', ... or style codes like 'underline', 'bold', 'italic', 'strikethrough', ...")
    public String textStyleName = "white";

    @ConfigEntry(name = "textStyleModName", comment = "Text style. Use a comma delimited list with colors like: 'red', 'green', 'blue', ... or style codes like 'underline', 'bold', 'italic', 'strikethrough', ...")
    public String textStyleModName = "blue";

    @ConfigEntry(name = "textStyleLabel", comment = "Text style. Use a comma delimited list with colors like: 'red', 'green', 'blue', ... or style codes like 'underline', 'bold', 'italic', 'strikethrough', ...")
    public String textStyleLabel = "gray";

    @ConfigEntry(name = "textStyleOk", comment = "Text style. Use a comma delimited list with colors like: 'red', 'green', 'blue', ... or style codes like 'underline', 'bold', 'italic', 'strikethrough', ...")
    public String textStyleOk = "green";

    @ConfigEntry(name = "textStyleInfo", comment = "Text style. Use a comma delimited list with colors like: 'red', 'green', 'blue', ... or style codes like 'underline', 'bold', 'italic', 'strikethrough', ...")
    public String textStyleInfo = "white";

    @ConfigEntry(name = "textStyleInfoImportant", comment = "Text style. Use a comma delimited list with colors like: 'red', 'green', 'blue', ... or style codes like 'underline', 'bold', 'italic', 'strikethrough', ...")
    public String textStyleInfoImportant = "blue";

    @ConfigEntry(name = "textStyleWarning", comment = "Text style. Use a comma delimited list with colors like: 'red', 'green', 'blue', ... or style codes like 'underline', 'bold', 'italic', 'strikethrough', ...")
    public String textStyleWarning = "yellow";

    @ConfigEntry(name = "textStyleError", comment = "Text style. Use a comma delimited list with colors like: 'red', 'green', 'blue', ... or style codes like 'underline', 'bold', 'italic', 'strikethrough', ...")
    public String textStyleError = "red,bold";

    @ConfigEntry(name = "textStyleObsolete", comment = "Text style. Use a comma delimited list with colors like: 'red', 'green', 'blue', ... or style codes like 'underline', 'bold', 'italic', 'strikethrough', ...")
    public String textStyleObsolete = "gray,strikethrough";

    @ConfigEntry(name = "textStyleProgress", comment = "Text style. Use a comma delimited list with colors like: 'red', 'green', 'blue', ... or style codes like 'underline', 'bold', 'italic', 'strikethrough', ...")
    public String textStyleProgress = "white";
}
