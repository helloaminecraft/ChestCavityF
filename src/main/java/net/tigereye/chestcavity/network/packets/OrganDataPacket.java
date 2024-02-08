package net.tigereye.chestcavity.network.packets;


import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.chestcavities.organs.OrganData;
import net.tigereye.chestcavity.chestcavities.organs.OrganManager;
import net.tigereye.chestcavity.util.OrganDataPacketHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class OrganDataPacket {

    private int organCount;
    private ArrayList<OrganDataPacketHelper> helpers;

    public OrganDataPacket(int organCount, ArrayList<OrganDataPacketHelper> helpers) {
        this.organCount = organCount;
        this.helpers = helpers;
    }

    public static OrganDataPacket decode(PacketBuffer buf) {
        int organCount = buf.readInt();
        ArrayList<OrganDataPacketHelper> helpers = new ArrayList<>();
        for(int i = 0; i < organCount; i++) {
            ResourceLocation organID = buf.readResourceLocation();

            boolean isPseudoOrgan = buf.readBoolean();
            int organAbilityCount = buf.readInt();
            Map<ResourceLocation, Float> floatMap = new HashMap<>();
            for(int j = 0; j < organAbilityCount; j++) {
                floatMap.put(buf.readResourceLocation(), buf.readFloat());
            }
            helpers.add(new OrganDataPacketHelper(organID, isPseudoOrgan, organAbilityCount, floatMap));
        }
        return new OrganDataPacket(organCount, helpers);
    }

    public void encode(PacketBuffer buf) {
        buf.writeInt(this.organCount);
        this.helpers.forEach(helper -> {
            buf.writeResourceLocation(helper.getResourceLocation());
            buf.writeBoolean(helper.getPseudoOrgan());
            buf.writeInt(helper.getAbilityCount());
            helper.getMap().forEach((ability, score) -> {
                buf.writeResourceLocation(ability);
                buf.writeFloat(score);
            });
        });
    }

    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {
        AtomicBoolean success = new AtomicBoolean(false);
        contextSupplier.get().enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            System.out.println("BOONELDAN TEST PACKET RECEIVED");
            OrganManager.GeneratedOrganData.clear();
            this.helpers.forEach(helper -> {
                OrganData organData = new OrganData();
                organData.pseudoOrgan = helper.getPseudoOrgan();
                organData.organScores.putAll(helper.getMap());
                OrganManager.GeneratedOrganData.put(helper.getResourceLocation(), organData);
            });
            ChestCavity.LOGGER.info("loaded " + organCount + " organs from server");
            success.set(true);
        }));
        contextSupplier.get().setPacketHandled(true);
        return success.get();
    }
}
