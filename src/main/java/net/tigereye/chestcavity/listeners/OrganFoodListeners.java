package net.tigereye.chestcavity.listeners;


import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.tigereye.chestcavity.interfaces.ChestCavityEntity;
import net.tigereye.chestcavity.registration.CCItems;
import net.tigereye.chestcavity.registration.CCOrganScores;
import net.tigereye.chestcavity.registration.CCStatusEffects;
import net.tigereye.chestcavity.registration.CCTags;

public class OrganFoodListeners {

    public static EffectiveFoodScores callMethods(Item food, Food foodComponent, ChestCavityEntity cce, EffectiveFoodScores efs){
        applyHerbivorousCarnivorous(food, foodComponent, cce, efs);
        applyRot(food, foodComponent, cce, efs);
        applyFurnacePower(food, foodComponent, cce, efs);

        return efs;
   }

    private static EffectiveFoodScores applyHerbivorousCarnivorous(Item food, Food foodComponent, ChestCavityEntity cce, EffectiveFoodScores efs) {
        if(foodComponent.isMeat() || food.getDefaultInstance().getItem().is(CCTags.CARNIVORE_FOOD)){
            efs.digestion += cce.getChestCavityInstance().getOrganScore(CCOrganScores.CARNIVOROUS_DIGESTION);
            efs.nutrition += cce.getChestCavityInstance().getOrganScore(CCOrganScores.CARNIVOROUS_NUTRITION);
        }
        else{
            efs.digestion += cce.getChestCavityInstance().getOrganScore(CCOrganScores.HERBIVOROUS_DIGESTION);
            efs.nutrition += cce.getChestCavityInstance().getOrganScore(CCOrganScores.HERBIVOROUS_NUTRITION);
        }
        return efs;
    }

    private static EffectiveFoodScores applyRot(Item food, Food foodComponent, ChestCavityEntity cce, EffectiveFoodScores efs) {
        if(food.getDefaultInstance().getItem().is(CCTags.ROTTEN_FOOD)){
            efs.digestion += cce.getChestCavityInstance().getOrganScore(CCOrganScores.ROT_DIGESTION);
            efs.nutrition += cce.getChestCavityInstance().getOrganScore(CCOrganScores.ROTGUT);
        }
        return efs;
    }

    private static EffectiveFoodScores applyFurnacePower(Item food, Food foodComponent, ChestCavityEntity cce, EffectiveFoodScores efs) {
        if(food == CCItems.FURNACE_POWER.get()){
            int power = 0;
            if(cce.getChestCavityInstance().owner.hasEffect(CCStatusEffects.FURNACE_POWER.get())){
                power = cce.getChestCavityInstance().owner.getEffect(CCStatusEffects.FURNACE_POWER.get()).getAmplifier() + 1;
            }
            //herbivorous will have gotten a false positive, so that needs corrected
            efs.digestion -= cce.getChestCavityInstance().getOrganScore(CCOrganScores.HERBIVOROUS_DIGESTION);
            efs.nutrition -= cce.getChestCavityInstance().getOrganScore(CCOrganScores.HERBIVOROUS_NUTRITION);
            //nutrition scales with furnaces
            efs.nutrition += power;
        }
        return efs;
    }
}
