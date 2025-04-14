package net.mcjty.whatsthis.mixin;

import net.mcjty.whatsthis.api.ProbeMode;
import net.mcjty.whatsthis.config.Config;
import net.mcjty.whatsthis.items.ProbeUtils;
import net.mcjty.whatsthis.keys.KeybindListener;
import net.mcjty.whatsthis.rendering.OverlayRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.mcjty.whatsthis.config.ConfigSetup.*;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glColor4f(FFFF)V", remap = false))
    public void renderOverlay(float tickDelta, boolean screenOpen, int mouseX, int mouseY, CallbackInfo ci) {
        if (Config.CLIENT_CONFIG.holdKeyToMakeVisible) {
            if (!Keyboard.isKeyDown(KeybindListener.toggleVisible.code)) {
                return;
            }
        } else {
            if (!Config.CLIENT_CONFIG.isVisible) {
                return;
            }
        }

        if (ProbeUtils.isDebugProbe(Minecraft.INSTANCE.player.getHand())) {
            OverlayRenderer.renderHUD(ProbeMode.DEBUG, tickDelta);
        } else {
            switch (Config.MAIN_CONFIG.needsProbe) {
                case PROBE_NOTNEEDED:
                case PROBE_NEEDEDFOREXTENDED:
                    OverlayRenderer.renderHUD(getModeForPlayer(), tickDelta);
                    break;
                case PROBE_NEEDED:
                case PROBE_NEEDEDHARD:
                    if (ProbeUtils.hasAProbeSomewhere(Minecraft.INSTANCE.player)) {
                        OverlayRenderer.renderHUD(getModeForPlayer(), tickDelta);
                    }
                    break;
            }
        }
    }

    @Unique
    private ProbeMode getModeForPlayer() {
        PlayerEntity player = Minecraft.INSTANCE.player;

        // If the mode is extended by default or the player is holding a probe
        if (Config.MAIN_CONFIG.extendedInMain || ProbeUtils.isHandProbe(player.getHand())) {
            // If the player has a probe somewhere, switch to extended mode
            if (ProbeUtils.hasAProbeSomewhere(player)) {
                return ProbeMode.EXTENDED;
            }
        }

        return player.isSneaking() ? ProbeMode.EXTENDED : ProbeMode.NORMAL;
    }
}
