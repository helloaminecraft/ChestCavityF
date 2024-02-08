package net.tigereye.chestcavity.listeners;

import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import net.tigereye.chestcavity.chestcavities.instance.ChestCavityInstance;

import java.util.List;

public class OrganFoodEffectCallback {
    //COMMENTED BY BOONELDAN
    public static void addFoodEffects(List<Pair<EffectInstance, Float>> list, ItemStack itemStack, World world, LivingEntity entity, ChestCavityInstance cc) {
        //OrganFoodEffectListeners.callMethods(list, itemStack, world, entity, cc);
    }
}

