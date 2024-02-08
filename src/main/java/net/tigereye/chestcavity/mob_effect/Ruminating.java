package net.tigereye.chestcavity.mob_effect;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectType;
import net.minecraft.util.FoodStats;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.registration.CCItems;

public class Ruminating extends CCStatusEffect{

    public Ruminating(){
        super(EffectType.BENEFICIAL, 0xC8FF00);
    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % ChestCavity.config.RUMINATION_TIME == 1;
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if(entity instanceof PlayerEntity){
            if(!(entity.level.isClientSide)) {
                FoodStats hungerManager = ((PlayerEntity) entity).getFoodData();
                hungerManager.eat(CCItems.CUD.get(), new ItemStack(CCItems.CUD.get()));
            }
        }
    }
}
