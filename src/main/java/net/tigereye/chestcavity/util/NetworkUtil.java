package net.tigereye.chestcavity.util;


import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.PacketDistributor;
import net.tigereye.chestcavity.chestcavities.instance.ChestCavityInstance;
import net.tigereye.chestcavity.network.NetworkHandler;
import net.tigereye.chestcavity.network.packets.ChestCavityUpdatePacket;

import java.util.Map;

public class NetworkUtil {
    //S2C = SERVER TO CLIENT //I think

    public static boolean SendS2CChestCavityUpdatePacket(ChestCavityInstance cc){ //FIXED THIS SO PUT IN CHANGELOG MAKE SURE PLEASE
        cc.updateInstantiated = true;
        if((!cc.owner.level.isClientSide()) && cc.owner instanceof ServerPlayerEntity) {
            ServerPlayerEntity spe = (ServerPlayerEntity) cc.owner;
            if(spe.connection == null) {
                return false;
            }
            Map<ResourceLocation, Float> organScores = cc.getOrganScores();
            NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> spe), new ChestCavityUpdatePacket(cc.opened, organScores.size(), organScores)); //Not PLAYER, it crashes when loading a world idk why
            return true;
        }
        return false;
    }
}
