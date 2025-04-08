//package net.mcjty.whatsthis;
//
//import net.mcjty.whatsthis.config.ConfigSetup;
//import net.mcjty.whatsthis.items.ModItems;
//import net.minecraft.entity.Entity;
//import net.minecraft.item.ItemStack;
//
//public class ForgeEventHandlers {
//
//
//    @SubscribeEvent
//    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
//        ConfigSetup.setupStyleConfig(ConfigSetup.mainConfig);
//        ConfigSetup.updateDefaultOverlayStyle();
//
//        if (ConfigSetup.mainConfig.hasChanged()) {
//            ConfigSetup.mainConfig.save();
//        }
//    }
//
//    @SubscribeEvent
//    public void onEntityConstructing(AttachCapabilitiesEvent<Entity> event){
//        if (event.getObject() instanceof EntityPlayer) {
//            if (!event.getObject().hasCapability(PlayerProperties.PLAYER_GOT_NOTE, null)) {
//                event.addCapability(new ResourceLocation(TheOneProbe.MODID, "Properties"), new PropertiesDispatcher());
//            }
//        }
//    }
//
//    @SubscribeEvent
//    public void onPlayerCloned(PlayerEvent.Clone event) {
//        if (event.isWasDeath()) {
//            // We need to copyFrom the capabilities
//            if (event.getOriginal().hasCapability(PlayerProperties.PLAYER_GOT_NOTE, null)) {
//                PlayerGotNote oldStore = event.getOriginal().getCapability(PlayerProperties.PLAYER_GOT_NOTE, null);
//                PlayerGotNote newStore = PlayerProperties.getPlayerGotNote(event.getEntityPlayer());
//                newStore.copyFrom(oldStore);
//            }
//        }
//    }
//
//    @SubscribeEvent
//    public void onPlayerLoggedIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
//        if (ConfigSetup.spawnNote) {
//            PlayerGotNote note = PlayerProperties.getPlayerGotNote(event.player);
//            if (!note.isPlayerGotNote()) {
//                boolean success = event.player.inventory.addItemStackToInventory(new ItemStack(ModItems.probeNote));
//                if (success) {
//                    note.setPlayerGotNote(true);
//                }
//            }
//        }
//    }
//}