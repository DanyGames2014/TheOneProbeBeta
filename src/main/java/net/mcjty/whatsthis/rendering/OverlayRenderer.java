package net.mcjty.whatsthis.rendering;

import net.mcjty.whatsthis.WhatsThis;
import net.mcjty.whatsthis.api.*;
import net.mcjty.whatsthis.apiimpl.ProbeHitData;
import net.mcjty.whatsthis.apiimpl.ProbeHitEntityData;
import net.mcjty.whatsthis.apiimpl.ProbeInfo;
import net.mcjty.whatsthis.apiimpl.elements.ElementProgress;
import net.mcjty.whatsthis.apiimpl.elements.ElementText;
import net.mcjty.whatsthis.apiimpl.providers.block.DefaultProbeInfoProvider;
import net.mcjty.whatsthis.apiimpl.providers.entity.DefaultProbeInfoEntityProvider;
import net.mcjty.whatsthis.apiimpl.styles.ProgressStyle;
import net.mcjty.whatsthis.config.Config;
import net.mcjty.whatsthis.config.ConfigSetup;
import net.mcjty.whatsthis.network.PacketGetEntityInfo;
import net.mcjty.whatsthis.network.PacketGetInfo;
import net.mcjty.whatsthis.network.ThrowableIdentity;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MultiplayerInteractionManager;
import net.minecraft.client.SingleplayerInteractionManager;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.util.Formatting;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static net.mcjty.whatsthis.api.TextStyleClass.ERROR;

@SuppressWarnings("DuplicatedCode")
public class OverlayRenderer {

    private static Map<Pair<Integer, BlockPos>, Pair<Long, ProbeInfo>> cachedInfo = new HashMap<>();
    private static Map<Integer, Pair<Long, ProbeInfo>> cachedEntityInfo = new HashMap<>();
    private static long lastCleanupTime = 0;

    // For a short while we keep displaying the last pair if we have no new information
    // to prevent flickering
    private static Pair<Long, ProbeInfo> lastPair;
    private static long lastPairTime = 0;

    // When the server delays too long we also show some preliminary information already
    private static long lastRenderedTime = -1;

    public static void renderHUD(ProbeMode mode, float partialTicks) {
        float dist = Config.MAIN_CONFIG.probeDistance;

        HitResult mouseOver = Minecraft.INSTANCE.crosshairTarget;
        if (mouseOver != null) {
            if (mouseOver.type == HitResultType.ENTITY) {
                GL11.glPushMatrix();

                double scale = Config.CLIENT_CONFIG.tooltipScale;

                Minecraft minecraft = Minecraft.INSTANCE;
                ScreenScaler screenScaler = new ScreenScaler(minecraft.options, minecraft.displayWidth, minecraft.displayHeight);
                double sw = screenScaler.getScaledWidth();
                double sh = screenScaler.getScaledHeight();

                RenderHelper.setupOverlayRendering(sw * scale, sh * scale);
                renderHUDEntity(mode, mouseOver, sw * scale, sh * scale);
                RenderHelper.setupOverlayRendering(sw, sh);
                GL11.glPopMatrix();

                checkCleanup();
                return;
            }
        }

        PlayerEntity entity = Minecraft.INSTANCE.player;
        Vec3d start = entity.getPosition(partialTicks);
        Vec3d vec31 = entity.getLookVector(partialTicks);
        Vec3d end = start.add(vec31.x * dist, vec31.y * dist, vec31.z * dist);

        mouseOver = entity.world.raycast(start, end, Config.CLIENT_CONFIG.showLiquids);
        if (mouseOver == null) {
            return;
        }

        if (mouseOver.type == HitResultType.BLOCK) {
            GL11.glPushMatrix();

            double scale = Config.CLIENT_CONFIG.tooltipScale;

            ScreenScaler scaledresolution = new ScreenScaler(Minecraft.INSTANCE.options, Minecraft.INSTANCE.displayWidth, Minecraft.INSTANCE.displayHeight);
            double sw = scaledresolution.getScaledWidth();
            double sh = scaledresolution.getScaledHeight();

            RenderHelper.setupOverlayRendering(sw * scale, sh * scale);
            renderHUDBlock(mode, mouseOver, sw * scale, sh * scale);
            RenderHelper.setupOverlayRendering(sw, sh);

            GL11.glPopMatrix();
        }

        checkCleanup();
    }

    private static void renderHUDBlock(ProbeMode mode, HitResult mouseOver, double sw, double sh) {
        BlockPos blockPos = new BlockPos(mouseOver.blockX, mouseOver.blockY, mouseOver.blockZ);

        PlayerEntity player = Minecraft.INSTANCE.player;
        if (player.world.isAir(blockPos.x, blockPos.y, blockPos.z)) {
            return;
        }

        long time = System.currentTimeMillis();

        IElement damageElement = null;
        if (Config.CLIENT_CONFIG.showBreakProgress > 0) {
            float damage = 0.0F;

            if (Minecraft.INSTANCE.interactionManager instanceof MultiplayerInteractionManager multiplayerInteractionManager) {
                damage = multiplayerInteractionManager.blockBreakingProgress;
            } else if (Minecraft.INSTANCE.interactionManager instanceof SingleplayerInteractionManager singleplayerInteractionManager) {
                damage = singleplayerInteractionManager.blockBreakingProgress;
            }

            if (damage > 0) {
                if (Config.CLIENT_CONFIG.showBreakProgress == 2) {
                    damageElement = new ElementText(Formatting.RED + "Progress " + (int) (damage * 100) + "%");
                } else {
                    damageElement = new ElementProgress((long) (damage * 100), 100, new ProgressStyle()
                            .prefix("Progress ")
                            .suffix("%")
                            .width(85)
                            .borderColor(0)
                            .filledColor(0)
                            .filledColor(0xff990000)
                            .alternateFilledColor(0xff550000));
                }
            }
        }

        int dimension = player.world.dimension.id;
        Pair<Integer, BlockPos> key = Pair.of(dimension, blockPos);
        Pair<Long, ProbeInfo> cacheEntry = cachedInfo.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {

            // To make sure we don't ask it too many times before the server got a chance to send the answer
            // we insert a dummy entry here for a while
            if (cacheEntry == null || time >= cacheEntry.getLeft()) {
                cachedInfo.put(key, Pair.of(time + 500, null));
                requestBlockInfo(mode, mouseOver, blockPos, player);
            }

            if (lastPair != null && time < lastPairTime + Config.MAIN_CONFIG.timeout) {
                renderElements(lastPair.getRight(), ConfigSetup.getDefaultOverlayStyle(), sw, sh, damageElement);
                lastRenderedTime = time;
            } else if (Config.MAIN_CONFIG.waitingForServerTimeout > 0 && lastRenderedTime != -1 && time > lastRenderedTime + Config.MAIN_CONFIG.waitingForServerTimeout) {
                // It has been a while. Show some info on client that we are
                // waiting for server information
                ProbeInfo info = getWaitingInfo(mode, mouseOver, blockPos, player);
                registerProbeInfo(dimension, blockPos, info);
                lastPair = Pair.of(time, info);
                lastPairTime = time;
                renderElements(lastPair.getRight(), ConfigSetup.getDefaultOverlayStyle(), sw, sh, damageElement);
                lastRenderedTime = time;
            }
        } else {
            if (time > cacheEntry.getLeft() + Config.MAIN_CONFIG.timeout) {
                // This info is slightly old. Update it

                // To make sure we don't ask it too many times before the server got a chance to send the answer
                // we increase the time a bit here
                cachedInfo.put(key, Pair.of(time + 500, cacheEntry.getRight()));
                requestBlockInfo(mode, mouseOver, blockPos, player);
            }
            renderElements(cacheEntry.getRight(), ConfigSetup.getDefaultOverlayStyle(), sw, sh, damageElement);
            lastRenderedTime = time;
            lastPair = cacheEntry;
            lastPairTime = time;
        }
    }

    private static void renderHUDEntity(ProbeMode mode, HitResult mouseOver, double sw, double sh) {
        Entity entity = mouseOver.entity;
        if (entity == null) {
            return;
        }

        String entityString = EntityRegistry.getId(entity);
        if (entityString == null && !(entity instanceof PlayerEntity)) {
            // We can't show info for this entity
            return;
        }

        int entityId = entity.id;

        PlayerEntity player = Minecraft.INSTANCE.player;
        long time = System.currentTimeMillis();

        Pair<Long, ProbeInfo> cacheEntry = cachedEntityInfo.get(entityId);
        if (cacheEntry == null || cacheEntry.getValue() == null) {

            // To make sure we don't ask it too many times before the server got a chance to send the answer
            // we insert a dummy entry here for a while
            if (cacheEntry == null || time >= cacheEntry.getLeft()) {
                cachedEntityInfo.put(entityId, Pair.of(time + 500, null));
                requestEntityInfo(mode, mouseOver, entity, player);
            }

            if (lastPair != null && time < lastPairTime + Config.MAIN_CONFIG.timeout) {
                renderElements(lastPair.getRight(), ConfigSetup.getDefaultOverlayStyle(), sw, sh, null);
                lastRenderedTime = time;
            } else if (Config.MAIN_CONFIG.waitingForServerTimeout > 0 && lastRenderedTime != -1 && time > lastRenderedTime + Config.MAIN_CONFIG.waitingForServerTimeout) {
                // It has been a while. Show some info on client that we are
                // waiting for server information
                ProbeInfo info = getWaitingEntityInfo(mode, mouseOver, entity, player);
                registerProbeInfo(entityId, info);
                lastPair = Pair.of(time, info);
                lastPairTime = time;
                renderElements(lastPair.getRight(), ConfigSetup.getDefaultOverlayStyle(), sw, sh, null);
                lastRenderedTime = time;
            }
        } else {
            if (time > cacheEntry.getLeft() + Config.MAIN_CONFIG.timeout) {
                // This info is slightly old. Update it

                // To make sure we don't ask it too many times before the server got a chance to send the answer
                // we increase the time a bit here
                cachedEntityInfo.put(entityId, Pair.of(time + 500, cacheEntry.getRight()));
                requestEntityInfo(mode, mouseOver, entity, player);
            }
            renderElements(cacheEntry.getRight(), ConfigSetup.getDefaultOverlayStyle(), sw, sh, null);
            lastRenderedTime = time;
            lastPair = cacheEntry;
            lastPairTime = time;
        }
    }

    public static void renderElements(ProbeInfo probeInfo, IOverlayStyle style, double sw, double sh, @Nullable IElement extra) {
        if (extra != null) {
            probeInfo.element(extra);
        }

        RenderHelper.disableLighting();

        int scaledWidth = (int) sw;
        int scaledHeight = (int) sh;

        int w = probeInfo.getWidth();
        int h = probeInfo.getHeight();

        int offset = style.getBorderOffset();
        int thick = style.getBorderThickness();
        int margin = 0;
        if (thick > 0) {
            w += (offset + thick + 3) * 2;
            h += (offset + thick + 3) * 2;
            margin = offset + thick + 3;
        }

        int x;
        int y;
        if (style.getLeftX() != -1) {
            x = style.getLeftX();
        } else if (style.getRightX() != -1) {
            x = scaledWidth - w - style.getRightX();
        } else {
            x = (scaledWidth - w) / 2;
        }
        if (style.getTopY() != -1) {
            y = style.getTopY();
        } else if (style.getBottomY() != -1) {
            y = scaledHeight - h - style.getBottomY();
        } else {
            y = (scaledHeight - h) / 2;
        }

        if (thick > 0) {
            if (offset > 0) {
                RenderHelper.drawThickBeveledBox(x, y, x + w - 1, y + h - 1, thick, style.getBoxColor(), style.getBoxColor(), style.getBoxColor());
            }
            RenderHelper.drawThickBeveledBox(x + offset, y + offset, x + w - 1 - offset, y + h - 1 - offset, thick, style.getBorderColor(), style.getBorderColor(), style.getBoxColor());
        }

        if (!Minecraft.INSTANCE.paused) {
            RenderHelper.rot += .5f;
        }

        probeInfo.render(x + margin, y + margin);
        if (extra != null) {
            probeInfo.removeElement(extra);
        }
    }

    // Request Info
    private static void requestBlockInfo(ProbeMode mode, HitResult mouseOver, BlockPos pos, PlayerEntity player) {
        World world = player.world;
        BlockState blockState = world.getBlockState(pos);

        ItemStack pickBlock = new ItemStack(blockState.getBlock(), 1, world.getBlockMeta(pos.x, pos.y, pos.z));

//         TODO: handle pickBlock
//        ItemStack pickBlock = block.getPickBlock(blockState, mouseOver, world, blockPos, player);
//        if (pickBlock == null || (!pickBlock.isEmpty() && pickBlock.getItem() == null)) {
//            // Protection for some invalid items.
//            pickBlock = ItemStack.EMPTY;
//        }
//        if (pickBlock != null && (!pickBlock.isEmpty()) && ConfigSetup.getDontSendNBTSet().contains(pickBlock.getItem().getRegistryName())) {
//            pickBlock = pickBlock.copy();
//            pickBlock.setTagCompound(null);
//        }

        PacketHelper.send(new PacketGetInfo(world.dimension.id, pos, mode, mouseOver, pickBlock));
    }
    private static void requestEntityInfo(ProbeMode mode, HitResult mouseOver, Entity entity, PlayerEntity player) {
        PacketHelper.send(new PacketGetEntityInfo(player.world.dimension.id, mode, mouseOver, entity));
    }

    // Register Info
    public static void registerProbeInfo(int dim, BlockPos pos, ProbeInfo probeInfo) {
        if (probeInfo == null) {
            return;
        }
        long time = System.currentTimeMillis();
        cachedInfo.put(Pair.of(dim, pos), Pair.of(time, probeInfo));
    }

    public static void registerProbeInfo(int entityId, ProbeInfo probeInfo) {
        if (probeInfo == null) {
            return;
        }
        long time = System.currentTimeMillis();
        cachedEntityInfo.put(entityId, Pair.of(time, probeInfo));
    }


    // Waiting Info
    private static ProbeInfo getWaitingInfo(ProbeMode mode, HitResult mouseOver, BlockPos pos, PlayerEntity player) {
        ProbeInfo probeInfo = WhatsThis.theOneProbeImp.create();

        World world = player.world;
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        ItemStack pickBlock = new ItemStack(block, 1, world.getBlockMeta(pos.x, pos.y, pos.z));
        IProbeHitData data = new ProbeHitData(pos, Vec3d.create(mouseOver.blockX, mouseOver.blockY, mouseOver.blockZ), Direction.byId(mouseOver.side), pickBlock);

        IProbeConfig probeConfig = WhatsThis.theOneProbeImp.createProbeConfig();

        try {
            DefaultProbeInfoProvider.showStandardBlockInfo(mode, probeInfo, world, pos, state, block, player, data, probeConfig);
        } catch (Exception e) {
            ThrowableIdentity.registerThrowable(e);
            probeInfo.text(ERROR + "Error (see log for details)!");
        }

        probeInfo.text(ERROR + "Waiting for server...");
        return probeInfo;
    }

    private static ProbeInfo getWaitingEntityInfo(ProbeMode mode, HitResult mouseOver, Entity entity, PlayerEntity player) {
        ProbeInfo probeInfo = WhatsThis.theOneProbeImp.create();
        IProbeHitEntityData data = new ProbeHitEntityData(Vec3d.create(mouseOver.blockX, mouseOver.blockY, mouseOver.blockZ));

        IProbeConfig probeConfig = WhatsThis.theOneProbeImp.createProbeConfig();
        try {
            DefaultProbeInfoEntityProvider.showStandardInfo(mode, probeInfo, entity, probeConfig);
        } catch (Exception e) {
            ThrowableIdentity.registerThrowable(e);
            probeInfo.text(ERROR + "Error (see log for details)!");
        }

        probeInfo.text(ERROR + "Waiting for server...");
        return probeInfo;
    }

    // Cleanup
    private static void checkCleanup() {
        long time = System.currentTimeMillis();
        if (time > lastCleanupTime + 5000) {
            cleanupCachedBlocks(time);
            cleanupCachedEntities(time);
            lastCleanupTime = time;
        }
    }

    private static void cleanupCachedBlocks(long time) {
        // It has been a while. Time to clean up unused cached pairs.
        Map<Pair<Integer, BlockPos>, Pair<Long, ProbeInfo>> newCachedInfo = new HashMap<>();
        for (Map.Entry<Pair<Integer, BlockPos>, Pair<Long, ProbeInfo>> entry : cachedInfo.entrySet()) {
            long t = entry.getValue().getLeft();
            if (time < t + Config.MAIN_CONFIG.timeout + 1000) {
                newCachedInfo.put(entry.getKey(), entry.getValue());
            }
        }
        cachedInfo = newCachedInfo;
    }

    private static void cleanupCachedEntities(long time) {
        // It has been a while. Time to clean up unused cached pairs.
        Map<Integer, Pair<Long, ProbeInfo>> newCachedInfo = new HashMap<>();
        for (Map.Entry<Integer, Pair<Long, ProbeInfo>> entry : cachedEntityInfo.entrySet()) {
            long t = entry.getValue().getLeft();
            if (time < t + Config.MAIN_CONFIG.timeout + 1000) {
                newCachedInfo.put(entry.getKey(), entry.getValue());
            }
        }
        cachedEntityInfo = newCachedInfo;
    }
}
