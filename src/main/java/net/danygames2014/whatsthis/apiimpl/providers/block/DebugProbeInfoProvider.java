package net.danygames2014.whatsthis.apiimpl.providers.block;

import net.danygames2014.whatsthis.WhatsThis;
import net.danygames2014.whatsthis.api.IProbeHitData;
import net.danygames2014.whatsthis.api.IProbeInfo;
import net.danygames2014.whatsthis.api.IProbeInfoProvider;
import net.danygames2014.whatsthis.api.ProbeMode;
import net.danygames2014.whatsthis.apiimpl.styles.LayoutStyle;
import net.danygames2014.whatsthis.config.Config;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.util.math.Direction;

import static net.danygames2014.whatsthis.api.TextStyleClass.INFO;
import static net.danygames2014.whatsthis.api.TextStyleClass.LABEL;

public class DebugProbeInfoProvider implements IProbeInfoProvider {

    @Override
    public String getID() {
        return WhatsThis.NAMESPACE.id("block_debug").toString();
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState state, IProbeHitData data) {
        if (mode == ProbeMode.DEBUG && Config.PROBE_CONFIG.showDebugInfo) {
            Block block = state.getBlock();
            BlockPos pos = data.getPos();
            showDebugInfo(probeInfo, world, state, pos, block, data.getSideHit());
        }
    }

    private void showDebugInfo(IProbeInfo probeInfo, World world, BlockState blockState, BlockPos pos, Block block, Direction side) {
        IProbeInfo vertical = probeInfo.vertical(new LayoutStyle().borderColor(0xffff4444).spacing(2))
                .text(LABEL + "Reg Name: " + INFO + BlockRegistry.INSTANCE.getId(block))
                .text(LABEL + "Unlocname: " + INFO + block.getTranslationKey())
                .text(LABEL + "Meta: " + INFO + world.getBlockMeta(pos.x, pos.y, pos.z))
                .text(LABEL + "Class: " + INFO + block.getClass().getSimpleName())
                .text(LABEL + "Hardness: " + INFO + block.getHardness(blockState, world, pos))
                .text(LABEL + "Light: " + INFO + world.getLightLevel(pos.x, pos.y, pos.z));

        BlockEntity blockEntity = world.getBlockEntity(pos.x, pos.y, pos.z);
        if (blockEntity != null) {
            vertical.text(LABEL + "Block Entity: " + INFO + blockEntity.getClass().getSimpleName());
        }
    }
}
