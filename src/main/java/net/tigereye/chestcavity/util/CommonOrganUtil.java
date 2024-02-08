package net.tigereye.chestcavity.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.chestcavities.instance.ChestCavityInstance;
import net.tigereye.chestcavity.interfaces.CCStatusEffectInstance;
import net.tigereye.chestcavity.interfaces.ChestCavityEntity;
import net.tigereye.chestcavity.registration.CCOrganScores;
import net.tigereye.chestcavity.registration.CCStatusEffects;

import java.util.*;

public class CommonOrganUtil {

    public static void explode(LivingEntity entity, float explosionYield) {
        if (!entity.level.isClientSide) {
            Explosion.Mode destructionType = entity.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) ? Explosion.Mode.DESTROY : Explosion.Mode.NONE;
            entity.level.explode(null, entity.getX(), entity.getY(), entity.getZ(), (float)Math.sqrt(explosionYield), destructionType);
            spawnEffectsCloud(entity);
        }

    }

    public static List<EffectInstance> getStatusEffects(ItemStack organ){
        CompoundNBT tag = organ.getOrCreateTag();
        ListNBT NbtList;
        if (!tag.contains("CustomPotionEffects", 9)) {
            return new ArrayList<>();
        }
        else{
            NbtList = tag.getList("CustomPotionEffects",10);
            List<EffectInstance> list = new ArrayList<>();
            for(int i = 0; i < NbtList.size(); ++i) {
                CompoundNBT compoundNBT = NbtList.getCompound(i);
                EffectInstance statusEffectInstance = EffectInstance.load(compoundNBT);
                if (statusEffectInstance != null) {
                    list.add(statusEffectInstance);
                }
            }
            return list;
        }
    }

    public static void milkSilk(LivingEntity entity){
        if(!entity.hasEffect(CCStatusEffects.SILK_COOLDOWN.get())){
            ChestCavityEntity.of(entity).ifPresent(cce -> {
                if(cce.getChestCavityInstance().opened){
                    ChestCavityInstance cc = cce.getChestCavityInstance();
                    float silk = cc.getOrganScore(CCOrganScores.SILK);
                    if(silk > 0){
                        if(spinWeb(entity,cc,silk)) {
                            entity.addEffect(new EffectInstance(CCStatusEffects.SILK_COOLDOWN.get(), ChestCavity.config.SILK_COOLDOWN,0,false,false,true));
                        }
                    }
                }
            });
        }
    }

    public static void queueDragonBombs(LivingEntity entity, ChestCavityInstance cc, int bombs){
        if(entity instanceof PlayerEntity){
            ((PlayerEntity)entity).causeFoodExhaustion(bombs*.6f);
        }
        for(int i = 0; i < bombs;i++){
            cc.projectileQueue.add(CommonOrganUtil::spawnDragonBomb);
        }
        entity.addEffect(new EffectInstance(CCStatusEffects.DRAGON_BOMB_COOLDOWN.get(), ChestCavity.config.DRAGON_BOMB_COOLDOWN, 0, false, false, true));
    }

    public static void queueForcefulSpit(LivingEntity entity, ChestCavityInstance cc, int projectiles){
        if(entity instanceof PlayerEntity){
            ((PlayerEntity)entity).causeFoodExhaustion(projectiles*.1f);
        }
        for(int i = 0; i < projectiles;i++){
            cc.projectileQueue.add(CommonOrganUtil::spawnSpit);
        }
        entity.addEffect(new EffectInstance(CCStatusEffects.FORCEFUL_SPIT_COOLDOWN.get(), ChestCavity.config.FORCEFUL_SPIT_COOLDOWN, 0, false, false, true));
    }

    public static void queueGhastlyFireballs(LivingEntity entity, ChestCavityInstance cc, int ghastly){
        if(entity instanceof PlayerEntity){
            ((PlayerEntity)entity).causeFoodExhaustion(ghastly*.3f);
        }
        for(int i = 0; i < ghastly;i++){
            cc.projectileQueue.add(CommonOrganUtil::spawnGhastlyFireball);
        }
        entity.addEffect(new EffectInstance(CCStatusEffects.GHASTLY_COOLDOWN.get(), ChestCavity.config.GHASTLY_COOLDOWN, 0, false, false, true));
    }

    public static void queuePyromancyFireballs(LivingEntity entity, ChestCavityInstance cc, int pyromancy){
        if(entity instanceof PlayerEntity){
            ((PlayerEntity)entity).causeFoodExhaustion(pyromancy*.1f);
        }
        for(int i = 0; i < pyromancy;i++){
            cc.projectileQueue.add(CommonOrganUtil::spawnPyromancyFireball);
        }
        entity.addEffect(new EffectInstance(CCStatusEffects.PYROMANCY_COOLDOWN.get(), ChestCavity.config.PYROMANCY_COOLDOWN, 0, false, false, true));
    }

    public static void queueShulkerBullets(LivingEntity entity, ChestCavityInstance cc, int shulkerBullets){
        if(entity instanceof PlayerEntity){
            ((PlayerEntity)entity).causeFoodExhaustion(shulkerBullets*.3f);
        }
        for(int i = 0; i < shulkerBullets;i++){
            cc.projectileQueue.add(CommonOrganUtil::spawnShulkerBullet);
        }
        entity.addEffect(new EffectInstance(CCStatusEffects.SHULKER_BULLET_COOLDOWN.get(), ChestCavity.config.SHULKER_BULLET_COOLDOWN, 0, false, false, true));
    }

    public static void setStatusEffects(ItemStack organ, ItemStack potion){
        List<EffectInstance> potionList = PotionUtils.getMobEffects(potion);
        List<EffectInstance> list = new ArrayList<>();
        for (EffectInstance effect : potionList) {
            EffectInstance effectCopy = new EffectInstance(effect);
            ((CCStatusEffectInstance) effectCopy).CC_setDuration(Math.max(1,effectCopy.getDuration()/4));
            list.add(effectCopy);
        }
        setStatusEffects(organ,list);
    }

    public static void setStatusEffects(ItemStack organ, List<EffectInstance> list){
        CompoundNBT tag = organ.getOrCreateTag();
        ListNBT NbtList = new ListNBT();

        for(int i = 0; i < list.size(); ++i) {
            EffectInstance effect = list.get(i);
            if (effect != null) {
                CompoundNBT compoundNBT = new CompoundNBT();
                NbtList.add(effect.save(compoundNBT));
            }
        }
        tag.put("CustomPotionEffects",NbtList);
    }

    public static void shearSilk(LivingEntity entity){
        ChestCavityEntity.of(entity).ifPresent(cce -> {
            if(cce.getChestCavityInstance().opened){
                float silk = cce.getChestCavityInstance().getOrganScore(CCOrganScores.SILK);

                if(silk > 0){
                    if(silk >= 2){
                        ItemStack stack = new ItemStack(Items.COBWEB,((int)silk)/2);
                        ItemEntity itemEntity = new ItemEntity(entity.level, entity.getX(), entity.getY(), entity.getZ(), stack);
                        entity.level.addFreshEntity(itemEntity);
                    }
                    if(silk % 2 >= 1){
                        ItemStack stack = new ItemStack(Items.STRING);
                        ItemEntity itemEntity = new ItemEntity(entity.level, entity.getX(), entity.getY(), entity.getZ(), stack);
                        entity.level.addFreshEntity(itemEntity);
                    }
                }
            }
        });
    }

    public static void spawnEffectsCloud(LivingEntity entity) {
        Collection<EffectInstance> collection = entity.getActiveEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(entity.level, entity.getX(), entity.getY(), entity.getZ());
            areaEffectCloudEntity.setRadius(2.5F);
            areaEffectCloudEntity.setRadiusOnUse(-0.5F);
            areaEffectCloudEntity.setWaitTime(10);
            areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 2);
            areaEffectCloudEntity.setRadiusPerTick(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
            Iterator<EffectInstance> var3 = collection.iterator();

            while(var3.hasNext()) {
                EffectInstance statusEffectInstance = var3.next();
                areaEffectCloudEntity.addEffect(new EffectInstance(statusEffectInstance));
            }

            entity.level.addFreshEntity(areaEffectCloudEntity);
        }

    }

    public static void spawnSilk(LivingEntity entity){
        entity.spawnAtLocation(Items.STRING);
    }

    public static void spawnSpit(LivingEntity entity){
        Vector3d entityFacing = entity.getLookAngle().normalize();

        LlamaEntity fakeLlama = new LlamaEntity(EntityType.LLAMA,entity.level);
        fakeLlama.setPos(entity.getX(),entity.getY(),entity.getZ());
        fakeLlama.xRot = entity.xRot;
        fakeLlama.yRot = entity.yRot;
        fakeLlama.yBodyRot = entity.yBodyRot;
        LlamaSpitEntity llamaSpitEntity = new LlamaSpitEntity(entity.level, fakeLlama);
        llamaSpitEntity.setOwner(entity);
        llamaSpitEntity.setDeltaMovement(entityFacing.x*2,entityFacing.y*2,entityFacing.z*2);
        entity.level.addFreshEntity(llamaSpitEntity);
        entityFacing = entityFacing.scale(-.1D);
        entity.push(entityFacing.x,entityFacing.y,entityFacing.z);
    }

    public static void spawnDragonBomb(LivingEntity entity){
        Vector3d entityFacing = entity.getLookAngle().normalize();
        DragonFireballEntity fireballEntity = new DragonFireballEntity(entity.level, entity, entityFacing.x, entityFacing.y, entityFacing.z);
        fireballEntity.absMoveTo(fireballEntity.getX(), entity.getX(0.5D) + 0.3D, fireballEntity.getZ());
        entity.level.addFreshEntity(fireballEntity);
        entityFacing = entityFacing.scale(-0.2D);
        entity.push(entityFacing.x,entityFacing.y,entityFacing.z);
    }

    public static void spawnDragonBreath(LivingEntity entity){
        Optional<ChestCavityEntity> optional = ChestCavityEntity.of(entity);
        if(!optional.isPresent()){
            return;
        }
        ChestCavityEntity cce = optional.get();
        ChestCavityInstance cc= cce.getChestCavityInstance();
        float breath = cc.getOrganScore(CCOrganScores.DRAGON_BREATH);
        double range = Math.sqrt(breath/2)*5;
        RayTraceResult result = entity.pick(range, 0, false);
        Vector3d pos = result.getLocation();
        double x = pos.x;
        double y = pos.y;
        double z = pos.z;
        BlockPos.Mutable mutable = new BlockPos.Mutable(x,y,z);
        while(entity.level.isEmptyBlock(mutable)) {
            --y;
            if (y < 0.0D) {
                return;
            }

            mutable.set(x,y,z);
        }
        y = (Math.floor(y) + 1);
        AreaEffectCloudEntity breathEntity = new AreaEffectCloudEntity(entity.level, x, y, z);
        breathEntity.setOwner(entity);
        breathEntity.setRadius((float)Math.max(range/2,Math.min(range, MathUtil.horizontalDistanceTo(breathEntity,entity))));
        breathEntity.setDuration(200);
        breathEntity.setParticle(ParticleTypes.DRAGON_BREATH);
        breathEntity.addEffect(new EffectInstance(Effects.HARM));
        entity.level.addFreshEntity(breathEntity);

    }

    public static void spawnGhastlyFireball(LivingEntity entity){
        Vector3d entityFacing = entity.getLookAngle().normalize();
        FireballEntity fireballEntity = new FireballEntity(entity.level, entity, entityFacing.x, entityFacing.y, entityFacing.z);
        fireballEntity.absMoveTo(fireballEntity.getX(), entity.getY(0.5D) + 0.3D, fireballEntity.getZ());
        entity.level.addFreshEntity(fireballEntity);
        entityFacing = entityFacing.scale(-.8D);
        entity.push(entityFacing.x,entityFacing.y,entityFacing.z);
    }

    public static void spawnPyromancyFireball(LivingEntity entity){
        Vector3d entityFacing = entity.getLookAngle().normalize();
        SmallFireballEntity smallFireballEntity = new SmallFireballEntity(entity.level, entity, entityFacing.x + entity.getRandom().nextGaussian() * .1, entityFacing.y, entityFacing.z + entity.getRandom().nextGaussian() * .1);
        smallFireballEntity.absMoveTo(smallFireballEntity.getX(), entity.getY(0.5D) + 0.3D, smallFireballEntity.getZ());
        entity.level.addFreshEntity(smallFireballEntity);
        entityFacing = entityFacing.scale(-.2D);
        entity.push(entityFacing.x,entityFacing.y,entityFacing.z);
    }

    public static void spawnShulkerBullet(LivingEntity entity){
        //Vector3dd entityFacing = entity.getRotationVector().normalize();
        EntityPredicate targetPredicate = new EntityPredicate();
        targetPredicate.range(ChestCavity.config.SHULKER_BULLET_TARGETING_RANGE*2);
        LivingEntity target = entity.level.getNearestEntity(LivingEntity.class,
                targetPredicate, entity, entity.getX(), entity.getY(),entity.getZ(),
                new AxisAlignedBB(entity.getX()-ChestCavity.config.SHULKER_BULLET_TARGETING_RANGE,entity.getY()-ChestCavity.config.SHULKER_BULLET_TARGETING_RANGE,entity.getZ()-ChestCavity.config.SHULKER_BULLET_TARGETING_RANGE,
                        entity.getX()+ChestCavity.config.SHULKER_BULLET_TARGETING_RANGE,entity.getY()+ChestCavity.config.SHULKER_BULLET_TARGETING_RANGE,entity.getZ()+ChestCavity.config.SHULKER_BULLET_TARGETING_RANGE));
        if(target == null){
            return;
        }
        ShulkerBulletEntity shulkerBulletEntity = new ShulkerBulletEntity(entity.level,entity,target, Direction.Axis.Y);
        shulkerBulletEntity.absMoveTo(shulkerBulletEntity.getX(), entity.getY(0.5D) + 0.3D, shulkerBulletEntity.getZ());
        entity.level.addFreshEntity(shulkerBulletEntity);
        //entityFacing = entityFacing.multiply(-.4D);
        //entity.addVelocity(entityFacing.x,entityFacing.y,entityFacing.z);
    }

    public static boolean spinWeb(LivingEntity entity, ChestCavityInstance cc, float silkScore){
        int hungerCost = 0;
        PlayerEntity player = null;
        if(entity instanceof PlayerEntity){
            player = (PlayerEntity)entity;
            if(player.getFoodData().getFoodLevel() < 6){
                return false;
            }
        }

        if(silkScore >= 2) {
            BlockPos pos = ((CCMixinThing) (Object) entity).getMixinBlockPos().relative(entity.getDirection().getOpposite());
            if(entity.level.getBlockState(pos).isAir()){
                if(silkScore >= 3) {
                    hungerCost = 16;
                    silkScore -= 3;
                    entity.level.setBlock(pos, Blocks.WHITE_WOOL.defaultBlockState(), 2);
                }
                else{
                    hungerCost = 8;
                    silkScore -= 2;
                    entity.level.setBlock(pos, Blocks.COBWEB.defaultBlockState(), 2);
                }
            }
        }
        while(silkScore >= 1) {
            silkScore--;
            hungerCost += 4;
            cc.projectileQueue.add(CommonOrganUtil::spawnSilk);
        }
        if(player != null){
            player.getFoodData().addExhaustion(hungerCost);
        }
        return hungerCost > 0;
    }

    public static boolean teleportRandomly(LivingEntity entity, float range) {
        if (!entity.level.isClientSide() && entity.isAlive()) {
            for(int i = 0; i < ChestCavity.config.MAX_TELEPORT_ATTEMPTS; i++) {
                double d = entity.getX() + ((entity.getRandom().nextDouble() - 0.5D) * range);
                double e = Math.max(1, entity.getY() + ((entity.getRandom().nextDouble() - 0.5D) * range));
                double f = entity.getZ() + ((entity.getRandom().nextDouble() - 0.5D) * range);
                if(teleportTo(entity, d, e, f)){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean teleportTo(LivingEntity entity, double x, double y, double z) {
        if(entity.isPassenger()){
            entity.stopRiding();
        }
        BlockPos.Mutable targetPos = new BlockPos.Mutable(x, y, z);
        BlockState blockState = entity.level.getBlockState(targetPos);
        while(targetPos.getY() > 0 && !(blockState.getMaterial().blocksMotion()
                || blockState.getMaterial().isLiquid()))
        {
            targetPos.move(Direction.DOWN);
            blockState = entity.level.getBlockState(targetPos);
        }
        if(targetPos.getY() <= 0){
            return false;
        }

        targetPos.move(Direction.UP);
        blockState = entity.level.getBlockState(targetPos);
        BlockState blockState2 = entity.level.getBlockState(targetPos.above());
        while(blockState.getMaterial().blocksMotion()
                || blockState.getMaterial().isLiquid()
                || blockState2.getMaterial().blocksMotion()
                || blockState2.getMaterial().isLiquid()){
            targetPos.move(Direction.UP);
            blockState = entity.level.getBlockState(targetPos);
            blockState2 = entity.level.getBlockState(targetPos.above());
        }

        if(entity.level.dimensionType().hasCeiling() && targetPos.getY() >= entity.level.getHeight()){
            return false;
        }
        entity.teleportTo(x, targetPos.getY()+.1, z);
        if (!entity.isSilent()) {
            entity.level.playSound(null, entity.xOld, entity.yOld, entity.zOld, SoundEvents.ENDERMAN_TELEPORT, entity.getSoundSource(), 1.0F, 1.0F);
            entity.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
        }

        return true;
    }
}
