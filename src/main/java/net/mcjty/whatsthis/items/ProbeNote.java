package net.mcjty.whatsthis.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mcjty.whatsthis.gui.GuiConfig;
import net.mcjty.whatsthis.gui.GuiNote;
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
            Minecraft.INSTANCE.setScreen(new GuiConfig());
        } else {
            Minecraft.INSTANCE.setScreen(new GuiNote());
        }

        return stack;
    }
}
