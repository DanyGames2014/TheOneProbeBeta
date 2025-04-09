package net.mcjty.whatsthis.network;

import net.mcjty.whatsthis.WhatsThis;
import net.mcjty.whatsthis.api.*;
import net.mcjty.whatsthis.apiimpl.ProbeHitEntityData;
import net.mcjty.whatsthis.apiimpl.ProbeInfo;
import net.mcjty.whatsthis.config.Config;
import net.mcjty.whatsthis.config.ConfigSetup;
import net.mcjty.whatsthis.items.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import static net.mcjty.whatsthis.api.TextStyleClass.ERROR;
import static net.mcjty.whatsthis.api.TextStyleClass.LABEL;
import static net.mcjty.whatsthis.config.ConfigSetup.PROBE_NEEDEDFOREXTENDED;
import static net.mcjty.whatsthis.config.ConfigSetup.PROBE_NEEDEDHARD;

public class PacketGetEntityInfo extends Packet implements ManagedPacket<PacketGetEntityInfo> {
    public static final PacketType<PacketGetEntityInfo> TYPE = PacketType.builder(false, true, PacketGetEntityInfo::new).build();
    
    private int dim;
    private int entityId;
    private ProbeMode mode;
    private Vec3d hitVec;

    public PacketGetEntityInfo() {
    }

    public PacketGetEntityInfo(int dim, ProbeMode mode, HitResult mouseOver, Entity entity) {
        this.dim = dim;
        this.entityId = entity.id;
        this.mode = mode;
        this.hitVec = Vec3d.create(mouseOver.blockX, mouseOver.blockY, mouseOver.blockZ);
    }
    
    @Override
    public void read(DataInputStream stream) {
        try {
            dim = stream.readInt();
            entityId = stream.readInt();
            mode = ProbeMode.values()[stream.readByte()];
            if (stream.readBoolean()) {
                hitVec = Vec3d.create(stream.readDouble(), stream.readDouble(), stream.readDouble());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(DataOutputStream buf) {
        try {
            buf.writeInt(dim);
            buf.writeInt(entityId);
            buf.writeByte(mode.ordinal());
            if (hitVec == null) {
                buf.writeBoolean(false);
            } else {
                buf.writeBoolean(true);
                buf.writeDouble(hitVec.x);
                buf.writeDouble(hitVec.y);
                buf.writeDouble(hitVec.z);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        // TODO: handle
    }

    @Override
    public int size() {
        return 0;
    }

//    public static class Handler implements IMessageHandler<PacketGetEntityInfo, IMessage> {
//        @Override
//        public IMessage onMessage(PacketGetEntityInfo message, MessageContext ctx) {
//            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
//            return null;
//        }
//
//        private void handle(PacketGetEntityInfo message, MessageContext ctx) {
//            WorldServer world = DimensionManager.getWorld(message.dim);
//            if (world != null) {
//                Entity entity = world.getEntityFromUuid(message.entityId);
//                if (entity != null) {
//                    ProbeInfo probeInfo = getProbeInfo(ctx.getServerHandler().player, message.mode, world, entity, message.hitVec);
//                    PacketHandler.INSTANCE.sendTo(new PacketReturnEntityInfo(message.entityId, probeInfo), ctx.getServerHandler().player);
//                }
//            }
//        }
//    }

    private static ProbeInfo getProbeInfo(PlayerEntity player, ProbeMode mode, World world, Entity entity, Vec3d hitVec) {
        if (Config.MAIN_CONFIG.needsProbe == PROBE_NEEDEDFOREXTENDED) {
            // We need a probe only for extended information
            if (!ModItems.hasAProbeSomewhere(player)) {
                // No probe anywhere, switch EXTENDED to NORMAL
                if (mode == ProbeMode.EXTENDED) {
                    mode = ProbeMode.NORMAL;
                }
            }
        } else if (Config.MAIN_CONFIG.needsProbe == PROBE_NEEDEDHARD && !ModItems.hasAProbeSomewhere(player)) {
            // The server says we need a probe but we don't have one in our hands or on our head
            return null;
        }

        ProbeInfo probeInfo = WhatsThis.theOneProbeImp.create();
        IProbeHitEntityData data = new ProbeHitEntityData(hitVec);

        IProbeConfig probeConfig = WhatsThis.theOneProbeImp.createProbeConfig();
        List<IProbeConfigProvider> configProviders = WhatsThis.theOneProbeImp.getConfigProviders();
        for (IProbeConfigProvider configProvider : configProviders) {
            configProvider.getProbeConfig(probeConfig, player, world, entity, data);
        }
        ConfigSetup.setRealConfig(probeConfig);

        List<IProbeInfoEntityProvider> entityProviders = WhatsThis.theOneProbeImp.getEntityProviders();
        for (IProbeInfoEntityProvider provider : entityProviders) {
            try {
                provider.addProbeEntityInfo(mode, probeInfo, player, world, entity, data);
            } catch (Throwable e) {
                ThrowableIdentity.registerThrowable(e);
                probeInfo.text(LABEL + "Error: " + ERROR + provider.getID());
            }
        }
        return probeInfo;
    }

    @Override
    public @NotNull PacketType<PacketGetEntityInfo> getType() {
        return TYPE;
    }
}
