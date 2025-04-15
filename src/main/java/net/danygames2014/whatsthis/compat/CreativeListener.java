package net.danygames2014.whatsthis.compat;

import net.danygames2014.whatsthis.WhatsThis;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import paulevs.bhcreative.api.CreativeTab;
import paulevs.bhcreative.api.SimpleTab;
import paulevs.bhcreative.registry.TabRegistryEvent;

import static net.danygames2014.whatsthis.WhatsThis.*;

public class CreativeListener {
    public static CreativeTab tab;

    @EventListener
    public void onTabInit(TabRegistryEvent event) {
        tab = new SimpleTab(WhatsThis.NAMESPACE.id("creative_tab"), WhatsThis.probe);
        event.register(tab);

        add(probeNote);
        add(probe);
        if (probeGoggles != null) {
            add(probeGoggles);
        }
        add(creativeProbe);
    }

    private static void add(Block block) {
        tab.addItem(new ItemStack(block));
    }

    private static void add(Item item) {
        tab.addItem(new ItemStack(item));
    }
}
