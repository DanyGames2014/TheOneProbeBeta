package net.mcjty.whatsthis.keys;

import net.mcjty.whatsthis.config.Config;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.keyboard.KeyStateChangedEvent;
import org.lwjgl.input.Keyboard;

public class KeyInputListener {
    @EventListener
    public void handle(KeyStateChangedEvent event) {
        if (Keyboard.getEventKeyState() && Keyboard.getEventKey() != Keyboard.KEY_NONE) {
            if (event.environment == KeyStateChangedEvent.Environment.IN_GAME) {
                if (Keyboard.isKeyDown(KeybindListener.toggleVisible.code)) {
                    Config.CLIENT_CONFIG.isVisible = !Config.CLIENT_CONFIG.isVisible;
                }
                
                if(Keyboard.isKeyDown(KeybindListener.toggleLiquids.code)) {
                    Config.CLIENT_CONFIG.showLiquids = !Config.CLIENT_CONFIG.showLiquids;   
                }
            }
        }
    }
}
