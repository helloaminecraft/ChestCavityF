package net.tigereye.chestcavity.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.chestcavities.organs.OrganManager;
import net.tigereye.chestcavity.network.NetworkHandler;
import net.tigereye.chestcavity.network.packets.OrganDataPacket;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = ChestCavity.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.DEDICATED_SERVER)
public class ForgeEventBusSubscriber {

}
