package net.mcjty.whatsthis.mixin;

import net.mcjty.whatsthis.WhatsThis;
import net.mcjty.whatsthis.items.ModItems;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingRecipeManager.class)
public class CraftingRecipeManagerMixin {
    @Unique
    private boolean hasProbe = false;
    @Unique
    private ItemStack helmet = null;

    @Inject(method = "craft", at = @At(value = "HEAD"), cancellable = true)
    public void probifyHelmet(CraftingInventory c, CallbackInfoReturnable<ItemStack> cir) {
        for (ItemStack stack : c.stacks) {
            if (stack != null) {
                if (stack.isOf(WhatsThis.probe)) {
                    hasProbe = true;
                } else if (stack.getItem() instanceof ArmorItem armorItem) {
                    if (armorItem.equipmentSlot == 0) {
                        helmet = stack;
                    }
                }
            }
        }

        if (hasProbe && helmet != null) {
            helmet.getStationNbt().putBoolean(ModItems.PROBETAG, true);
            cir.setReturnValue(helmet);
        }
    }
}
