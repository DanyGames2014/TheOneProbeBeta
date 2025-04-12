package net.mcjty.whatsthis.apiimpl.providers.block;

import net.mcjty.whatsthis.api.IProbeInfo;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;

import java.util.HashMap;

public class HarvestInfoTools {

    private static final String ICONS = "textures/gui/icons.png";
    private static String[] harvestLevels = new String[]{
            "stone",
            "iron",
            "diamond",
            "obsidian",
            "cobalt"
    };

    private static final HashMap<String, ItemStack> testTools = new HashMap<>();
    static {
        testTools.put("shovel", new ItemStack(Item.WOODEN_SHOVEL));
        testTools.put("axe", new ItemStack(Item.WOODEN_AXE));
        testTools.put("pickaxe", new ItemStack(Item.WOODEN_PICKAXE));
    }

    static void showHarvestLevel(IProbeInfo probeInfo, World world, BlockPos pos, BlockState blockState, Block block) {
//        String harvestTool = block.getHarvestTool(blockState);
//        if (harvestTool != null) {
//            int harvestLevel = block.getHarvestLevel(blockState);
//            String harvestName;
//            if (harvestLevel >= harvestLevels.length) {
//                harvestName = Integer.toString(harvestLevel);
//            } else if (harvestLevel < 0) {
//                harvestName = Integer.toString(harvestLevel);
//            } else {
//                harvestName = harvestLevels[harvestLevel];
//            }
//            probeInfo.text(LABEL + "Tool: " + INFO + harvestTool + " (level " + harvestName + ")");
//        }
    }

    static void showCanBeHarvested(IProbeInfo probeInfo, World world, BlockPos pos, BlockState state, Block block, PlayerEntity player) {
//        if (ModItems.isProbeInHand(player.getHand())) {
//            // If the player holds the probe there is no need to show harvestability information as the
//            // probe cannot harvest anything. This is only supposed to work in off hand.
//            return;
//        }
//
//        boolean harvestable = block.canHarvestBlock(world, pos, player) && world.getBlockState(pos).getBlockHardness(world, pos) >= 0;
//        if (harvestable) {
//            probeInfo.text(OK + "Harvestable");
//        } else {
//            probeInfo.text(WARNING + "Not harvestable");
//        }
    }

    static void showHarvestInfo(IProbeInfo probeInfo, World world, BlockPos pos, BlockState blockState, Block block, PlayerEntity player) {
//        boolean harvestable = block.canHarvestBlock(world, pos, player) && world.getBlockState(pos).getBlockHardness(world, pos) >= 0;
//
//        String harvestTool = block.getHarvestTool(blockState);
//        String harvestName = null;
//
//        if (harvestTool == null) {
//            // The block doesn't have an explicitly-set harvest tool, so we're going to test our wooden tools against the block.
//            float blockHardness = blockState.getBlockHardness(world, pos);
//            if (blockHardness > 0f) {
//                for (Map.Entry<String, ItemStack> testToolEntry : testTools.entrySet()) {
//                    // loop through our test tools until we find a winner.
//                    ItemStack testTool = testToolEntry.getValue();
//
//                    if (testTool != null && testTool.getItem() instanceof ItemTool) {
//                        ItemTool toolItem = (ItemTool) testTool.getItem();
//                        // @todo
//                        if (testTool.getDestroySpeed(blockState) >= toolItem.toolMaterial.getEfficiency()) {
//                            // BINGO!
//                            harvestTool = testToolEntry.getKey();
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//
//        if (harvestTool != null) {
//            int harvestLevel = block.getHarvestLevel(blockState);
//            if (harvestLevel < 0) {
//                // NOTE: When a block doesn't have an explicitly-set harvest tool, getHarvestLevel will return -1 for ANY tool. (Expected behavior)
////                TheOneProbe.logger.info("HarvestLevel out of bounds (less than 0). Found " + harvestLevel);
//            } else if (harvestLevel >= harvestLevels.length) {
////                TheOneProbe.logger.info("HarvestLevel out of bounds (Max value " + harvestLevels.length + "). Found " + harvestLevel);
//            } else {
//                harvestName = harvestLevels[harvestLevel];
//            }
//            harvestTool = StringUtils.capitalize(harvestTool);
//        }
//
//        boolean v = ConfigSetup.harvestStyleVanilla;
//        int offs = v ? 16 : 0;
//        int dim = v ? 13 : 16;
//
//        ILayoutStyle alignment = probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER);
//        IIconStyle iconStyle = probeInfo.defaultIconStyle().width(v ? 18 : 20).height(v ? 14 : 16).textureWidth(32).textureHeight(32);
//        IProbeInfo horizontal = probeInfo.horizontal(alignment);
//        if (harvestable) {
//            horizontal.icon(ICONS, 0, offs, dim, dim, iconStyle)
//                    .text(OK + ((harvestTool != null) ? harvestTool : "No tool"));
//        } else {
//            if (harvestName == null || harvestName.isEmpty()) {
//                horizontal.icon(ICONS, 16, offs, dim, dim, iconStyle)
//                        .text(WARNING + ((harvestTool != null) ? harvestTool : "No tool"));
//            } else {
//                horizontal.icon(ICONS, 16, offs, dim, dim, iconStyle)
//                        .text(WARNING + ((harvestTool != null) ? harvestTool : "No tool") + " (level " + harvestName + ")");
//            }
//        }
    }
}
