package net.danygames2014.whatsthis.keys;

import net.danygames2014.whatsthis.config.Config;
import net.danygames2014.whatsthis.config.ConfigSetup;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.keyboard.KeyStateChangedEvent;
import org.lwjgl.input.Keyboard;

public class KeyInputListener {
    @EventListener
    public void handle(KeyStateChangedEvent event) {
        if (Keyboard.getEventKeyState() && Keyboard.getEventKey() != Keyboard.KEY_NONE) {
            if (event.environment == KeyStateChangedEvent.Environment.IN_GAME) {
                if (Keyboard.isKeyDown(KeybindListener.toggleVisible.code)) {
                    ConfigSetup.setVisible(!Config.CLIENT_CONFIG.isVisible);
                }

                if (Keyboard.isKeyDown(KeybindListener.toggleLiquids.code)) {
                    ConfigSetup.setLiquids(!Config.CLIENT_CONFIG.showLiquids);
                }
            }
        }
    }
}
