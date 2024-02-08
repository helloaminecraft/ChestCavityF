package net.tigereye.chestcavity.listeners;



import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.tigereye.chestcavity.interfaces.ChestCavityEntity;

public class OrganFoodCallback {
    public static EffectiveFoodScores addFoodListener(Item food, Food foodComponent, ChestCavityEntity cce, EffectiveFoodScores efs) {
        return OrganFoodListeners.callMethods(food, foodComponent, cce, efs);
    }
}
