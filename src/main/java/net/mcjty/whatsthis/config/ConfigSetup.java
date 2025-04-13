package net.mcjty.whatsthis.config;


import net.glasslauncher.mods.gcapi3.api.GCAPI;
import net.glasslauncher.mods.gcapi3.impl.GlassYamlFile;
import net.mcjty.whatsthis.WhatsThis;
import net.mcjty.whatsthis.api.IOverlayStyle;
import net.mcjty.whatsthis.api.IProbeConfig;
import net.mcjty.whatsthis.api.TextStyleClass;
import net.mcjty.whatsthis.apiimpl.ProbeConfig;
import net.mcjty.whatsthis.apiimpl.styles.DefaultOverlayStyle;
import net.mcjty.whatsthis.rendering.ProbeTextRenderer;
import net.modificationstation.stationapi.api.util.Formatting;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static net.mcjty.whatsthis.api.TextStyleClass.*;

// TODO: Move this to config
@SuppressWarnings("StringConcatenationArgumentToLogCall")
public class ConfigSetup {
    public static final int PROBE_NOTNEEDED = 0;
    public static final int PROBE_NEEDED = 1;
    public static final int PROBE_NEEDEDHARD = 2;
    public static final int PROBE_NEEDEDFOREXTENDED = 3;

    private static Set<Identifier> inventoriesToShow = null;
    private static Set<Identifier> inventoriesToNotShow = null;
    private static Set<Identifier> dontSendNBTSet = null;

    public static Map<TextStyleClass, String> defaultTextStyleClasses = new HashMap<>();
    public static Map<TextStyleClass, String> textStyleClasses = new HashMap<>();

    static {
        defaultTextStyleClasses.put(NAME, "white");
        defaultTextStyleClasses.put(MODNAME, "blue");
        defaultTextStyleClasses.put(ERROR, "red,bold");
        defaultTextStyleClasses.put(WARNING, "yellow");
        defaultTextStyleClasses.put(OK, "green");
        defaultTextStyleClasses.put(INFO, "white");
        defaultTextStyleClasses.put(INFOIMP, "blue");
        defaultTextStyleClasses.put(OBSOLETE, "gray,strikethrough");
        defaultTextStyleClasses.put(LABEL, "gray");
        defaultTextStyleClasses.put(PROGRESS, "white");
        textStyleClasses = new HashMap<>(defaultTextStyleClasses);
    }

    public static int loggingThrowableTimeout = 20000;

    private static IOverlayStyle defaultOverlayStyle;
    private static ProbeConfig probeConfig = new ProbeConfig();

    public static ProbeConfig getProbeConfig() {
        return probeConfig;
    }
    
    public static void setProbeConfig(IProbeConfig config) {
        probeConfig = (ProbeConfig) config;
    }

    public static void setTextStyle(Map<TextStyleClass, String> defaultStyle, Map<TextStyleClass, String> style) {
        HashMap<String, Object> toSave = new HashMap<>();

        // First load the defaults
        textStyleClasses.putAll(defaultStyle);
        for (Map.Entry<TextStyleClass, String> styleClass : defaultStyle.entrySet()) {
            toSave.put("textStyle" + styleClass.getKey().getReadableName(), styleClass.getValue());
        }
        
        // After loading the defaults, overwrite any changed values
        textStyleClasses.putAll(style);
        for (Map.Entry<TextStyleClass, String> styleClass : style.entrySet()) {
            toSave.put("textStyle" + styleClass.getKey().getReadableName(), styleClass.getValue());
        }

        setConfigValues("whatsthis:client", toSave);
    }
    
    public static void setTextStyle(Map<TextStyleClass, String> style) {
        HashMap<String, Object> toSave = new HashMap<>();
        
        textStyleClasses.putAll(style);
        for (Map.Entry<TextStyleClass, String> styleClass : style.entrySet()) {
            toSave.put("textStyle" + styleClass.getKey().getReadableName(), styleClass.getValue());
        }
        
        setConfigValues("whatsthis:client", toSave);
    }
    

    public static void setConfigValue(String configId, String key, Object value) {
        GlassYamlFile yamlFile = new GlassYamlFile();
        yamlFile.set(key, value);
        GCAPI.reloadConfig(configId, yamlFile);
    }
    
    public static void setConfigValues(String configId, HashMap<String, Object> values) {
        GlassYamlFile yamlFile = new GlassYamlFile();
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            yamlFile.set(entry.getKey(), entry.getValue());
        }
        GCAPI.reloadConfig(configId, yamlFile);
    }

    public static void setProbeNeeded(int needsProbe) {
        setConfigValue("whatsthis:config", "needsProbe", needsProbe);
    }
    
    public static void setExtendedInMain(boolean extendedInMain) {
        setConfigValue("whatsthis:client", "extendedInMain", extendedInMain);
    }

    public static void setLiquids(boolean showLiquids) {
        setConfigValue("whatsthis:client", "showLiquids", showLiquids);
    }

    public static void setVisible(boolean isVisible) {
        setConfigValue("whatsthis:client", "isVisible", isVisible);
    }

    public static void setCompactEqualStacks(boolean compactEqualStacks) {
        setConfigValue("whatsthis:main", "compactEqualStacks", compactEqualStacks);
    }

    public static void setPos(int leftx, int topy, int rightx, int bottomy) {
        setConfigValues("whatsthis:client", new HashMap<>(){{
            put("leftX", leftx);
            put("topY", topy);
            put("rightX", rightx);
            put("bottomY", bottomy);
        }});
        updateDefaultOverlayStyle();
    }

    public static void setTooltipScale(float scale) {
        scale = MathHelper.clamp(scale, 0.75F, 2.5F);
        System.err.println(scale);
        setConfigValue("whatsthis:client", "tooltipScale", scale);
        updateDefaultOverlayStyle();
    }

    public static void setBoxStyle(int thickness, int borderColor, int fillcolor, int offset) {
        setConfigValues("whatsthis:client", new HashMap<>(){{
            put("boxThickness", thickness);
            put("boxBorderColor", Integer.toHexString(borderColor));
            put("boxFillColor", Integer.toHexString(fillcolor));
            put("boxOffset", offset);
        }});
        updateDefaultOverlayStyle();
    }

    private static String convertFormatting(String input) {
        switch (input) {
            case "black" -> {
                return Formatting.BLACK.toString();
            }
            case "dark_blue" -> {
                return Formatting.DARK_BLUE.toString();
            }
            case "dark_green" -> {
                return Formatting.DARK_GREEN.toString();
            }
            case "dark_aqua" -> {
                return Formatting.DARK_AQUA.toString();
            }
            case "dark_red" -> {
                return Formatting.DARK_RED.toString();
            }
            case "dark_purple" -> {
                return Formatting.DARK_PURPLE.toString();
            }
            case "gold" -> {
                return Formatting.GOLD.toString();
            }
            case "gray" -> {
                return Formatting.GRAY.toString();
            }
            case "dark_gray" -> {
                return Formatting.DARK_GRAY.toString();
            }
            case "blue" -> {
                return Formatting.BLUE.toString();
            }
            case "green" -> {
                return Formatting.GREEN.toString();
            }
            case "aqua" -> {
                return Formatting.AQUA.toString();
            }
            case "red" -> {
                return Formatting.RED.toString();
            }
            case "light_purple" -> {
                return Formatting.LIGHT_PURPLE.toString();
            }
            case "yellow" -> {
                return Formatting.YELLOW.toString();
            }
            case "white" -> {
                return Formatting.WHITE.toString();
            }
            case "bold" -> {
                return ProbeTextRenderer.BOLD;    
            }
            case "italic" -> {
                return ProbeTextRenderer.ITALICS;
            }
            case "underline" -> {
                return ProbeTextRenderer.UNDERLINE;
            }
            case "strikethrough" -> {
                return ProbeTextRenderer.STRIKETHROUGH;
            }
            case "obfuscated" -> {
                return ProbeTextRenderer.OBFUSCATED;
            }
            
            default -> {
                WhatsThis.LOGGER.warn("Unhandled formatting: " + input);
                return Formatting.WHITE.toString();
            }
        }
    }

    private static String configToTextFormat(String input) {
        if ("context".equals(input)) {
            return "context";
        }
        
        StringBuilder builder = new StringBuilder();
        String[] splitted = StringUtils.split(input, ',');
        for (String s : splitted) {
            String format = convertFormatting(s);// Formatting.valueOf(s);
            if (format != null) {
                builder.append(format);
            }
        }
        return builder.toString();
    }

    public static String getTextStyle(TextStyleClass styleClass) {
        if (textStyleClasses.containsKey(styleClass)) {
            return configToTextFormat(textStyleClasses.get(styleClass));
        }
        return "";
    }

    private static int parseColor(String col) {
        try {
            return (int) Long.parseLong(col, 16);
        } catch (NumberFormatException e) {
            System.out.println("Config.parseColor");
            return 0;
        }
    }

    public static void updateDefaultOverlayStyle() {
        defaultOverlayStyle = new DefaultOverlayStyle()
                .borderThickness(Config.CLIENT_CONFIG.boxThickness)
                .borderColor(Config.parseColor(Config.CLIENT_CONFIG.boxBorderColor))
                .boxColor(Config.parseColor(Config.CLIENT_CONFIG.boxFillColor))
                .borderOffset(Config.CLIENT_CONFIG.boxOffset)
                .location(Config.CLIENT_CONFIG.leftX, Config.CLIENT_CONFIG.rightX, Config.CLIENT_CONFIG.topY, Config.CLIENT_CONFIG.bottomY);
    }

    public static IOverlayStyle getDefaultOverlayStyle() {
        if (defaultOverlayStyle == null) {
            updateDefaultOverlayStyle();
        }
        return defaultOverlayStyle;
    }

    public static Set<Identifier> getInventoriesToShow() {
        if (inventoriesToShow == null) {
            inventoriesToShow = new HashSet<>();
            for (String s : Config.MAIN_CONFIG.showContentsWithoutSneaking) {
                inventoriesToShow.add(Identifier.of(s));
            }
        }
        return inventoriesToShow;
    }

    public static Set<Identifier> getInventoriesToNotShow() {
        if (inventoriesToNotShow == null) {
            inventoriesToNotShow = new HashSet<>();
            for (String s : Config.MAIN_CONFIG.dontShowContentsUnlessSneaking) {
                inventoriesToNotShow.add(Identifier.of(s));
            }
        }
        return inventoriesToNotShow;
    }

    public static Set<Identifier> getDontSendNBTSet() {
        if (dontSendNBTSet == null) {
            dontSendNBTSet = new HashSet<>();
            for (String s : Config.MAIN_CONFIG.dontSendNBT) {
                dontSendNBTSet.add(Identifier.of(s));
            }
        }
        return dontSendNBTSet;
    }
}
