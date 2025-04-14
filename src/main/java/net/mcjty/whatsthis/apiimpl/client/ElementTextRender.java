package net.mcjty.whatsthis.apiimpl.client;

import net.mcjty.whatsthis.api.TextStyleClass;
import net.mcjty.whatsthis.config.ConfigSetup;
import net.mcjty.whatsthis.rendering.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resource.language.I18n;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;
import java.util.Set;

import static net.mcjty.whatsthis.api.IProbeInfo.ENDLOC;
import static net.mcjty.whatsthis.api.IProbeInfo.STARTLOC;

public class ElementTextRender {

    public static void render(String text, int x, int y) {
        RenderHelper.renderText(Minecraft.INSTANCE, x, y, stylifyString(text));
    }

    private static String stylifyString(String text) {
        while (text.contains(STARTLOC) && text.contains(ENDLOC)) {
            int start = text.indexOf(STARTLOC);
            int end = text.indexOf(ENDLOC);
            if (start < end) {
                // Translation is needed
                String left = text.substring(0, start);
                String middle = text.substring(start + 2, end);
                middle = I18n.getTranslation(middle).trim();
                //middle = I18n.format(middle).trim();
                String right = text.substring(end + 2);
                text = left + middle + right;
            } else {
                break;
            }
        }
        if (text.contains("{=")) {
            Set<TextStyleClass> stylesNeedingContext = EnumSet.noneOf(TextStyleClass.class);
            TextStyleClass context = null;
            for (TextStyleClass styleClass : ConfigSetup.textStyleClasses.keySet()) {
                if (text.contains(styleClass.toString())) {
                    String replacement = ConfigSetup.getTextStyle(styleClass);
                    if ("context".equals(replacement)) {
                        stylesNeedingContext.add(styleClass);
                    } else if (context == null) {
                        context = styleClass;
                        text = StringUtils.replace(text, styleClass.toString(), replacement);
                    } else {
                        text = StringUtils.replace(text, styleClass.toString(), replacement);
                    }
                }
            }
            if (context != null) {
                for (TextStyleClass styleClass : stylesNeedingContext) {
                    String replacement = ConfigSetup.getTextStyle(context);
                    text = StringUtils.replace(text, styleClass.toString(), replacement);
                }
            }
        }
        return text;
    }

    public static int getWidth(String text) {
        return Minecraft.INSTANCE.textRenderer.getWidth(stylifyString(text));
    }
}
