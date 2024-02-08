package net.tigereye.chestcavity.network.packets;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.tigereye.chestcavity.interfaces.ChestCavityEntity;
import net.tigereye.chestcavity.listeners.OrganActivationListeners;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class ChestCavityHotkeyPacket {

    ResourceLocation location;

    public ChestCavityHotkeyPacket(ResourceLocation location) {
        this.location = location;
    }

    public ChestCavityHotkeyPacket(PacketBuffer buf) {
        this(buf.readResourceLocation());
    }

    public void encode(PacketBuffer buf) {
        buf.writeResourceLocation(this.location);
    }

    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        AtomicBoolean success = new AtomicBoolean(false);
        contextSupplier.get().enqueueWork(() -> {
            PlayerEntity player = contextSupplier.get().getSender();
            Optional<ChestCavityEntity> optional = ChestCavityEntity.of(player);
            optional.ifPresent(chestCavityEntity -> {
                OrganActivationListeners.activate(this.location, chestCavityEntity.getChestCavityInstance());
                success.set(true);
            });
        });
        contextSupplier.get().setPacketHandled(true);
        return  success.get();
    }
}
