package net.danygames2014.whatsthis.apiimpl.providers.block;

import net.danygames2014.whatsthis.api.ElementAlignment;
import net.danygames2014.whatsthis.api.IIconStyle;
import net.danygames2014.whatsthis.api.ILayoutStyle;
import net.danygames2014.whatsthis.api.IProbeInfo;
import net.danygames2014.whatsthis.config.Config;
import net.danygames2014.whatsthis.item.ProbeUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.tool.TagToolLevel;
import net.modificationstation.stationapi.api.item.tool.ToolLevel;
import net.modificationstation.stationapi.api.tag.TagKey;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static net.danygames2014.whatsthis.api.TextStyleClass.*;

public class HarvestabilityInfo {

    private static final String ICONS = "assets/whatsthis/stationapi/textures/gui/icons.png";

    // Harvest Levels
    private record HarvestLevelEntry(String name, TagKey<Block> tagKey, ToolLevel toolLevel) {
    }

    private static ArrayList<HarvestLevelEntry> HARVEST_LEVELS = null;
    private static final HashMap<BlockState, String> harvestLevelCache = new HashMap<>();

    @SuppressWarnings("UnstableApiUsage")
    private static void getToolLevels() {
        if (HARVEST_LEVELS != null) {
            return;
        }

        HARVEST_LEVELS = new ArrayList<>();

        // Find lowest node ?
        ToolLevel currentLevel = (ToolLevel) ToolLevel.ALL_LEVELS.toArray()[0];
        boolean run = true;

        while (run) {
            Set<ToolLevel> predecessors = ToolLevel.GRAPH.predecessors(currentLevel);

            if (!predecessors.isEmpty()) {
                currentLevel = (ToolLevel) predecessors.toArray()[0];
            } else {
                run = false;
            }
        }

        // Now go up ?
        run = true;

        while (run) {
            if (currentLevel instanceof TagToolLevel tagToolLevel) {
                String name = tagToolLevel.tag.id().path;
                String[] split = tagToolLevel.tag.id().path.split("_");
                if (split.length > 0) {
                    name = split[split.length - 2];
                    name = StringUtils.capitalize(name);
                }
                HARVEST_LEVELS.add(new HarvestLevelEntry(name, tagToolLevel.tag, tagToolLevel));
            } else {
                HARVEST_LEVELS.add(new HarvestLevelEntry(currentLevel.toString(), null, currentLevel));
            }

            var successors = ToolLevel.GRAPH.successors(currentLevel);
            if (!successors.isEmpty()) {
                currentLevel = (ToolLevel) successors.toArray()[0];
            } else {
                run = false;
            }
        }
    }

    private static String getHarvestLevel(BlockState state) {
        getToolLevels();
        return harvestLevelCache.computeIfAbsent(state, HarvestabilityInfo::computeHarvestLevel);

    }

    private static String computeHarvestLevel(BlockState state) {
        for (HarvestLevelEntry entry : HARVEST_LEVELS) {
            if (ToolLevel.isSuitable(entry.toolLevel, state)) {
                return entry.name;
            }
        }

        return "";
    }

    // Harvest Tools
    private static final HashMap<BlockState, String> harvestToolCache = new HashMap<>();
    private static final HashMap<String, ItemStack> testTools = new HashMap<>();

    static {
        testTools.put("Shovel", new ItemStack(Item.DIAMOND_SHOVEL));
        testTools.put("Axe", new ItemStack(Item.DIAMOND_AXE));
        testTools.put("Pickaxe", new ItemStack(Item.DIAMOND_PICKAXE));
        testTools.put("Hoe", new ItemStack(Item.DIAMOND_HOE));
        testTools.put("Sword", new ItemStack(Item.DIAMOND_SWORD));
        testTools.put("Shears", new ItemStack(Item.SHEARS));
    }

    private static String getHarvestTool(World world, BlockPos pos, BlockState state, Block block) {
        if (!harvestToolCache.containsKey(state)) {
            harvestToolCache.put(state, computeHarvestTools(world, pos, state, block));
        }

        return harvestToolCache.get(state);
    }

    private static String computeHarvestTools(World world, BlockPos pos, BlockState state, Block block) {
        String harvestTool = null;

        // The block doesn't have an explicitly-set harvest tool, so we're going to test our tools against the block.
        float blockHardness = state.getHardness(world, pos);
        if (blockHardness > 0f) {
            for (Map.Entry<String, ItemStack> testToolEntry : testTools.entrySet()) {
                // loop through our test tools until we find a winner.
                ItemStack testTool = testToolEntry.getValue();

                if (testTool != null) {
                    // First check if it is a tool
                    if (testTool.getItem() instanceof ToolItem toolItem) {
                        if (testTool.getMiningSpeedMultiplier(block) >= toolItem.toolMaterial.getMiningSpeedMultiplier()) {
                            return testToolEntry.getKey();
                        }
                        // Then check if it is a sword which has a base mining speed multiplier of 1.5F
                    } else if (testTool.getItem() instanceof SwordItem) {
                        if (testTool.getMiningSpeedMultiplier(block) > 1.5F) {
                            return testToolEntry.getKey();
                        }

                        // Finally check for other items
                    } else if (testTool.getMiningSpeedMultiplier(block) > 1.0F) {
                        return testToolEntry.getKey();
                    }
                }
            }
        }

//        String[] tools = new String[]{"pickaxe", "shovel", "axe", "hoe", "sword", "shears"};
//        StringBuilder s = new StringBuilder();
//
//        for (String tool : tools) {
//            TagKey<Block> toolTag = TagKey.of(BlockRegistry.KEY, Identifier.of("minecraft:mineable/" + tool));
//
//            if (state.isIn(toolTag)) {
//                s.append(StringUtils.capitalize(tool)).append(" ");
//            }
//        }
//
//        if (s.isEmpty()) {
//            return null;
//        }
//
//        harvestTool = s.toString();
        return harvestTool;
    }


    static void showHarvestLevel(IProbeInfo probeInfo, World world, BlockPos pos, BlockState state, Block block) {
        String harvestTool = getHarvestTool(world, pos, state, block);
        String harvestLevel = getHarvestLevel(state);
        if (harvestTool != null) {
            probeInfo.text(LABEL + "Tool: " + INFO + harvestTool + " (level " + harvestLevel + ")");
        }
    }

    static void showCanBeHarvested(IProbeInfo probeInfo, World world, BlockPos pos, BlockState state, Block block, PlayerEntity player) {
        if (ProbeUtil.isHandProbe(player.getHand())) {
            // If the player holds the probe there is no need to show harvestability information as the
            // probe cannot harvest anything. This is only supposed to work in off hand.
            return;
        }

        boolean harvestable = player.canHarvest(world, pos, state);
        if (harvestable) {
            probeInfo.text(OK + "Harvestable");
        } else {
            probeInfo.text(WARNING + "Not harvestable");
        }
    }

    static void showHarvestInfo(IProbeInfo probeInfo, World world, BlockPos pos, BlockState state, Block block, PlayerEntity player) {
        boolean harvestable = player.canHarvest(world, pos, state);

        String harvestTool = getHarvestTool(world, pos, state, block);
        String harvestLevel = getHarvestLevel(state);

        boolean v = Config.CLIENT_CONFIG.harvestStyleVanilla;
        int offs = v ? 16 : 0;
        int dim = v ? 13 : 16;

        ILayoutStyle alignment = probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER);
        IIconStyle iconStyle = probeInfo.defaultIconStyle().width(v ? 18 : 20).height(v ? 14 : 16).textureWidth(32).textureHeight(32);
        IProbeInfo horizontal = probeInfo.horizontal(alignment);

        String harvestToolString = getHarvestToolString(harvestTool, world, pos, state, block, player);

        if (harvestable) {
            horizontal.icon(ICONS, 0, offs, dim, dim, iconStyle)
                    .text(harvestToolString);
        } else {
            if (harvestLevel == null || harvestLevel.isEmpty()) {
                horizontal.icon(ICONS, 16, offs, dim, dim, iconStyle)
                        .text(harvestToolString);
            } else {
                horizontal.icon(ICONS, 16, offs, dim, dim, iconStyle)
                        .text(harvestToolString + WARNING + " (" + harvestLevel + " Level)");
            }
        }
    }

    private static String getHarvestToolString(String harvestTool, World world, BlockPos pos, BlockState state, Block block, PlayerEntity player) {
        if (harvestTool != null) {
            if (player.getHand() != null) {
                Item hand = player.getHand().getItem();
                float multiplier = player.getHand().getItem().getMiningSpeedMultiplier(player.getHand(), block);
                if (hand instanceof ToolItem && multiplier > 1.0F) {
                    return OK + harvestTool;
                } else if (hand instanceof SwordItem && multiplier <= 1.5F) {
                    return WARNING + harvestTool;
                } else if (multiplier <= 1.0F) {
                    return WARNING + harvestTool;
                }
            } else {
                return WARNING + harvestTool;
            }
        } else {
            return OK + "No Tool";
        }
        return WARNING + harvestTool;
    }
}
