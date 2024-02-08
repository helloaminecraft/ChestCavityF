package net.tigereye.chestcavity.mixin;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SUpdateRecipesPacket;
import net.tigereye.chestcavity.network.NetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SUpdateRecipesPacket.class)
public class MixinRecipeUpdate { //add onto the recipes???
    //i guess we could lol idk ohwel nad yeah nah

    @Inject(at = @At("TAIL"), method = "write")
    public void sendOrganPacket(PacketBuffer buffer, CallbackInfo ci) {
        //NetworkHandler.CHANNEL.send()
    }
}
