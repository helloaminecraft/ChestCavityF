package net.tigereye.chestcavity.listeners;


import net.minecraft.entity.LivingEntity;
import net.tigereye.chestcavity.chestcavities.instance.ChestCavityInstance;

public class OrganTickCallback {
    public static void organTick(LivingEntity entity, ChestCavityInstance cc) {
        OrganTickListeners.callMethods(entity, cc);
    }
}
