package net.mcjty.whatsthis.apiimpl.providers.block;

import net.mcjty.whatsthis.Tools;
import net.mcjty.whatsthis.api.*;
import net.mcjty.whatsthis.apiimpl.styles.ItemStyle;
import net.mcjty.whatsthis.apiimpl.styles.LayoutStyle;
import net.mcjty.whatsthis.config.Config;
import net.mcjty.whatsthis.config.ConfigSetup;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.BlockRegistry;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.mcjty.whatsthis.api.TextStyleClass.INFO;

public class ChestInfoTools {

    static void showChestInfo(ProbeMode mode, IProbeInfo probeInfo, World world, BlockPos pos, BlockState state, Block block, IProbeHitData data, IProbeConfig config) {
        List<ItemStack> stacks = null;
        IProbeConfig.ConfigMode chestMode = config.getShowChestContents();
        
        if (chestMode == IProbeConfig.ConfigMode.EXTENDED && (Config.MAIN_CONFIG.showSmallChestContentsWithoutSneaking > 0 || !ConfigSetup.getInventoriesToShow().isEmpty())) {
            if (ConfigSetup.getInventoriesToShow().contains(BlockRegistry.INSTANCE.getId(block))) {
                chestMode = IProbeConfig.ConfigMode.NORMAL;
            } else if (Config.MAIN_CONFIG.showSmallChestContentsWithoutSneaking > 0) {
                stacks = new ArrayList<>();
                int slots = getChestContents(world, pos, stacks);
                if (slots <= Config.MAIN_CONFIG.showSmallChestContentsWithoutSneaking) {
                    chestMode = IProbeConfig.ConfigMode.NORMAL;
                }
            }
        } else if (chestMode == IProbeConfig.ConfigMode.NORMAL && !ConfigSetup.getInventoriesToNotShow().isEmpty()) {
            if (ConfigSetup.getInventoriesToNotShow().contains(BlockRegistry.INSTANCE.getId(block))) {
                chestMode = IProbeConfig.ConfigMode.EXTENDED;
            }
        }

        if (Tools.show(mode, chestMode)) {
            if (stacks == null) {
                stacks = new ArrayList<>();
                getChestContents(world, pos, stacks);
            }

            if (!stacks.isEmpty()) {
                boolean showDetailed = Tools.show(mode, config.getShowChestContentsDetailed()) && stacks.size() <= Config.MAIN_CONFIG.showItemDetailThreshold;
                showChestContents(probeInfo, world, pos, stacks, showDetailed);
            }
        }
    }

    private static void addItemStack(List<ItemStack> stacks, Set<Item> foundItems, ItemStack stack) {
        if (stack == null || stack.getItem() == null || stack.count <= 0) {
            return;
        }

        if (foundItems != null && foundItems.contains(stack.getItem())) {
            for (ItemStack s : stacks) {
                if (s.isItemEqual(stack)) {
                    s.count += stack.count;
                    return;
                }
            }
        }

        // If we come here we need to append a new stack
        stacks.add(stack.copy());
        if (foundItems != null) {
            foundItems.add(stack.getItem());
        }
    }

    private static void showChestContents(IProbeInfo probeInfo, World world, BlockPos pos, List<ItemStack> stacks, boolean detailed) {
        IProbeInfo vertical = null;
        IProbeInfo horizontal = null;

        int rows = 0;
        int idx = 0;

        vertical = probeInfo.vertical(probeInfo.defaultLayoutStyle().borderColor(Config.parseColor(Config.CLIENT_CONFIG.chestContentsBorderColor)).spacing(0));

        if (detailed) {
            for (ItemStack stackInSlot : stacks) {
                horizontal = vertical.horizontal(new LayoutStyle().spacing(10).alignment(ElementAlignment.ALIGN_CENTER));
                horizontal.item(stackInSlot, new ItemStyle().width(16).height(16))
                        .text(INFO + stackInSlot.getItem().getTranslatedName());
            }
        } else {
            for (ItemStack stackInSlot : stacks) {
                if (idx % 10 == 0) {
                    horizontal = vertical.horizontal(new LayoutStyle().spacing(0));
                    rows++;
                    if (rows > 4) {
                        break;
                    }
                }
                horizontal.item(stackInSlot);
                idx++;
            }
        }
    }

    private static int getChestContents(World world, BlockPos pos, List<ItemStack> stacks) {
        BlockEntity blockEntity = world.getBlockEntity(pos.x, pos.y, pos.z);

        Set<Item> foundItems = Config.CLIENT_CONFIG.compactEqualStacks ? new HashSet<>() : null;
        int maxSlots = 0;
        try {
            // TODO: ItemHandler capability
            if (blockEntity instanceof Inventory inventory) {
                maxSlots = inventory.size();
                for (int i = 0; i < maxSlots; i++) {
                    addItemStack(stacks, foundItems, inventory.getStack(i));
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Getting the contents of a " + BlockRegistry.INSTANCE.getId(world.getBlockState(pos).getBlock()) + " (" + blockEntity.getClass().getName() + ")", e);
        }
        return maxSlots;
    }
}
