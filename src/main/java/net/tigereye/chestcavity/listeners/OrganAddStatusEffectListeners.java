package net.tigereye.chestcavity.listeners;


import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.chestcavities.instance.ChestCavityInstance;
import net.tigereye.chestcavity.interfaces.CCStatusEffect;
import net.tigereye.chestcavity.interfaces.CCStatusEffectInstance;
import net.tigereye.chestcavity.registration.CCOrganScores;

public class OrganAddStatusEffectListeners {

    public static EffectInstance callMethods(LivingEntity livingEntity, ChestCavityInstance chestCavityInstance, EffectInstance mobEffectInstance){
        ApplyBuffPurging(livingEntity, chestCavityInstance, mobEffectInstance);
        ApplyDetoxification(livingEntity, chestCavityInstance, mobEffectInstance);
        ApplyWithered(livingEntity, chestCavityInstance, mobEffectInstance);
        return mobEffectInstance;
    }

    private static EffectInstance ApplyBuffPurging(LivingEntity entity, ChestCavityInstance cc, EffectInstance instance) {
        if(cc.getOrganScore(CCOrganScores.BUFF_PURGING) > 0
                && ((CCStatusEffect)(instance.getEffect())).CC_IsBeneficial())
        {
            CCStatusEffectInstance ccInstance = (CCStatusEffectInstance) instance;
            ccInstance.CC_setDuration((int)(instance.getDuration()/
                    (1+(ChestCavity.config.BUFF_PURGING_DURATION_FACTOR*cc.getOrganScore(CCOrganScores.BUFF_PURGING)))));
        }
        return instance;
    }

    private static EffectInstance ApplyDetoxification(LivingEntity entity, ChestCavityInstance cc, EffectInstance instance) {
        if(cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.DETOXIFICATION) <= 0
        || cc.getOrganScore(CCOrganScores.DETOXIFICATION) == cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.DETOXIFICATION))
        {
            return instance;
        }
        CCStatusEffect ccStatusEffect = (CCStatusEffect)instance.getEffect();
        if(ccStatusEffect.CC_IsHarmful()){
            CCStatusEffectInstance ccInstance = (CCStatusEffectInstance) instance;
            float detoxRatio = cc.getOrganScore(CCOrganScores.DETOXIFICATION)/cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.DETOXIFICATION);
            ccInstance.CC_setDuration((int) Math.max(1,instance.getDuration() * 2 / (1 + detoxRatio)));
        }
        return instance;
    }

    private static EffectInstance ApplyFiltration(LivingEntity entity, ChestCavityInstance cc, EffectInstance instance) {
        float filtrationDiff = cc.getOrganScore(CCOrganScores.FILTRATION) - cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.FILTRATION);
        if(filtrationDiff > 0
                && instance.getEffect() == Effects.POISON)
        {
            CCStatusEffectInstance ccInstance = (CCStatusEffectInstance) instance;
            ccInstance.CC_setDuration((int)(instance.getDuration()/
                    (1+(ChestCavity.config.FILTRATION_DURATION_FACTOR*cc.getOrganScore(CCOrganScores.FILTRATION)))));
        }
        return instance;
    }

    private static EffectInstance ApplyWithered(LivingEntity entity, ChestCavityInstance cc, EffectInstance instance) {
        if(cc.getOrganScore(CCOrganScores.WITHERED) > 0
                && instance.getEffect() == Effects.WITHER)
        {
            CCStatusEffectInstance ccInstance = (CCStatusEffectInstance) instance;
            ccInstance.CC_setDuration((int)(instance.getDuration()/
                    (1+(ChestCavity.config.WITHERED_DURATION_FACTOR*cc.getOrganScore(CCOrganScores.WITHERED)))));
        }
        return instance;
    }
}
