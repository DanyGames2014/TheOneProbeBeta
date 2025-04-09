package net.mcjty.whatsthis.mixin;

import net.mcjty.whatsthis.api.ProbeMode;
import net.mcjty.whatsthis.config.Config;
import net.mcjty.whatsthis.items.ModItems;
import net.mcjty.whatsthis.rendering.OverlayRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.mcjty.whatsthis.config.ConfigSetup.*;
import static net.mcjty.whatsthis.config.ConfigSetup.PROBE_NEEDEDHARD;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glColor4f(FFFF)V", remap = false))
    public void renderOverlay(float tickDelta, boolean screenOpen, int mouseX, int mouseY, CallbackInfo ci) {
        if (Config.CLIENT_CONFIG.holdKeyToMakeVisible) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) { //!KeyBindings.toggleVisible.isKeyDown()) {
                return;
            }
        } else {
            if (!Config.CLIENT_CONFIG.isVisible) {
                return;
            }
        }

        if (hasItemInMainHand(Item.STICK)) {
            OverlayRenderer.renderHUD(ProbeMode.DEBUG, tickDelta);
        } else {
            switch (Config.MAIN_CONFIG.needsProbe) {
                case PROBE_NOTNEEDED:
                case PROBE_NEEDEDFOREXTENDED:
                    OverlayRenderer.renderHUD(getModeForPlayer(), tickDelta);
                    break;
                case PROBE_NEEDED:
                case PROBE_NEEDEDHARD:
                    if (ModItems.hasAProbeSomewhere(Minecraft.INSTANCE.player)) {
                        OverlayRenderer.renderHUD(getModeForPlayer(), tickDelta);
                    }
                    break;
            }
        }
    }

    @Unique
    private ProbeMode getModeForPlayer() {
        PlayerEntity player = Minecraft.INSTANCE.player;
        if (Config.MAIN_CONFIG.extendedInMain) {
            if (hasItemInMainHand(Item.BOOK)) {
                return ProbeMode.EXTENDED;
            }
        }
        return player.isSneaking() ? ProbeMode.EXTENDED : ProbeMode.NORMAL;
    }


    @Unique
    private boolean hasItemInMainHand(Item item) {
        ItemStack mainHeldItem = Minecraft.INSTANCE.player.getHand();
        return mainHeldItem != null && mainHeldItem.getItem() == item;
    }
}
