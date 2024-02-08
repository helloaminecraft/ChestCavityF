package net.tigereye.chestcavity.mob_effect;



import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.registration.CCDamageSource;

public class OrganRejection extends CCStatusEffect {

    public OrganRejection(){
        super(EffectType.NEUTRAL, 0xC8FF00);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration <= 1;
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if(!(entity.level.isClientSide)){
            entity.hurt(CCDamageSource.ORGAN_REJECTION, ChestCavity.config.ORGAN_REJECTION_DAMAGE);
        }
    }
}
