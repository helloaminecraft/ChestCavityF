package net.tigereye.chestcavity.listeners;



import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.tigereye.chestcavity.chestcavities.instance.ChestCavityInstance;

public interface OrganOnHitListener {
    float onHit(DamageSource source, LivingEntity attacker, LivingEntity target, ChestCavityInstance chestCavity, ItemStack organ, float damage);
}
