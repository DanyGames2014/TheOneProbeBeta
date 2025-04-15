package net.danygames2014.thatsthis.provider;

import net.danygames2014.thatsthis.ThatsThis;
import net.danygames2014.whatsthis.api.IProbeHitData;
import net.danygames2014.whatsthis.api.IProbeInfo;
import net.danygames2014.whatsthis.api.IProbeInfoProvider;
import net.danygames2014.whatsthis.api.ProbeMode;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;

public class TestProbeInfoProvider implements IProbeInfoProvider {
    @Override
    public String getID() {
        return ThatsThis.NAMESPACE.id("test_block").toString();
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, BlockState state, IProbeHitData data) {
        probeInfo.horizontal()
            .item(new ItemStack(Item.ARROW, 69))
            .text("Testing ProbeInfo");
        
        probeInfo.vertical()
            .progress(69,100, probeInfo.defaultProgressStyle()
                    .showText(true)
                    .prefix("Progress! ")
                    .suffix("%")
                    .borderColor(0xFFFFC0CB)
                    .filledColor(0xFFADD8E6)
            );
    }
}
