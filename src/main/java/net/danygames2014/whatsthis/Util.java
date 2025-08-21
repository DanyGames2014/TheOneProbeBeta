package net.danygames2014.whatsthis;

import net.danygames2014.whatsthis.api.IProbeConfig;
import net.danygames2014.whatsthis.api.ProbeMode;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.HashMap;
import java.util.Optional;

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

    public static String getModName(Block block) {
        Identifier identifier = BlockRegistry.INSTANCE.getId(block);

        if (identifier == null) {
            return "Minecraft";
        } else {
            return getModName(identifier.namespace.toString());
        }
    }

    public static String getModName(Entity entity) {
        String[] enttityName = EntityRegistry.getId(entity).split(":");

        if (enttityName.length <= 1) {
            return "Minecraft";
        } else {
            return getModName(enttityName[1]);
        }
    }

    public static HashMap<String, String> modIds = new HashMap<>();

    public static String getModName(String modId) {
        return modIds.computeIfAbsent(modId, Util::fetchModName);
    }

    public static String fetchModName(String modId) {
        // Special case for modloader mods loaded via Apron. Hi CatCore o/
        if (modId.startsWith("mod_")) {
            return modId.substring(4);
        }
        
        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(modId);
        if (modContainer.isPresent()) {
            return modContainer.get().getMetadata().getName();
        } else {
            return "Unknown";
        }
    }

    public static boolean show(ProbeMode mode, IProbeConfig.ConfigMode cfg) {
        return cfg == NORMAL || (cfg == EXTENDED && mode == ProbeMode.EXTENDED) || mode == ProbeMode.DEBUG;
    }
}
