package net.danygames2014.whatsthis.item;

import com.matthewperiut.accessoryapi.api.Accessory;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class ProbeGogglesItem extends TemplateItem implements Accessory {
    public ProbeGogglesItem(Identifier identifier) {
        super(identifier);
    }

    @Override
    public String[] getAccessoryTypes(ItemStack itemStack) {
        return new String[]{"probe"};
    }
}
