package net.tigereye.chestcavity.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.tigereye.chestcavity.chestcavities.instance.ChestCavityInstance;
import net.tigereye.chestcavity.interfaces.ChestCavityEntity;
import net.tigereye.chestcavity.network.NetworkHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ChestCavityUpdatePacket {
    private final boolean open;
    private final int size;
    private final Map<ResourceLocation,Float> organScoresMap;

    public ChestCavityUpdatePacket(boolean open, int size, Map<ResourceLocation,Float> organScoresMap) {
        this.open = open;
        this.size = size;
        this.organScoresMap = organScoresMap;
    }

    public static ChestCavityUpdatePacket decode(PacketBuffer buf) {
        boolean open = buf.readBoolean();
        int size = buf.readInt();
        Map<ResourceLocation,Float> organScores = new HashMap<>();
        for(int i = 0; i < size; i++){
            organScores.put(new ResourceLocation(buf.readUtf()),buf.readFloat());
        }
        return new ChestCavityUpdatePacket(open, size, organScores);
    }

    public void encode(PacketBuffer buf) {
        buf.writeBoolean(this.open);
        buf.writeInt(this.size);
        this.organScoresMap.forEach((id, value) -> {
            buf.writeUtf(id.toString());
            buf.writeFloat(value);
        });
    }

    public boolean handle(Supplier<NetworkEvent.Context> ctx) {
        AtomicBoolean success = new AtomicBoolean(false);
        ctx.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            Optional<ChestCavityEntity> optional = ChestCavityEntity.of(Minecraft.getInstance().cameraEntity);
            optional.ifPresent(chestCavityEntity -> {
                ChestCavityInstance instance = chestCavityEntity.getChestCavityInstance();
                instance.opened = this.open;
                instance.setOrganScores(this.organScoresMap);
                //NetworkHandler.CHANNEL.sendToServer(new RecievedChestCavityUpdatePacket());
                success.set(true);
            });
        }));
        ctx.get().setPacketHandled(true);
        return success.get();
    }
}
