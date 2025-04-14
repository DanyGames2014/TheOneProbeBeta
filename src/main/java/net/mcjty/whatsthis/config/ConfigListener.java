package net.mcjty.whatsthis.config;

import net.glasslauncher.mods.gcapi3.api.PostConfigLoadedListener;
import net.mcjty.whatsthis.api.TextStyleClass;

import java.util.HashMap;
import java.util.Map;

import static net.mcjty.whatsthis.api.TextStyleClass.*;

public class ConfigListener implements PostConfigLoadedListener {
    @Override
    public void PostConfigLoaded(int source) {
        Map<TextStyleClass, String> styles = new HashMap<>();

        styles.put(NAME, Config.CLIENT_CONFIG.textStyleName);
        styles.put(MODNAME, Config.CLIENT_CONFIG.textStyleModName);
        styles.put(LABEL, Config.CLIENT_CONFIG.textStyleLabel);
        styles.put(OK, Config.CLIENT_CONFIG.textStyleOk);
        styles.put(INFO, Config.CLIENT_CONFIG.textStyleInfo);
        styles.put(INFOIMP, Config.CLIENT_CONFIG.textStyleInfoImportant);
        styles.put(WARNING, Config.CLIENT_CONFIG.textStyleWarning);
        styles.put(ERROR, Config.CLIENT_CONFIG.textStyleError);
        styles.put(OBSOLETE, Config.CLIENT_CONFIG.textStyleObsolete);
        styles.put(PROGRESS, Config.CLIENT_CONFIG.textStyleProgress);

        ConfigSetup.textStyleClasses = styles;
    }
}
