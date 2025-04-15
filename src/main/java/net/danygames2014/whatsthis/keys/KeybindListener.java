package net.danygames2014.whatsthis.keys;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.option.KeyBinding;
import net.modificationstation.stationapi.api.client.event.option.KeyBindingRegisterEvent;
import org.lwjgl.input.Keyboard;

public class KeybindListener {
    public static KeyBinding toggleVisible;
    public static KeyBinding toggleLiquids;

    @EventListener
    public void registerKeybinds(KeyBindingRegisterEvent event) {
        toggleVisible = new KeyBinding("key.whatsthis.toggle_visible", Keyboard.KEY_NONE);
        toggleLiquids = new KeyBinding("key.whatsthis.toggle_liquids", Keyboard.KEY_L);

        event.keyBindings.add(toggleVisible);
        event.keyBindings.add(toggleLiquids);
    }
}
