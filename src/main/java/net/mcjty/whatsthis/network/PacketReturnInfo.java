package net.mcjty.whatsthis.network;

import net.mcjty.whatsthis.apiimpl.ProbeInfo;
import net.mcjty.whatsthis.rendering.OverlayRenderer;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.math.BlockPos;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketReturnInfo extends Packet implements ManagedPacket<PacketReturnInfo> {
    public static final PacketType<PacketReturnInfo> TYPE = PacketType.builder(true, false, PacketReturnInfo::new).build();

    private int dim;
    private BlockPos pos;
    private ProbeInfo probeInfo;

    private int size;

    public PacketReturnInfo() {
    }

    public PacketReturnInfo(int dim, BlockPos pos, ProbeInfo probeInfo) {
        this.dim = dim;
        this.pos = pos;
        this.probeInfo = probeInfo;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            dim = stream.readInt();
            pos = new BlockPos(stream.readInt(), stream.readInt(), stream.readInt());
            if (stream.readBoolean()) {
                probeInfo = new ProbeInfo(stream);
            } else {
                probeInfo = null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            int streamInitialSize = stream.size();

            stream.writeInt(dim);
            stream.writeInt(pos.getX());
            stream.writeInt(pos.getY());
            stream.writeInt(pos.getZ());
            
            if (probeInfo != null) {
                stream.writeBoolean(true);
                probeInfo.toBytes(stream);
            } else {
                stream.writeBoolean(false);
            }

            this.size = stream.size() - streamInitialSize;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void apply(NetworkHandler networkHandler) {
        OverlayRenderer.registerProbeInfo(dim, pos, probeInfo);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public @NotNull PacketType<PacketReturnInfo> getType() {
        return TYPE;
    }
}
