package net.mcjty.whatsthis.apiimpl.providers;

import net.mcjty.whatsthis.WhatsThis;
import net.mcjty.whatsthis.config.ConfigSetup;
import net.mcjty.whatsthis.api.IProbeHitData;
import net.mcjty.whatsthis.api.IProbeInfo;
import net.mcjty.whatsthis.api.IProbeInfoProvider;
import net.mcjty.whatsthis.api.ProbeMode;
import net.mcjty.whatsthis.apiimpl.styles.LayoutStyle;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;

import static net.mcjty.whatsthis.api.TextStyleClass.INFO;
import static net.mcjty.whatsthis.api.TextStyleClass.LABEL;

public class DebugProbeInfoProvider implements IProbeInfoProvider {

    @Override
    public String getID() {
        return WhatsThis.NAMESPACE.getName() + ":debug";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState blockState, IProbeHitData data) {
        if (mode == ProbeMode.DEBUG && ConfigSetup.showDebugInfo) {
            Block block = blockState.getBlock();
            BlockPos pos = data.getPos();
            showDebugInfo(probeInfo, world, blockState, pos, block, data.getSideHit());
        }
    }

    private void showDebugInfo(IProbeInfo probeInfo, World world, BlockState blockState, BlockPos pos, Block block, Direction side) {
        String simpleName = block.getClass().getSimpleName();
        IProbeInfo vertical = probeInfo.vertical(new LayoutStyle().borderColor(0xffff4444).spacing(2))
//                .text(LABEL + "Reg Name: " + INFO + block.getRegistryName().toString())
//                .text(LABEL + "Unlocname: " + INFO + block.getUnlocalizedName())
                .text(LABEL + "Meta: " + INFO + world.getBlockMeta(pos.x, pos.y, pos.z))
                .text(LABEL + "Class: " + INFO + simpleName)
                .text(LABEL + "Hardness: " + INFO + block.getHardness(blockState, world, pos))
//                .text(LABEL + "Light: " + INFO + block.getLightValue(blockState, world, pos))
                ;
        BlockEntity te = world.getBlockEntity(pos.x, pos.y, pos.z);
        if (te != null) {
            vertical.text(LABEL + "TE: " + INFO + te.getClass().getSimpleName());
        }
    }
}
