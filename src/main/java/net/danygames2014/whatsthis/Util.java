package net.danygames2014.whatsthis;

import net.danygames2014.whatsthis.api.IProbeConfig;
import net.danygames2014.whatsthis.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.util.Identifier;
import org.apache.commons.lang3.text.WordUtils;

import static net.danygames2014.whatsthis.api.IProbeConfig.ConfigMode.EXTENDED;
import static net.danygames2014.whatsthis.api.IProbeConfig.ConfigMode.NORMAL;

public class Util {
    public static Entity getEntity(World world, int entityId) {
        for (var entity : world.entities) {
            if (((Entity) entity).id == entityId) {
                return (Entity) entity;
            }
        }
        return null;
    }

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

    @SuppressWarnings("deprecation")
    public static String formatModName(String input) {
        return WordUtils.capitalizeFully(input);
    }

    public static boolean show(ProbeMode mode, IProbeConfig.ConfigMode cfg) {
        return cfg == NORMAL || (cfg == EXTENDED && mode == ProbeMode.EXTENDED) || mode == ProbeMode.DEBUG;
    }
}
