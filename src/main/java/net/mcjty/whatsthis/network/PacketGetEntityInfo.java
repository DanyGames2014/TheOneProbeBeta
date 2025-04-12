package net.mcjty.whatsthis.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.mcjty.whatsthis.WhatsThis;
import net.mcjty.whatsthis.api.*;
import net.mcjty.whatsthis.apiimpl.ProbeHitEntityData;
import net.mcjty.whatsthis.apiimpl.ProbeInfo;
import net.mcjty.whatsthis.config.Config;
import net.mcjty.whatsthis.config.ConfigSetup;
import net.mcjty.whatsthis.items.ProbeUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import static net.mcjty.whatsthis.Util.getEntity;
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

    private int size;

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
    public void write(DataOutputStream stream) {
        try {
            int initialStreamSize = stream.size();
            
            stream.writeInt(dim); // 4b
            stream.writeInt(entityId); // 4b
            stream.writeByte(mode.ordinal()); // 1b
            if (hitVec == null) {
                stream.writeBoolean(false); // 1b
            } else {
                stream.writeBoolean(true); // 1b
                stream.writeDouble(hitVec.x); // 8b
                stream.writeDouble(hitVec.y); // 8b
                stream.writeDouble(hitVec.z); // 8b
            }
            
            this.size = stream.size() - initialStreamSize;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        switch (FabricLoader.getInstance().getEnvironmentType()) {
            case CLIENT -> {
                handleClient(networkHandler);
            }
            case SERVER -> {
                handleServer(networkHandler);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public void handleClient(NetworkHandler networkHandler) {
        World world = Minecraft.INSTANCE.world;
        if (world != null) {
            Entity entity = getEntity(world, entityId);
            if (entity != null) {
                ProbeInfo probeInfo = getProbeInfo(Minecraft.INSTANCE.player, mode, world, entity, hitVec);
                PacketHelper.sendTo(Minecraft.INSTANCE.player, new PacketReturnEntityInfo(entityId, probeInfo));
            }
        }
    }

    @Environment(EnvType.SERVER)
    public void handleServer(NetworkHandler networkHandler) {
        if (networkHandler instanceof ServerPlayNetworkHandler handler) {
            World world = handler.player.world;
            if (world != null) {
                Entity entity = getEntity(world, entityId);
                if (entity != null) {
                    ProbeInfo probeInfo = getProbeInfo(handler.player, mode, world, entity, hitVec);
                    PacketHelper.sendTo(handler.player, new PacketReturnEntityInfo(entityId, probeInfo));
                }
            }
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    private static ProbeInfo getProbeInfo(PlayerEntity player, ProbeMode mode, World world, Entity entity, Vec3d hitVec) {
        if (Config.MAIN_CONFIG.needsProbe == PROBE_NEEDEDFOREXTENDED) {
            // We need a probe only for extended information
            if (!ProbeUtils.hasAProbeSomewhere(player)) {
                // No probe anywhere, switch EXTENDED to NORMAL
                if (mode == ProbeMode.EXTENDED) {
                    mode = ProbeMode.NORMAL;
                }
            }
        } else if (Config.MAIN_CONFIG.needsProbe == PROBE_NEEDEDHARD && !ProbeUtils.hasAProbeSomewhere(player)) {
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
