package net.tigereye.chestcavity.listeners;


import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.network.NetworkHandler;
import net.tigereye.chestcavity.network.packets.ChestCavityHotkeyPacket;
import net.tigereye.chestcavity.registration.CCKeybindings;
import net.tigereye.chestcavity.registration.CCOrganScores;

@Mod.EventBusSubscriber(modid = ChestCavity.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KeybindingClientListeners {
    private static PlayerEntity player = null;

    @SubscribeEvent
    public static void checkForKeys(TickEvent.ClientTickEvent tickEvent){
        player = Minecraft.getInstance().player;
        while(CCKeybindings.UTILITY_ABILITIES.consumeClick()) {
            if(player != null) {
                ChestCavity.printOnDebug("KeyBinding consumeClick()! Key id: " + CCKeybindings.UTILITY_ABILITIES_ID.toString());
                for(ResourceLocation i : CCKeybindings.UTILITY_ABILITY_LIST) { //Why send each one individually? why not send the resourcelocation and then run the code on the server?
                    NetworkHandler.CHANNEL.sendToServer(new ChestCavityHotkeyPacket(i));
                }
            }
        }
        while(CCKeybindings.ATTACK_ABILITIES.consumeClick()) {
            if(player != null) {
                ChestCavity.printOnDebug("KeyBinding consumeClick()! Key id: " + CCKeybindings.ATTACK_ABILITIES_ID.toString());
                for(ResourceLocation i : CCKeybindings.ATTACK_ABILITY_LIST) {
                    NetworkHandler.CHANNEL.sendToServer(new ChestCavityHotkeyPacket(i));
                }
            }
        }

        checkSpecificKey(CCKeybindings.CREEPY,CCOrganScores.CREEPY);
        checkSpecificKey(CCKeybindings.DRAGON_BREATH,CCOrganScores.DRAGON_BREATH);
        checkSpecificKey(CCKeybindings.DRAGON_BOMBS,CCOrganScores.DRAGON_BOMBS);
        checkSpecificKey(CCKeybindings.FORCEFUL_SPIT,CCOrganScores.FORCEFUL_SPIT);
        checkSpecificKey(CCKeybindings.FURNACE_POWERED,CCOrganScores.FURNACE_POWERED);
        checkSpecificKey(CCKeybindings.IRON_REPAIR,CCOrganScores.IRON_REPAIR);
        checkSpecificKey(CCKeybindings.GHASTLY,CCOrganScores.GHASTLY);
        checkSpecificKey(CCKeybindings.GRAZING,CCOrganScores.GRAZING);
        checkSpecificKey(CCKeybindings.PYROMANCY,CCOrganScores.PYROMANCY);
        checkSpecificKey(CCKeybindings.SHULKER_BULLETS,CCOrganScores.SHULKER_BULLETS);
        checkSpecificKey(CCKeybindings.SILK,CCOrganScores.SILK);
    }


    public static void checkSpecificKey(KeyBinding keybinding, ResourceLocation id){
        while(keybinding.consumeClick()) {
            if(player != null) {
                ChestCavity.printOnDebug("KeyBinding consumeClick()! Key id: " + id.toString());
                NetworkHandler.CHANNEL.sendToServer(new ChestCavityHotkeyPacket(id));
            }
        }
    }
}
