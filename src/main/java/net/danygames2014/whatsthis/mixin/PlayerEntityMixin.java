package net.danygames2014.whatsthis.mixin;

import net.danygames2014.whatsthis.GotNotePersistentState;
import net.danygames2014.whatsthis.WhatsThis;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin extends LivingEntity {
    @Shadow
    public PlayerInventory inventory;

    @Shadow
    public String name;

    public PlayerEntityMixin(World world) {
        super(world);
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void aVoid(World world, CallbackInfo ci) {
        if (world.isRemote) {
            return;
        }

        GotNotePersistentState gotNoteState = GotNotePersistentState.get(world, "got_note");

        if (!gotNoteState.gotNote(name)) {
            this.inventory.addStack(new ItemStack(WhatsThis.probeNote, 1));
            this.inventory.markDirty();
            gotNoteState.setGotNote(name, true);
        }

//        if (!playerGotNote) {
//            this.inventory.addStack(new ItemStack(WhatsThis.probeNote, 1));
//            this.inventory.markDirty();
//            playerGotNote = true;
//        }
    }

    @Inject(method = "writeNbt", at = @At(value = "TAIL"))
    public void write(NbtCompound nbt, CallbackInfo ci) {
//        if(world.isRemote) {
//            return;
//        }
//        
//        nbt.putBoolean("playerGotNote", playerGotNote);
    }

    @Inject(method = "readNbt", at = @At(value = "TAIL"))
    public void read(NbtCompound nbt, CallbackInfo ci) {
//        if (world.isRemote) {
//            return;
//        }
//        
//        playerGotNote = nbt.getBoolean("playerGotNote");
//
//        if (!playerGotNote) {
//            this.inventory.addStack(new ItemStack(WhatsThis.probeNote, 1));
//            this.inventory.markDirty();
//            playerGotNote = true;
//        }
    }
}
