package net.mcjty.whatsthis.network;

import net.mcjty.whatsthis.apiimpl.ProbeInfo;
import net.mcjty.whatsthis.rendering.OverlayRenderer;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketReturnEntityInfo extends Packet implements ManagedPacket<PacketReturnEntityInfo> {
    public static final PacketType<PacketReturnEntityInfo> TYPE = PacketType.builder(true, false, PacketReturnEntityInfo::new).build();

    private int entityId;
    private ProbeInfo probeInfo;

    private int size;

    public PacketReturnEntityInfo() {
    }

    public PacketReturnEntityInfo(int entityId, ProbeInfo probeInfo) {
        this.entityId = entityId;
        this.probeInfo = probeInfo;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            entityId = stream.readInt();
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
            int initialStreamSize = stream.size();

            stream.writeInt(entityId);
            if (probeInfo != null) {
                stream.writeBoolean(true);
                probeInfo.toBytes(stream);
            } else {
                stream.writeBoolean(false);
            }

            this.size = stream.size() - initialStreamSize;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        OverlayRenderer.registerProbeInfo(entityId, probeInfo);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public @NotNull PacketType<PacketReturnEntityInfo> getType() {
        return TYPE;
    }
}
