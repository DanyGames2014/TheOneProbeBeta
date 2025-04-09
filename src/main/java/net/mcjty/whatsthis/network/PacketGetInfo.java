package net.mcjty.whatsthis.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.mcjty.whatsthis.WhatsThis;
import net.mcjty.whatsthis.api.*;
import net.mcjty.whatsthis.apiimpl.ProbeHitData;
import net.mcjty.whatsthis.apiimpl.ProbeInfo;
import net.mcjty.whatsthis.config.ConfigSetup;
import net.mcjty.whatsthis.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import static net.mcjty.whatsthis.api.TextStyleClass.ERROR;
import static net.mcjty.whatsthis.api.TextStyleClass.LABEL;
import static net.mcjty.whatsthis.config.ConfigSetup.PROBE_NEEDEDFOREXTENDED;
import static net.mcjty.whatsthis.config.ConfigSetup.PROBE_NEEDEDHARD;

public class PacketGetInfo extends Packet implements ManagedPacket<PacketGetInfo> {
    public static final PacketType<PacketGetInfo> TYPE = PacketType.builder(false, true, PacketGetInfo::new).build();

    private int dim;
    private BlockPos pos;
    private ProbeMode mode;
    private Direction sideHit;
    private Vec3d hitVec;
    private ItemStack pickBlock;

    private int size = 0;

    public PacketGetInfo() {
    }

    public PacketGetInfo(int dim, BlockPos pos, ProbeMode mode, HitResult mouseOver, ItemStack pickBlock) {
        this.dim = dim;
        this.pos = pos;
        this.mode = mode;
        this.sideHit = Direction.byId(mouseOver.side);
        this.hitVec = Vec3d.create(mouseOver.blockX, mouseOver.blockY, mouseOver.blockZ);
        this.pickBlock = pickBlock;
    }

    @Override
    public void read(DataInputStream buf) {
        try {
            dim = buf.readInt();
            pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
            mode = ProbeMode.values()[buf.readByte()];
            byte sideByte = buf.readByte();
            if (sideByte == 127) {
                sideHit = null;
            } else {
                sideHit = Direction.values()[sideByte];
            }
            if (buf.readBoolean()) {
                hitVec = Vec3d.create(buf.readDouble(), buf.readDouble(), buf.readDouble());
            }

            //pickBlock = ByteBufUtils.readItemStack(buf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(dim); // 4b
            stream.writeInt(pos.getX()); // 4b
            stream.writeInt(pos.getY()); // 4b
            stream.writeInt(pos.getZ()); // 4b
            stream.writeByte(mode.ordinal()); // 1b
            stream.writeByte(sideHit == null ? 127 : sideHit.ordinal()); // 1b
            if (hitVec == null) {
                stream.writeBoolean(false); // 1b

                this.size = 19;
            } else {
                stream.writeBoolean(true); // 1b
                stream.writeDouble(hitVec.x); // 8b 
                stream.writeDouble(hitVec.y); // 8b
                stream.writeDouble(hitVec.z); // 8b

                this.size = 43;
            }
            
            // TODO: Write the item

//            ByteBuff buffer = Unpooled.buffer();
//            ByteBufUtils.writeItemStack(buffer, pickBlock);
//            if (buffer.writerIndex() <= ConfigSetup.maxPacketToServer) {
//                buf.writeBytes(buffer);
//            } else {
//                ItemStack copy = new ItemStack(pickBlock.getItem(), pickBlock.getCount(), pickBlock.getMetadata());
//                ByteBufUtils.writeItemStack(buf, copy);
//            }

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
            ProbeInfo probeInfo = getProbeInfo(Minecraft.INSTANCE.player, mode, world, pos, sideHit, hitVec, pickBlock);
            PacketHelper.sendTo(Minecraft.INSTANCE.player, new PacketReturnInfo(dim, pos, probeInfo));
        }
    }

    @Environment(EnvType.SERVER)
    public void handleServer(NetworkHandler networkHandler) {
        if (networkHandler instanceof ServerPlayNetworkHandler handler) {
            World world = handler.player.world;
            if (world != null) {
                ProbeInfo probeInfo = getProbeInfo(handler.player, mode, world, pos, sideHit, hitVec, pickBlock);
                PacketHelper.sendTo(handler.player, new PacketReturnInfo(dim, pos, probeInfo));
            }
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public @NotNull PacketType<PacketGetInfo> getType() {
        return TYPE;
    }

//    public static class Handler implements IMessageHandler<PacketGetInfo, IMessage> {
//        @Override
//        public IMessage onMessage(PacketGetInfo message, MessageContext ctx) {
//            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
//            return null;
//        }
//
//        private void handle(PacketGetInfo message, MessageContext ctx) {
//            WorldServer world = DimensionManager.getWorld(message.dim);
//            if (world != null) {
//                ProbeInfo probeInfo = getProbeInfo(ctx.getServerHandler().player,
//                        message.mode, world, message.pos, message.sideHit, message.hitVec, message.pickBlock);
//                PacketHandler.INSTANCE.sendTo(new PacketReturnInfo(message.dim, message.pos, probeInfo), ctx.getServerHandler().player);
//            }
//        }
//    }

    private static ProbeInfo getProbeInfo(PlayerEntity player, ProbeMode mode, World world, BlockPos blockPos, Direction sideHit, Vec3d hitVec, ItemStack pickBlock) {
        if (ConfigSetup.needsProbe == PROBE_NEEDEDFOREXTENDED) {
            // We need a probe only for extended information
            if (!ModItems.hasAProbeSomewhere(player)) {
                // No probe anywhere, switch EXTENDED to NORMAL
                if (mode == ProbeMode.EXTENDED) {
                    mode = ProbeMode.NORMAL;
                }
            }
        } else if (ConfigSetup.needsProbe == PROBE_NEEDEDHARD && !ModItems.hasAProbeSomewhere(player)) {
            // The server says we need a probe but we don't have one in our hands
            return null;
        }

        BlockState state = world.getBlockState(blockPos);
        ProbeInfo probeInfo = WhatsThis.theOneProbeImp.create();
        IProbeHitData data = new ProbeHitData(blockPos, hitVec, sideHit, pickBlock);

        IProbeConfig probeConfig = WhatsThis.theOneProbeImp.createProbeConfig();
        List<IProbeConfigProvider> configProviders = WhatsThis.theOneProbeImp.getConfigProviders();
        for (IProbeConfigProvider configProvider : configProviders) {
            configProvider.getProbeConfig(probeConfig, player, world, state, data);
        }
        ConfigSetup.setRealConfig(probeConfig);

        List<IProbeInfoProvider> providers = WhatsThis.theOneProbeImp.getProviders();
        for (IProbeInfoProvider provider : providers) {
            try {
                provider.addProbeInfo(mode, probeInfo, player, world, state, data);
            } catch (Throwable e) {
                ThrowableIdentity.registerThrowable(e);
                probeInfo.text(LABEL + "Error: " + ERROR + provider.getID());
            }
        }
        return probeInfo;
    }

}
