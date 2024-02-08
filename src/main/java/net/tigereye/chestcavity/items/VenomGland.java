package net.tigereye.chestcavity.items;


import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.chestcavities.instance.ChestCavityInstance;
import net.tigereye.chestcavity.listeners.OrganOnHitListener;
import net.tigereye.chestcavity.registration.CCFoodComponents;
import net.tigereye.chestcavity.registration.CCStatusEffects;
import net.tigereye.chestcavity.util.CommonOrganUtil;

import java.util.List;

public class VenomGland extends Item implements OrganOnHitListener {

    public VenomGland() {
        super(new Properties().stacksTo(1).tab(ChestCavity.ORGAN_ITEM_GROUP).food(CCFoodComponents.RAW_TOXIC_ORGAN_MEAT_FOOD_COMPONENT));
    }

    @Override
    public float onHit(DamageSource source, LivingEntity attacker, LivingEntity target, ChestCavityInstance cc, ItemStack organ, float damage) {
        if(attacker.getItemInHand(attacker.getUsedItemHand()).isEmpty()
        //venom glands don't trigger from projectiles... unless it is llama spit. Because I find that hilarious.
        ||(source instanceof IndirectEntityDamageSource && !(((IndirectEntityDamageSource)source).getEntity() instanceof LlamaSpitEntity))){
            if(source instanceof IndirectEntityDamageSource &&
                    !(((IndirectEntityDamageSource)source).getEntity() instanceof LlamaSpitEntity)){
                return damage;
            }
            //venom glands don't trigger if they are on cooldown,
            //unless that cooldown was applied this same tick
            if(attacker.hasEffect(CCStatusEffects.VENOM_COOLDOWN.get())){
                EffectInstance cooldown = attacker.getEffect(CCStatusEffects.VENOM_COOLDOWN.get());
                //this is to check if the cooldown was inflicted this same tick; likely because of other venom glands
                if(cooldown.getDuration() != ChestCavity.config.VENOM_COOLDOWN){
                    return damage;
                }
            }
            //failure conditions passed, the venom gland now delivers its payload
            List<EffectInstance> effects = CommonOrganUtil.getStatusEffects(organ);
            if(!effects.isEmpty()){
                for(EffectInstance effect : effects){
                    target.addEffect(effect);
                }
            }
            else {
                target.addEffect(new EffectInstance(Effects.POISON, 200, 0));
            }
            attacker.addEffect(new EffectInstance(CCStatusEffects.VENOM_COOLDOWN.get(), ChestCavity.config.VENOM_COOLDOWN, 0));
            if(attacker instanceof PlayerEntity){
                ((PlayerEntity)attacker).causeFoodExhaustion(.1f);
            }
        }
        return damage;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, World world, List<ITextComponent> tooltip, ITooltipFlag tooltipContext) {
        super.appendHoverText(itemStack,world,tooltip,tooltipContext);
        if(!CommonOrganUtil.getStatusEffects(itemStack).isEmpty()) {
            PotionUtils.addPotionTooltip(itemStack, tooltip, 1);
        }
    }


}
