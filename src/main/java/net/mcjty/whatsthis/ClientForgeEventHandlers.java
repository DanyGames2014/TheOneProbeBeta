//package net.mcjty.whatsthis;
//
//import net.mcjty.whatsthis.api.ProbeMode;
//import net.mcjty.whatsthis.config.ConfigSetup;
//import net.mcjty.whatsthis.items.ModItems;
//import net.mcjty.whatsthis.rendering.OverlayRenderer;
//import net.minecraft.client.Minecraft;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//
//import static net.mcjty.whatsthis.config.ConfigSetup.*;
//
// Moved to InGameHudMixin
//
//public class ClientForgeEventHandlers {
//
//    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
//    public void renderGameOverlayEvent(RenderGameOverlayEvent.Pre event) {
//        if (event.getType() != RenderGameOverlayEvent.ElementType.TEXT) {
//            return;
//        }
//
//        if (ConfigSetup.holdKeyToMakeVisible) {
//            if (!KeyBindings.toggleVisible.isKeyDown()) {
//                return;
//            }
//        } else {
//            if (!ConfigSetup.isVisible) {
//                return;
//            }
//        }
//
//        if (hasItemInMainHand(ModItems.creativeProbe)) {
//            OverlayRenderer.renderHUD(ProbeMode.DEBUG, event.getPartialTicks());
//        } else {
//            switch (ConfigSetup.needsProbe) {
//                case PROBE_NOTNEEDED:
//                case PROBE_NEEDEDFOREXTENDED:
//                    OverlayRenderer.renderHUD(getModeForPlayer(), event.getPartialTicks());
//                    break;
//                case PROBE_NEEDED:
//                case PROBE_NEEDEDHARD:
//                    if (ModItems.hasAProbeSomewhere(Minecraft.getMinecraft().player)) {
//                        OverlayRenderer.renderHUD(getModeForPlayer(), event.getPartialTicks());
//                    }
//                    break;
//            }
//        }
//    }
//
//    private ProbeMode getModeForPlayer() {
//        PlayerEntity player = Minecraft.INSTANCE.player;
//        if (ConfigSetup.extendedInMain) {
//            if (hasItemInMainHand(ModItems.probe)) {
//                return ProbeMode.EXTENDED;
//            }
//        }
//        return player.isSneaking() ? ProbeMode.EXTENDED : ProbeMode.NORMAL;
//    }
//
//
//    private boolean hasItemInMainHand(Item item) {
//        ItemStack mainHeldItem = Minecraft.INSTANCE.player.getHand();
//        return mainHeldItem != null && mainHeldItem.getItem() == item;
//    }
//}