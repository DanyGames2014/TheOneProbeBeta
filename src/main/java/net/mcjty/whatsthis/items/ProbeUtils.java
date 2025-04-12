package net.mcjty.whatsthis.items;

import net.mcjty.whatsthis.WhatsThis;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class ProbeUtils {
    public static String PROBETAG = "theoneprobe";
    public static String PROBETAG_HAND = "theoneprobe_hand";

    /**
     * Determines whether the given ItemStack can be used as a probe in hand
     *
     * @param stack The ItemStack to check
     * @return Whther the ItemStack can be used as a probe in hand
     */
    public static boolean isHandProbe(ItemStack stack) {
        // Check stack validity
        if (stack == null || stack.count <= 0 || stack.getItem() == null) {
            return false;
        }

        // Check for default probes
        if (stack.isOf(WhatsThis.probe) || stack.isOf(WhatsThis.creativeProbe)) {
            return true;
        }

        // If there is no NBT, there wont be a tag
        if (stack.getStationNbt() == null) {
            return false;
        }
        
        // Return whether the ItemStack has the required tag
        return stack.getStationNbt().contains(PROBETAG_HAND);
    }

    /**
     * Determines whether the given ItemStack is a valid helmet with a probe
     *
     * @param stack The ItemStack to check
     * @return Whther the ItemStack is a valid helmet with a probe
     */
    private static boolean isHelmetProbe(ItemStack stack) {
        // Check stack validity
        if (stack == null || stack.count <= 0 || stack.getItem() == null) {
            return false;
        }

        // Check if the ItemStack is of an helmet
        if (stack.getItem() instanceof ArmorItem armorItem) {
            if (armorItem.equipmentSlot != 0) {
                return false;
            }
        }

        // If there is no NBT, there wont be a tag
        if (stack.getStationNbt() == null) {
            return false;
        }
        
        // Return whether the ItemStack has the required tag
        return stack.getStationNbt().contains(PROBETAG);
    }

    public static boolean isDebugProbe(ItemStack stack) {
        // Check stack validity
        if (stack == null || stack.count <= 0 || stack.getItem() == null) {
            return false;
        }

        return stack.isOf(WhatsThis.creativeProbe);

    }

    public static boolean hasAProbeSomewhere(PlayerEntity player) {
        return hasProbeInHand(player) || hasProbeInHelmet(player) || hasProbeInBauble(player);
    }

    private static boolean hasProbeInHand(PlayerEntity player) {
        return isHandProbe(player.getHand());
    }

    private static boolean hasProbeInHelmet(PlayerEntity player) {
        return isHelmetProbe(player.inventory.armor[3]);
    }

    private static boolean hasProbeInBauble(PlayerEntity player) {
        return false;
//        if (ModSetup.baubles) {
//            return BaubleTools.hasProbeGoggle(player);
//        } else {
//            return false;
//        }
    }
}
