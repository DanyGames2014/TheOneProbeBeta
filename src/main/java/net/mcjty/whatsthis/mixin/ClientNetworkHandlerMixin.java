package net.mcjty.whatsthis.mixin;

import net.minecraft.client.network.ClientNetworkHandler;
import net.minecraft.network.packet.play.UpdateSignPacket;
import net.minecraft.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientNetworkHandler.class)
public class ClientNetworkHandlerMixin {
    @Shadow private ClientWorld world;

    @Inject(method = "handleUpdateSign", at = @At(value = "HEAD"), cancellable = true)
    public void diagnoseSkillIssue(UpdateSignPacket packet, CallbackInfo ci){
        if(this.world == null) {
            System.err.println("x:" + packet.x + ", y:" + packet.y + ", z:" + packet.z);
            for (String line : packet.text) {
                System.err.println(line);
            }

            ci.cancel();
        }
    }
    
}
