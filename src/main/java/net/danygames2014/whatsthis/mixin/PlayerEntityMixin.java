package net.danygames2014.whatsthis.mixin;

import net.danygames2014.whatsthis.WhatsThis;
import net.danygames2014.whatsthis.config.Config;
import net.danygames2014.whatsthis.item.GotNotePersistentState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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

    @Inject(method = "readNbt", at = @At(value = "TAIL"))
    public void aVoid(NbtCompound nbt, CallbackInfo ci) {
        if (world.isRemote) {
            return;
        }

        if (!Config.PROBE_CONFIG.spawnNote) {
            return;
        }

        GotNotePersistentState gotNoteState = GotNotePersistentState.get(world, "got_note");

        String username = this.name;
        
        // If we are in singleplayer, the name will be null, we get the name from the session instead 
        if (username == null && FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            username = getClientUsername();
        }

        if (!gotNoteState.gotNote(username)) {
            this.inventory.addStack(new ItemStack(WhatsThis.probeNote, 1));
            this.inventory.markDirty();
            gotNoteState.setGotNote(username, true);
        }
    }

    @Unique
    @Environment(EnvType.CLIENT)
    public String getClientUsername() {
        return Minecraft.INSTANCE.session.username;
    }
}
