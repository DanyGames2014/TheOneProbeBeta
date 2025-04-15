package net.danygames2014.whatsthis.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.danygames2014.whatsthis.gui.ConfigScreen;
import net.danygames2014.whatsthis.gui.NoteScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class ProbeNote extends TemplateItem {
    public ProbeNote(Identifier identifier) {
        super(identifier);
        setMaxCount(1);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public ItemStack use(ItemStack stack, World world, PlayerEntity user) {
        if (user.isSneaking()) {
            Minecraft.INSTANCE.setScreen(new ConfigScreen());
        } else {
            Minecraft.INSTANCE.setScreen(new NoteScreen());
        }

        return stack;
    }
}
