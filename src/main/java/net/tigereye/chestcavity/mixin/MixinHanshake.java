package net.tigereye.chestcavity.mixin;


import net.minecraft.network.NetworkManager;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;
import net.tigereye.chestcavity.chestcavities.organs.OrganManager;
import net.tigereye.chestcavity.network.NetworkHandler;
import net.tigereye.chestcavity.network.packets.OrganDataPacket;
import net.tigereye.chestcavity.util.OrganDataPacketHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(NetworkHooks.class)
public class MixinHanshake {     //ondeatj save [ps amd tp to last death command wish idk ohwel adn yeah                           //mixin or not idk oh well yeah spell wrong i know but other mod for longest time everything lol idk ohwe lnadyeah sad lol

    @Inject(at = @At("TAIL"), method = "sendMCRegistryPackets", remap = false)
    private static void sendServerPackets(NetworkManager manager, String direction, CallbackInfo ci) { //(NetworkManager networkManager, NetworkDirection side, CallbackInfo ci) {
        //System.out.println("EVENT CALLED!");
        if(direction.equals("PLAY_TO_CLIENT")) { //soemthing == somethingelse boolean and must be same either both true both false but if one wrong idk ohwe ll xnor maybe idk ohwell nady eah
            ArrayList<NetworkManager> managers = new ArrayList<>();
            managers.add(manager);
            int count = OrganManager.GeneratedOrganData.size();
            ArrayList<OrganDataPacketHelper> helpers = new ArrayList<>();
            OrganManager.GeneratedOrganData.forEach((id, data) -> helpers.add(new OrganDataPacketHelper(id, data.pseudoOrgan, data.organScores.size(), data.organScores)));
            //System.out.println("BOONELDAN TEST PACKET SENT");
            NetworkHandler.CHANNEL.send(PacketDistributor.NMLIST.with(() -> managers), new OrganDataPacket(count, helpers));
        }

        //problem was not only that the item mixin did not work, but also that the packet didnt even send lo lyeah both
    } //Backpack does infact block the chest opener cause chest plate on chest slot lol idko hwell nady yeah all messed up lol idko h wel and yeah
} //events bloc classes lock yeah know later yeah better click clack code type idk ohwel nad yeah and blank lol cornmaze dat e lol and yeahliek idk oh well and eyah STOP just dp leave it actally yeah
