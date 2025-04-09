package net.mcjty.whatsthis;

import jdk.dynalink.beans.StaticClass;
import net.mcjty.whatsthis.api.IProbeConfig;
import net.mcjty.whatsthis.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityRegistry;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.util.Formatting;
import net.modificationstation.stationapi.api.util.Identifier;
import org.apache.commons.lang3.text.WordUtils;

import static net.mcjty.whatsthis.api.IProbeConfig.ConfigMode.EXTENDED;
import static net.mcjty.whatsthis.api.IProbeConfig.ConfigMode.NORMAL;

public class Tools {
    private static String modName = "";

    public static String getModName(Block block) {
        Identifier identifier = BlockRegistry.INSTANCE.getId(block);

        if (identifier == null) {
            modName = "Minecraft";
        } else {
            modName = identifier.namespace.toString();
        }

        return formatModName(modName);
    }

    public static String getModName(Entity entity) {
        String[] enttityName = EntityRegistry.getId(entity).split(":");

        if (enttityName.length <= 1) {
            modName = "Minecraft";
        } else {
            modName = enttityName[1];
        }

        return formatModName(modName);
    }
    
    public static String formatModName(String input) {
        return Formatting.BLUE + WordUtils.capitalize(input);
    }

    public static boolean show(ProbeMode mode, IProbeConfig.ConfigMode cfg) {
        return cfg == NORMAL || (cfg == EXTENDED && mode == ProbeMode.EXTENDED);
    }
}
