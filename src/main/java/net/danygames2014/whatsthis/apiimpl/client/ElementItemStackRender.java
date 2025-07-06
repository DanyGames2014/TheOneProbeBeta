package net.danygames2014.whatsthis.apiimpl.client;

import net.danygames2014.whatsthis.api.IItemStyle;
import net.danygames2014.whatsthis.rendering.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.util.Formatting;

public class ElementItemStackRender {

    public static void render(ItemStack itemStack, IItemStyle style, int x, int y) {
        // TODO: Find proper mapping
        //ItemRenderer itemRender = Minecraft.INSTANCE.getRenderItem();
        ItemRenderer itemRender = new ItemRenderer();

        if (itemStack != null && itemStack.count != 0 && itemStack.getItem() != null) {
            int size = itemStack.count;
            String amount;
            if (size <= 1) {
                amount = "";
            } else if (size < 100000) {
                amount = String.valueOf(size);
            } else if (size < 1000000) {
                amount = String.valueOf(size / 1000) + "k";
            } else if (size < 1000000000) {
                amount = String.valueOf(size / 1000000) + "m";
            } else {
                amount = String.valueOf(size / 1000000000) + "g";
            }

            if (!RenderHelper.renderItemStack(Minecraft.INSTANCE, itemRender, itemStack, x + (style.getWidth() - 18) / 2, y + (style.getHeight() - 18) / 2, amount)) {
                // There was a crash rendering this item
                RenderHelper.renderText(Minecraft.INSTANCE, x, y, Formatting.RED + "ERROR: " + itemStack.getItem().getTranslatedName());
            }
        }
    }

}
