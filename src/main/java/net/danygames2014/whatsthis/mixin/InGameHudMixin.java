package net.danygames2014.whatsthis.mixin;

import net.danygames2014.whatsthis.api.ProbeMode;
import net.danygames2014.whatsthis.config.Config;
import net.danygames2014.whatsthis.items.ProbeUtil;
import net.danygames2014.whatsthis.keys.KeybindListener;
import net.danygames2014.whatsthis.rendering.OverlayRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.danygames2014.whatsthis.config.ConfigSetup.*;

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

        if (ProbeUtil.isDebugProbe(Minecraft.INSTANCE.player.getHand())) {
            OverlayRenderer.renderHUD(ProbeMode.DEBUG, tickDelta);
        } else {
            switch (Config.MAIN_CONFIG.needsProbe) {
                case PROBE_NOTNEEDED:
                case PROBE_NEEDEDFOREXTENDED:
                    OverlayRenderer.renderHUD(getModeForPlayer(), tickDelta);
                    break;
                case PROBE_NEEDED:
                case PROBE_NEEDEDHARD:
                    if (ProbeUtil.hasAProbeSomewhere(Minecraft.INSTANCE.player)) {
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
        if (Config.MAIN_CONFIG.extendedInMain || ProbeUtil.isHandProbe(player.getHand())) {
            // If the player has a probe somewhere, switch to extended mode
            if (ProbeUtil.hasAProbeSomewhere(player)) {
                return ProbeMode.EXTENDED;
            }
        }

        return player.isSneaking() ? ProbeMode.EXTENDED : ProbeMode.NORMAL;
    }
}
