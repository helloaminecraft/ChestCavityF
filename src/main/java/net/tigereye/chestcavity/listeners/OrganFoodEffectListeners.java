package net.tigereye.chestcavity.listeners;

public class OrganFoodEffectListeners {

    //COMMENTED BY BOONELDAN

    /*
    public static void callMethods(List<Pair<MobEffectInstance, Float>> list, ItemStack itemStack, Level world, LivingEntity entity, ChestCavityInstance cc){
        applyRotgut(list, itemStack, world, entity, cc);
    }

    private static List<Pair<MobEffectInstance, Float>> applyRotgut(List<Pair<MobEffectInstance, Float>> list, ItemStack itemStack, Level world, LivingEntity entity, ChestCavityInstance cc) {
        float rotten = cc.getOrganScore(CCOrganScores.ROTGUT)+cc.getOrganScore(CCOrganScores.ROT_DIGESTION);
        if(rotten > 0){
            if(itemStack.is(CCTags.ROTTEN_FOOD)) {
                list.removeIf(pair -> pair.getFirst().getEffect() == MobEffects.HUNGER);
            }
        }
        return list;
    }

     */
}
