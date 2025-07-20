package net.danygames2014.whatsthis.compat;

import com.matthewperiut.accessoryapi.api.AccessoryRegister;
import com.matthewperiut.accessoryapi.api.helper.AccessoryAccess;
import net.danygames2014.whatsthis.WhatsThis;
import net.danygames2014.whatsthis.item.ProbeGogglesItem;
import net.minecraft.entity.player.PlayerEntity;

public class AccessoryApiCompat {
    public static void registerProbeAccessory() {
        AccessoryRegister.add("probe", "/assets/whatsthis/stationapi/textures/item/probe.png", 0, 0);
        WhatsThis.probeGoggles = new ProbeGogglesItem(WhatsThis.NAMESPACE.id("probe_goggles")).setTranslationKey(WhatsThis.NAMESPACE, "probe_goggles").setMaxCount(1);
    }

    public static boolean hasProbeGoggles(PlayerEntity player) {
        return AccessoryAccess.hasAccessory(player, WhatsThis.probeGoggles);
    }
}
