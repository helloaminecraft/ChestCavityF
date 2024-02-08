package net.tigereye.chestcavity.listeners;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.chestcavities.instance.ChestCavityInstance;
import net.tigereye.chestcavity.registration.CCDamageSource;
import net.tigereye.chestcavity.registration.CCOrganScores;
import net.tigereye.chestcavity.registration.CCStatusEffects;
import net.tigereye.chestcavity.util.CCMixinThing;
import net.tigereye.chestcavity.util.CommonOrganUtil;

import java.util.List;

public class OrganTickListeners {

    public static void callMethods(LivingEntity entity, ChestCavityInstance cc){
        TickIncompatibility(entity, cc);
        TickProjectileQueue(entity, cc);

        TickHealth(entity, cc);
        TickFiltration(entity, cc);

        TickBuoyant(entity, cc);
        TickCrystalsynthesis(entity, cc);
        TickPhotosynthesis(entity, cc);
        TickHydroallergenic(entity, cc);
        TickHydrophobia(entity, cc);
        TickGlowing(entity, cc);
    }

    public static void TickBuoyant(LivingEntity entity, ChestCavityInstance chestCavity){
        if(entity.isOnGround() || entity.isNoGravity()){
            return;
        }
        float buoyancy = chestCavity.getOrganScore(CCOrganScores.BUOYANT) - chestCavity.getChestCavityType().getDefaultOrganScore(CCOrganScores.BUOYANT);
        if(buoyancy != 0)
        {
            entity.push(0.0D, buoyancy*0.02D, 0.0D);
        }
    }

    public static void TickCrystalsynthesis(LivingEntity entity, ChestCavityInstance cc){
        float crystalsynthesis = cc.getOrganScore(CCOrganScores.CRYSTALSYNTHESIS);
        //if the old crystal had been exploded, suffer
        if (cc.connectedCrystal != null) {
            if(cc.connectedCrystal.isPickable()) {
                entity.hurt(DamageSource.STARVE, crystalsynthesis * 2);
                cc.connectedCrystal = null;
            }
            else{
                if(crystalsynthesis != 0){
                    cc.connectedCrystal.setBeamTarget(((CCMixinThing) (Object) entity).getMixinBlockPos().below(2));
                }
                else{
                    cc.connectedCrystal.setBeamTarget(null);
                    cc.connectedCrystal = null;
                }
            }
        }
        if(crystalsynthesis != 0 && entity.level.getDayTime() % ChestCavity.config.CRYSTALSYNTHESIS_FREQUENCY == 0 && !(entity instanceof EnderDragonEntity))
        {
            EnderCrystalEntity oldcrystal = cc.connectedCrystal;
            //attempt to bind to a crystal
            List<EnderCrystalEntity> list = entity.level.getEntitiesOfClass(EnderCrystalEntity.class, entity.getBoundingBox().inflate(ChestCavity.config.CRYSTALSYNTHESIS_RANGE));
            EnderCrystalEntity endCrystalEntity = null;
            double d = Double.MAX_VALUE;

            for (EnderCrystalEntity endCrystalEntity2 : list) {
                double e = endCrystalEntity2.distanceToSqr(entity);
                if (e < d) {
                    d = e;
                    endCrystalEntity = endCrystalEntity2;
                }
            }
            cc.connectedCrystal = endCrystalEntity;
            if(oldcrystal != null && oldcrystal != cc.connectedCrystal){
                oldcrystal.setBeamTarget(null);
            }
            //then, if a crystal has been bound to
            if (cc.connectedCrystal != null) {
                if(entity instanceof PlayerEntity){
                    PlayerEntity playerEntity = (PlayerEntity) entity;
                    FoodStats hungerManager = playerEntity.getFoodData();
                    //first restore hunger
                    if(hungerManager.needsFood()) {
                        if(crystalsynthesis >= 5
                                || entity.level.getDayTime() % (ChestCavity.config.CRYSTALSYNTHESIS_FREQUENCY *5) < (ChestCavity.config.CRYSTALSYNTHESIS_FREQUENCY *crystalsynthesis)) {
                            hungerManager.eat(1, 0f);
                        }
                    }
                    //then restore saturation
                    else if(hungerManager.getSaturationLevel() < hungerManager.getFoodLevel()){
                        hungerManager.eat(1, crystalsynthesis/10);
                    }
                    //then restore health
                    else {
                        playerEntity.heal(crystalsynthesis / 5);
                    }
                }
                else{
                    //just restore health...
                    entity.heal(crystalsynthesis/5);
                }
                //finally, something about shiny lines linking to crystals?
            }

        }
    }

    public static void TickPhotosynthesis(LivingEntity entity, ChestCavityInstance cc){
        if(entity.level.isClientSide()){
            return;
        }
        float photosynthesis = cc.getOrganScore(CCOrganScores.PHOTOSYNTHESIS) - cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.PHOTOSYNTHESIS);
        if(photosynthesis > 0){
            cc.photosynthesisProgress += photosynthesis*entity.level.getLightEmission(((CCMixinThing) (Object) entity).getMixinBlockPos());
            if(cc.photosynthesisProgress > ChestCavity.config.PHOTOSYNTHESIS_FREQUENCY*8*15){
                cc.photosynthesisProgress = 0;
                if(entity instanceof PlayerEntity){
                    PlayerEntity playerEntity = (PlayerEntity) entity;
                    FoodStats hungerManager = playerEntity.getFoodData();
                    //first restore hunger
                    if(hungerManager.needsFood()) {
                        hungerManager.eat(1,0);
                    }
                    //then restore saturation
                    else if(hungerManager.getSaturationLevel() < hungerManager.getFoodLevel()){
                        hungerManager.eat(1, .5f);
                    }
                    //then restore health
                    else {
                        playerEntity.heal(1);
                    }
                }
                else{
                    //just restore health
                    entity.heal(1);
                }
            }
        }
    }

    public static void TickHealth(LivingEntity entity, ChestCavityInstance cc){
        if (cc.getOrganScore(CCOrganScores.HEALTH) <= 0
                && cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.HEALTH) != 0)
        {
            if(entity.level.getDayTime() % ChestCavity.config.HEARTBLEED_RATE == 0) {
                cc.heartBleedTimer = cc.heartBleedTimer + 1;
                entity.hurt(CCDamageSource.HEARTBLEED, Math.min(cc.heartBleedTimer,cc.getChestCavityType().getHeartBleedCap()));
            }
        }
        else{
            cc.heartBleedTimer = 0;
        }
    }

    public static void TickFiltration(LivingEntity entity, ChestCavityInstance cc){
        if(entity.level.isClientSide()){ //this is a server-side event
            return;
        }
        if(cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.FILTRATION) <= 0){ //don't bother if the target doesn't need kidneys
            return;
        }
        float KidneyRatio = cc.getOrganScore(CCOrganScores.FILTRATION)/cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.FILTRATION);
        if(KidneyRatio < 1)
        {
            cc.bloodPoisonTimer = cc.bloodPoisonTimer+1;
            if(cc.bloodPoisonTimer >= ChestCavity.config.KIDNEY_RATE){
                entity.addEffect(new EffectInstance(Effects.POISON, (int)Math.max(1,48*(1-KidneyRatio))));
                cc.bloodPoisonTimer = 0;
            }
        }
    }

    private static void TickProjectileQueue(LivingEntity entity, ChestCavityInstance cc) {
        //if(entity.world.isClient){
            //this is spawning entities, this is no place for a client
            //return;
        //}
        if(cc.projectileCooldown > 0){
            cc.projectileCooldown -= 1;
            return;
        }
        if(!cc.projectileQueue.isEmpty()) {
            cc.projectileCooldown = 5;
            cc.projectileQueue.pop().accept(entity);
        }
    }

    private static void TickHydroallergenic(LivingEntity entity, ChestCavityInstance cc) {
        if(entity.level.isClientSide()){ //this is a server-side event
            return;
        }
        float Hydroallergy = cc.getOrganScore(CCOrganScores.HYDROALLERGENIC);
        if(Hydroallergy <= 0){   //do nothing if the target isn't hydrophobic
            return;                                                                 //TODO: make enderman water-damage dependent on hydroallergenic
        }
        if (entity.isInWater()) {
            if(!entity.hasEffect(CCStatusEffects.WATER_VULNERABILITY.get())) {
                entity.hurt(DamageSource.MAGIC, 10);
                entity.addEffect(new EffectInstance(CCStatusEffects.WATER_VULNERABILITY.get(), (int) (260 / Hydroallergy), 0, false, false, true));
            }
        }
        else if (entity.isInWaterOrRain()) {
            if(!entity.hasEffect(CCStatusEffects.WATER_VULNERABILITY.get())) {
                entity.hurt(DamageSource.MAGIC, 1);
                entity.addEffect(new EffectInstance(CCStatusEffects.WATER_VULNERABILITY.get(), (int) (260 / Hydroallergy), 0, false, false, true));
            }
        }
    }

    public static void TickHydrophobia(LivingEntity entity, ChestCavityInstance cc){
        float hydrophobia = cc.getOrganScore(CCOrganScores.HYDROPHOBIA);
        if(hydrophobia <= 0                                                         //do nothing if the target isn't hydrophobic
            || cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.HYDROPHOBIA) != 0){   //do nothing if they are by default, otherwise endermen will spaz even harder
            return;                                                                 //TODO: make enderman water-teleporting dependent on hydrophobia
        }
        if(entity.isInWaterOrRain()){
            CommonOrganUtil.teleportRandomly(entity,hydrophobia*32);
        }
    }

    public static void TickIncompatibility(LivingEntity entity,ChestCavityInstance chestCavity){
        if(entity.level.isClientSide() || ChestCavity.config.DISABLE_ORGAN_REJECTION){ //this is a server-side event
            return;
        }
        float incompatibility = chestCavity.getOrganScore(CCOrganScores.INCOMPATIBILITY);
        if(incompatibility > 0)
        {
            if(!entity.hasEffect(CCStatusEffects.ORGAN_REJECTION.get())){
                entity.addEffect(new EffectInstance(CCStatusEffects.ORGAN_REJECTION.get(), (int)(ChestCavity.config.ORGAN_REJECTION_RATE /incompatibility),0, false, true, true));
            }
        }
    }

    public static void TickGlowing(LivingEntity entity,ChestCavityInstance chestCavity){
        if(entity.level.isClientSide()){ //this is a server-side event
            return;
        }
        float glowing = chestCavity.getOrganScore(CCOrganScores.GLOWING);
        if(glowing > 0)
        {
            if(!entity.hasEffect(Effects.GLOWING)){
                entity.addEffect(new EffectInstance(Effects.GLOWING, 200,0, false, true, true));
            }
        }
    }
}