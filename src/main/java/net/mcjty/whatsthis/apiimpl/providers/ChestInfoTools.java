package net.mcjty.whatsthis.apiimpl.providers;

import net.mcjty.whatsthis.Tools;
import net.mcjty.whatsthis.api.ElementAlignment;
import net.mcjty.whatsthis.api.IProbeConfig;
import net.mcjty.whatsthis.api.IProbeInfo;
import net.mcjty.whatsthis.api.ProbeMode;
import net.mcjty.whatsthis.apiimpl.styles.ItemStyle;
import net.mcjty.whatsthis.apiimpl.styles.LayoutStyle;
import net.mcjty.whatsthis.config.ConfigSetup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.mcjty.whatsthis.api.TextStyleClass.INFO;

public class ChestInfoTools {

    static void showChestInfo(ProbeMode mode, IProbeInfo probeInfo, World world, BlockPos pos, IProbeConfig config) {
//        List<ItemStack> stacks = null;
//        IProbeConfig.ConfigMode chestMode = config.getShowChestContents();
//        if (chestMode == IProbeConfig.ConfigMode.EXTENDED && (ConfigSetup.showSmallChestContentsWithoutSneaking > 0 || !ConfigSetup.getInventoriesToShow().isEmpty())) {
//            if (ConfigSetup.getInventoriesToShow().contains(world.getBlockState(pos).getBlock().getRegistryName())) {
//                chestMode = IProbeConfig.ConfigMode.NORMAL;
//            } else if (ConfigSetup.showSmallChestContentsWithoutSneaking > 0) {
//                stacks = new ArrayList<>();
//                int slots = getChestContents(world, pos, stacks);
//                if (slots <= ConfigSetup.showSmallChestContentsWithoutSneaking) {
//                    chestMode = IProbeConfig.ConfigMode.NORMAL;
//                }
//            }
//        } else if (chestMode == IProbeConfig.ConfigMode.NORMAL && !ConfigSetup.getInventoriesToNotShow().isEmpty()) {
//            if (ConfigSetup.getInventoriesToNotShow().contains(world.getBlockState(pos).getBlock().getRegistryName())) {
//                chestMode = IProbeConfig.ConfigMode.EXTENDED;
//            }
//        }
//
//        if (Tools.show(mode, chestMode)) {
//            if (stacks == null) {
//                stacks = new ArrayList<>();
//                getChestContents(world, pos, stacks);
//            }
//
//            if (!stacks.isEmpty()) {
//                boolean showDetailed = Tools.show(mode, config.getShowChestContentsDetailed()) && stacks.size() <= ConfigSetup.showItemDetailThresshold;
//                showChestContents(probeInfo, world, pos, stacks, showDetailed);
//            }
//        }
    }

    private static void addItemStack(List<ItemStack> stacks, Set<Item> foundItems, @Nonnull ItemStack stack) {
//        if (stack.isEmpty()) {
//            return;
//        }
//        if (foundItems != null && foundItems.contains(stack.getItem())) {
//            for (ItemStack s : stacks) {
//                if (ItemHandlerHelper.canItemStacksStack(s, stack)) {
//                    s.grow(stack.getCount());
//                    return;
//                }
//            }
//        }
//        // If we come here we need to append a new stack
//        stacks.add(stack.copy());
//        if (foundItems != null) {
//            foundItems.add(stack.getItem());
//        }
    }

    private static void showChestContents(IProbeInfo probeInfo, World world, BlockPos pos, List<ItemStack> stacks, boolean detailed) {
//        IProbeInfo vertical = null;
//        IProbeInfo horizontal = null;
//
//        int rows = 0;
//        int idx = 0;
//
//        vertical = probeInfo.vertical(probeInfo.defaultLayoutStyle().borderColor(ConfigSetup.chestContentsBorderColor).spacing(0));
//
//        if (detailed) {
//            for (ItemStack stackInSlot : stacks) {
//                horizontal = vertical.horizontal(new LayoutStyle().spacing(10).alignment(ElementAlignment.ALIGN_CENTER));
//                horizontal.item(stackInSlot, new ItemStyle().width(16).height(16))
//                    .text(INFO + stackInSlot.getDisplayName());
//            }
//        } else {
//            for (ItemStack stackInSlot : stacks) {
//                if (idx % 10 == 0) {
//                    horizontal = vertical.horizontal(new LayoutStyle().spacing(0));
//                    rows++;
//                    if (rows > 4) {
//                        break;
//                    }
//                }
//                horizontal.item(stackInSlot);
//                idx++;
//            }
//        }
    }

    private static int getChestContents(World world, BlockPos pos, List<ItemStack> stacks) {
//        TileEntity te = world.getTileEntity(pos);
//
//        Set<Item> foundItems = ConfigSetup.compactEqualStacks ? new HashSet<>() : null;
//        int maxSlots = 0;
//        try {
//            if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
//                IItemHandler capability = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
//                maxSlots = capability.getSlots();
//                for (int i = 0; i < maxSlots; i++) {
//                    addItemStack(stacks, foundItems, capability.getStackInSlot(i));
//                }
//            } else if (te instanceof IInventory) {
//                IInventory inventory = (IInventory) te;
//                maxSlots = inventory.getSizeInventory();
//                for (int i = 0; i < maxSlots; i++) {
//                    addItemStack(stacks, foundItems, inventory.getStackInSlot(i));
//                }
//            }
//        } catch(RuntimeException e) {
//            throw new RuntimeException("Getting the contents of a " + world.getBlockState(pos).getBlock().getRegistryName() + " (" + te.getClass().getName() + ")", e);
//        }
//        return maxSlots;
        return 0;
    }
}
