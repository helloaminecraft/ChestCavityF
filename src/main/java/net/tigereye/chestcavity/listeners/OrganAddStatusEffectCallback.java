package net.tigereye.chestcavity.listeners;



import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.tigereye.chestcavity.chestcavities.instance.ChestCavityInstance;
import net.tigereye.chestcavity.interfaces.ChestCavityEntity;

import java.util.Optional;

public class OrganAddStatusEffectCallback {

    public static EffectInstance organAddMobEffect(LivingEntity entity, EffectInstance mobEffectInstance) {
        Optional<ChestCavityEntity> optional = ChestCavityEntity.of(entity);
        if(optional.isPresent()) {
            ChestCavityEntity chestCavityEntity = optional.get();
            ChestCavityInstance chestCavityInstance = chestCavityEntity.getChestCavityInstance();
            return OrganAddStatusEffectListeners.callMethods(entity, chestCavityInstance, mobEffectInstance);
        }
        return mobEffectInstance;
    }
}
