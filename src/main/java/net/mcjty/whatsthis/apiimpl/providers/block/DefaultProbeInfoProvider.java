package net.mcjty.whatsthis.apiimpl.providers.block;

import net.mcjty.whatsthis.Util;
import net.mcjty.whatsthis.WhatsThis;
import net.mcjty.whatsthis.api.*;
import net.mcjty.whatsthis.apiimpl.ProbeConfig;
import net.mcjty.whatsthis.apiimpl.elements.ElementProgress;
import net.mcjty.whatsthis.config.Config;
import net.mcjty.whatsthis.config.ConfigSetup;
import net.minecraft.block.*;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;

import static net.mcjty.whatsthis.api.TextStyleClass.*;

public class DefaultProbeInfoProvider implements IProbeInfoProvider {

    @Override
    public String getID() {
        return WhatsThis.NAMESPACE.id("block_default").toString();
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState state, IProbeHitData data) {
        Block block = state.getBlock();
        BlockPos pos = data.getPos();

        IProbeConfig config = ConfigSetup.getProbeConfig();

        // Standard Information
        boolean handled = false;
        for (IBlockDisplayOverride override : WhatsThis.theOneProbeImp.getBlockOverrides()) {
            if (override.overrideStandardInfo(mode, probeInfo, player, world, state, data)) {
                handled = true;
                break;
            }
        }

        if (!handled) {
            showStandardBlockInfo(mode, probeInfo, world, pos, state, block, player, data, config);
        }

        // If its a liquid, we don't need to do further processing
        if (block instanceof LiquidBlock) {
            return;
        }

        // Harvest Level
        boolean showHarvestLevel = Util.show(mode, config.getShowHarvestLevel());
        boolean showHarvested = Util.show(mode, config.getShowCanBeHarvested());
        if (showHarvested && showHarvestLevel) {
            HarvestabilityInfo.showHarvestInfo(probeInfo, world, pos, state, block, player);
        } else if (showHarvestLevel) {
            HarvestabilityInfo.showHarvestLevel(probeInfo, world, pos, state, block);
        } else if (showHarvested) {
            HarvestabilityInfo.showCanBeHarvested(probeInfo, world, pos, state, block, player);
        }

        // Crop Growth
        if (Util.show(mode, config.getShowCropPercentage())) {
            showGrowthLevel(probeInfo, world, pos, state, block, data);
        }

        // Mob Spawner Info
        if (Util.show(mode, config.getShowMobSpawnerSetting())) {
            showMobSpawnerInfo(probeInfo, world, pos, state, block, data);
        }

        // Redstone Power
        if (Util.show(mode, config.getShowRedstone())) {
            showRedstonePower(probeInfo, world, pos, state, block, data, Util.show(mode, config.getShowLeverSetting()));
        }

        // Redstone Component
        if (Util.show(mode, config.getShowLeverSetting())) {
            showLeverSetting(probeInfo, world, pos, state, block, data);
        }

        // Inventory Contents
        InventoryInfo.showInventoryInfo(mode, probeInfo, world, pos, state, block, data, config);

        // Energy Info
        if (config.getRFMode() > 0) {
            showRF(probeInfo, world, pos);
        }

        // Fluid Info
        if (Util.show(mode, config.getShowTankSetting())) {
            if (config.getTankMode() > 0) {
                showTankInfo(probeInfo, world, pos);
            }
        }
    }

    public static void showStandardBlockInfo(ProbeMode mode, IProbeInfo probeInfo, World world, BlockPos pos, BlockState blockState, Block block, PlayerEntity player, IProbeHitData data, IProbeConfig config) {
        String modid = Util.getModName(block);
        ItemStack pickBlock = data.getPickBlock();
        
        // TODO: Special handling for liquids when the time comes

        if (pickBlock != null) {
            if (Util.show(mode, config.getShowModName())) {
                probeInfo.horizontal()
                        .item(pickBlock)
                        .vertical()
                        .itemLabel(pickBlock)
                        .text(MODNAME + modid);
            } else {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER))
                        .item(pickBlock)
                        .itemLabel(pickBlock);
            }
        } else {
            if (Util.show(mode, config.getShowModName())) {
                probeInfo.vertical()
                        .text(NAME + block.getTranslatedName())
                        .text(MODNAME + modid);
            } else {
                probeInfo.vertical()
                        .text(NAME + block.getTranslatedName());
            }
        }
    }

    private void showMobSpawnerInfo(IProbeInfo probeInfo, World world, BlockPos pos, BlockState state, Block block, IProbeHitData data) {
        if (block instanceof SpawnerBlock) {
            if (world.getBlockEntity(data.getPos().x, data.getPos().y, data.getPos().z) instanceof MobSpawnerBlockEntity spawner) {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle()
                                .alignment(ElementAlignment.ALIGN_CENTER))
                        .text(LABEL + "Entity: " + INFO + spawner.spawnedEntityId);
            }
        }
    }

    private void showRedstonePower(IProbeInfo probeInfo, World world, BlockPos pos, BlockState state, Block block, IProbeHitData data, boolean showLever) {
        if (showLever && block instanceof LeverBlock) {
            // We are showing the lever setting so we don't show redstone in that case
            return;
        }

        int redstonePower;

        if (block instanceof RedstoneWireBlock) {
            redstonePower = world.getBlockMeta(pos.x, pos.y, pos.z);
        } else {
            redstonePower = world.isEmittingRedstonePower(pos.x, pos.y, pos.z) ? 15 : 0;
        }

        if (redstonePower > 0) {
            probeInfo.horizontal()
                    .item(new ItemStack(Item.REDSTONE), probeInfo.defaultItemStyle().width(14).height(14))
                    .text(LABEL + "Power: " + INFO + redstonePower);
        }
    }

    private void showLeverSetting(IProbeInfo probeInfo, World world, BlockPos pos, BlockState state, Block block, IProbeHitData data) {
        if (block instanceof LeverBlock lever) {
            boolean powered = lever.isEmittingRedstonePowerInDirection(world, pos.x, pos.y, pos.z, 0);
            probeInfo.horizontal().item(new ItemStack(Item.REDSTONE), probeInfo.defaultItemStyle().width(14).height(14))
                    .text(LABEL + "State: " + INFO + (powered ? "On" : "Off"));

        } else if (block instanceof RepeaterBlock) {
            int delay = ((world.getBlockMeta(pos.x, pos.y, pos.z) & 12) >> 2) + 1;
            probeInfo.text(LABEL + "Delay: " + INFO + delay + " ticks");
        }
    }

    private void showTankInfo(IProbeInfo probeInfo, World world, BlockPos pos) {
//        ProbeConfig config = ConfigSetup.getDefaultConfig();
//        TileEntity te = world.getTileEntity(pos);
//        if (te != null && te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
//            net.minecraftforge.fluids.capability.IFluidHandler handler = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
//            if (handler != null) {
//                IFluidTankProperties[] properties = handler.getTankProperties();
//                if (properties != null) {
//                    for (IFluidTankProperties property : properties) {
//                        if (property != null) {
//                            FluidStack fluidStack = property.getContents();
//                            int maxContents = property.getCapacity();
//                            addFluidInfo(probeInfo, config, fluidStack, maxContents);
//                        }
//                    }
//                }
//            }
//        }
    }

//    private void addFluidInfo(IProbeInfo probeInfo, ProbeConfig config, FluidStack fluidStack, int maxContents) {
//        int contents = fluidStack == null ? 0 : fluidStack.amount;
//        if (fluidStack != null) {
//            probeInfo.text(NAME + "Liquid: " + fluidStack.getLocalizedName());
//        }
//        if (config.getTankMode() == 1) {
//            probeInfo.progress(contents, maxContents,
//                    probeInfo.defaultProgressStyle()
//                            .suffix("mB")
//                            .filledColor(ConfigSetup.tankbarFilledColor)
//                            .alternateFilledColor(ConfigSetup.tankbarAlternateFilledColor)
//                            .borderColor(ConfigSetup.tankbarBorderColor)
//                            .numberFormat(ConfigSetup.tankFormat));
//        } else {
//            probeInfo.text(PROGRESS + ElementProgress.format(contents, ConfigSetup.tankFormat, "mB"));
//        }
//    }

    private void showRF(IProbeInfo probeInfo, World world, BlockPos pos) {
//        ProbeConfig config = ConfigSetup.getDefaultConfig();
//        TileEntity te = world.getTileEntity(pos);
//        if (ModSetup.tesla && TeslaTools.isEnergyHandler(te)) {
//            long energy = TeslaTools.getEnergy(te);
//            long maxEnergy = TeslaTools.getMaxEnergy(te);
//            addRFInfo(probeInfo, config, energy, maxEnergy);
//        } else if (te instanceof IBigPower) {
//            long energy = ((IBigPower) te).getStoredPower();
//            long maxEnergy = ((IBigPower) te).getCapacity();
//            addRFInfo(probeInfo, config, energy, maxEnergy);
//        } else if (ModSetup.redstoneflux && RedstoneFluxTools.isEnergyHandler(te)) {
//            int energy = RedstoneFluxTools.getEnergy(te);
//            int maxEnergy = RedstoneFluxTools.getMaxEnergy(te);
//            addRFInfo(probeInfo, config, energy, maxEnergy);
//        } else if (te != null && te.hasCapability(CapabilityEnergy.ENERGY, null)) {
//            net.minecraftforge.energy.IEnergyStorage handler = te.getCapability(CapabilityEnergy.ENERGY, null);
//            if (handler != null) {
//                addRFInfo(probeInfo, config, handler.getEnergyStored(), handler.getMaxEnergyStored());
//            }
//        }
    }

    private void addRFInfo(IProbeInfo probeInfo, ProbeConfig config, long energy, long maxEnergy) {
        if (config.getRFMode() == 1) {
            probeInfo.progress(energy, maxEnergy,
                    probeInfo.defaultProgressStyle()
                            .suffix("RF")
                            .filledColor(Config.parseColor(Config.MAIN_CONFIG.rfbarFilledColor))
                            .alternateFilledColor(Config.parseColor(Config.MAIN_CONFIG.rfbarAlternateFilledColor))
                            .borderColor(Config.parseColor(Config.MAIN_CONFIG.rfbarBorderColor))
                            .numberFormat(Config.MAIN_CONFIG.rfFormat));
        } else {
            probeInfo.text(PROGRESS + "RF: " + ElementProgress.format(energy, Config.MAIN_CONFIG.rfFormat, "RF"));
        }
    }

    private void showGrowthLevel(IProbeInfo probeInfo, World world, BlockPos pos, BlockState blockState, Block block, IProbeHitData data) {
        if (blockState.isOf(Block.WHEAT)) {
            int age = world.getBlockMeta(pos.x, pos.y, pos.z);
            int maxAge = 7;

            if (age == maxAge) {
                probeInfo.text(OK + "Fully grown");
            } else {
                probeInfo.text(LABEL + "Growth: " + WARNING + (age * 100) / maxAge + "%");
            }
        }
    }
}
