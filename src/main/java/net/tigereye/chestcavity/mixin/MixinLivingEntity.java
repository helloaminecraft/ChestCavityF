package net.tigereye.chestcavity.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.chestcavities.instance.ChestCavityInstance;
import net.tigereye.chestcavity.chestcavities.instance.ChestCavityInstanceFactory;
import net.tigereye.chestcavity.interfaces.ChestCavityEntity;
import net.tigereye.chestcavity.items.ChestOpener;
import net.tigereye.chestcavity.listeners.LootRegister;
import net.tigereye.chestcavity.registration.CCItems;
import net.tigereye.chestcavity.registration.CCOrganScores;
import net.tigereye.chestcavity.util.ChestCavityUtil;
import net.tigereye.chestcavity.util.CommonOrganUtil;
import net.tigereye.chestcavity.util.NetworkUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Mixin(value = LivingEntity.class,priority = 900)
public class MixinLivingEntity extends Entity implements ChestCavityEntity {
    private ChestCavityInstance chestCavityInstance;

    protected MixinLivingEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    public void chestCavityLivingEntityConstructorMixin(EntityType<? extends LivingEntity> entityType, World world, CallbackInfo info){
        chestCavityInstance = ChestCavityInstanceFactory.newChestCavityInstance(entityType,(LivingEntity)(Object)this);
        ChestCavity.printOnDebug("CHESTCAVITY LIVINGENTITY MIXIN <init> RAN!");
        //chestCavityInstance.init();
    }

    @Inject(at = @At("HEAD"), method = "baseTick")
    public void chestCavityLivingEntityBaseTickMixin(CallbackInfo info){
        ChestCavityUtil.onTick(chestCavityInstance);
    }

    @Inject(at = @At("TAIL"), method = "baseTick")
    protected void chestCavityLivingEntityBaseTickBreathAirMixin(CallbackInfo info) {
        if(!this.isEyeInFluid(FluidTags.WATER) || this.level.getBlockState(new BlockPos(this.getX(), this.getEyeY(), this.getZ())).is(Blocks.BUBBLE_COLUMN)) {
            this.setAirSupply(ChestCavityUtil.applyBreathOnLand(chestCavityInstance,this.getAirSupply(), this.increaseAirSupply(0)));
        }
    }

    @ModifyVariable(at = @At(value = "CONSTANT", args = "floatValue=0.0F", ordinal = 0), ordinal = 0, method = "actuallyHurt")
    public float chestCavityLivingEntityOnHitMixin(float amount, DamageSource source){
        if(source.getEntity() instanceof LivingEntity){
            Optional<ChestCavityEntity> cce = ChestCavityEntity.of(source.getEntity());
            if(cce.isPresent()){
                    amount = ChestCavityUtil.onHit(cce.get().getChestCavityInstance(), source, (LivingEntity)(Object)this,amount);
            }
        }
        return amount;
    }

    @Inject(at = @At("RETURN"), method = "createLootContext")
    protected void addCustomLootFromEntity(boolean p_21105_, DamageSource p_21106_, CallbackInfoReturnable<LootContext.Builder> cir) {
        LootContext lootContext = cir.getReturnValue().create(LootParameterSets.ENTITY);
        List<ItemStack> list = LootRegister.addLoot(lootContext);
        list = LootRegister.modifyLoot(list, lootContext);
        list.forEach(this::spawnAtLocation);
    }

    @Inject(at = @At("RETURN"), method = "decreaseAirSupply", cancellable = true)
    protected void chestCavityLivingEntityGetNextAirUnderwaterMixin(int air, CallbackInfoReturnable<Integer> info) {
        info.setReturnValue(ChestCavityUtil.applyBreathInWater(chestCavityInstance,air,info.getReturnValueI()));
    }

    @Inject(at = @At("RETURN"), method = "getDamageAfterArmorAbsorb",cancellable = true)
    public void chestCavityLivingEntityDamageMixin(DamageSource source, float amount, CallbackInfoReturnable<Float> info){
        info.setReturnValue(ChestCavityUtil.applyDefenses(chestCavityInstance, source, info.getReturnValueF()));
    }

    @Inject(at = @At("HEAD"), method = "dropEquipment")
    public void chestCavityLivingEntityDropInventoryMixin(CallbackInfo info){
        //chestCavityInstance.getChestCavityType().onDeath(chestCavityInstance);
        ChestCavityUtil.onDeath(this);
    }

    @ModifyVariable(at = @At("HEAD"), method = "addEffect", ordinal = 0)
    public EffectInstance chestCavityLivingEntityAddStatusEffectMixin(EffectInstance effect){
        return ChestCavityUtil.onAddStatusEffect(chestCavityInstance,effect);
    }







    /*
    Lnet/minecraft/entity/Entity; (maybe LivingEntity)
    updateVelocity(
            F
            Lnet/minecraft/util/math/Vec3d;
    )V
     */
    //Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z
    //@ModifyVariable(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;updateVelocity(FLnet/minecraft/util/math/Vec3d;)V"), method = "travel",ordinal = 1)
    //@Group(name = "WaterTravelMixins", max = 2, min = 1)
    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;moveRelative(FLnet/minecraft/util/math/vector/Vector3d;)V"), method = "travel",index = 0, require = 0)
    protected float chestCavityLivingEntityWaterTravelMixin(float g) {
        return g*ChestCavityUtil.applySwimSpeedInWater(chestCavityInstance);
    }
    //Origins combatibility variant. Why must I do this?!?
    //@Group(name = "WaterTravelMixins")
    //@ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;updateVelocity(Lnet/minecraft/entity/LivingEntity;FLnet/minecraft/util/math/Vec3d;)V"), method = "travel",index = 0)
    //protected double chestCavityLivingEntityWaterTravelMixinAlt(double g) {
    //    return g*ChestCavityUtil.applySwimSpeedInWater(chestCavityInstance);
    //}

    @Inject(at = @At("RETURN"), method = "getJumpPower",cancellable = true)
    public void chestCavityLivingEntityJumpVelocityMixin(CallbackInfoReturnable<Float> info){
        info.setReturnValue(ChestCavityUtil.applyLeaping(chestCavityInstance, info.getReturnValueF()));
    }

    public ChestCavityInstance getChestCavityInstance() {
        return chestCavityInstance;
    }

    public void setChestCavityInstance(ChestCavityInstance chestCavityInstance) {
        this.chestCavityInstance = chestCavityInstance;
    }


    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readCustomDataFromNbt(CompoundNBT tag, CallbackInfo callbackInfo) {
        chestCavityInstance.fromTag(tag,(LivingEntity)(Object)this);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void writeCustomDataToNbt(CompoundNBT tag, CallbackInfo callbackInfo) {
        chestCavityInstance.toTag(tag);
    }

    @Mixin(MobEntity.class)
    private static abstract class MixinMobEntity extends LivingEntity {
        protected MixinMobEntity(EntityType<? extends LivingEntity> entityType, World world) {super(entityType, world);}



        @Inject(at = @At("HEAD"), method = "checkAndHandleImportantInteractions"/*"method_29506"*/, cancellable = true) //if this breaks, its likely because yarn changed the name to interactWithItem
        protected void chestCavityLivingEntityInteractMobMixin(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResultType> info) {
            ChestCavity.printOnDebug("checkAndHandleImportantInteractions called for: " + this.toString());
            if(player.getItemInHand(hand).getItem() == CCItems.CHEST_OPENER.get() && (!(((LivingEntity)(Object)this) instanceof PlayerEntity))){
                ((ChestOpener)player.getItemInHand(hand).getItem()).openChestCavity(player,(LivingEntity)(Object)this);
                ChestCavity.printOnDebug("Opened Cavity from checkAndHandleImportantInteractions. this: " + ((LivingEntity)(Object)this).toString());
                info.setReturnValue(ActionResultType.SUCCESS);
            }
        }
    }
    
    @Mixin(PlayerEntity.class)
    public static abstract class MixinPlayerEntity extends LivingEntity{
        protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
            super(entityType, world);
        }

        @ModifyVariable(at = @At(value = "CONSTANT", args = "floatValue=0.0F", ordinal = 0), ordinal = 0, method = "actuallyHurt")
        public float chestCavityPlayerEntityOnHitMixin(float amount, DamageSource source){
            if(source.getEntity() instanceof LivingEntity){
                Optional<ChestCavityEntity> cce = ChestCavityEntity.of(source.getEntity());
                if(cce.isPresent()){
                    amount = ChestCavityUtil.onHit(cce.get().getChestCavityInstance(), source, (LivingEntity)(Object)this,amount);
                }
            }
            return amount;
        }

        @Inject(at = @At("HEAD"), method = "interactOn", cancellable = true)
        void chestCavityPlayerEntityInteractPlayerMixin(Entity entity, Hand hand, CallbackInfoReturnable<ActionResultType> info) {
            ChestCavity.printOnDebug("InteractOn Mixin Called! entity instanceof LivingEntity: " + (entity instanceof LivingEntity) + ". Entity.toString(): " + entity.toString());
            if (entity instanceof LivingEntity && ChestCavity.config.CAN_OPEN_OTHER_PLAYERS) {
                PlayerEntity player = ((PlayerEntity) (Object) this);
                ItemStack stack = player.getItemInHand(hand);
    
                if (stack.getItem() == CCItems.CHEST_OPENER.get()) {
                    ((ChestOpener) stack.getItem()).openChestCavity(player, (LivingEntity) entity);
                    info.setReturnValue(ActionResultType.SUCCESS);
                    info.cancel();
                }
            }
        }
        @Inject(at = @At("RETURN"), method = "getDigSpeed", cancellable = true, remap = false)
        void chestCavityPlayerEntityGetBlockBreakingSpeedMixin(BlockState block, @Nullable BlockPos pos, CallbackInfoReturnable<Float> cir) {
            cir.setReturnValue(ChestCavityUtil.applyNervesToMining(((ChestCavityEntity)this).getChestCavityInstance(),cir.getReturnValue()));
        }
    }

    @Mixin(CowEntity.class)
    private static abstract class MixinCowEntity extends AnimalEntity {

        protected MixinCowEntity(EntityType<? extends AnimalEntity> entityType, World world) {super(entityType, world);}

        @Inject(method = "mobInteract",
                /*at = @At(value = "INVOKE",
                        target = "Lnet/minecraft/entity/LivingEntity;setStackInHand(" +
                                    "Lnet/minecraft/util/Hand;" +
                                    "Lnet/minecraft/item/ItemStack;" +
                                    ")V",
                        shift = At.Shift.AFTER)*/
                at = @At(value = "RETURN", ordinal = 0)
                )
        protected void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResultType> info) {
            CommonOrganUtil.milkSilk(this);
        }
    }

    @Mixin(CreeperEntity.class)
    private static abstract class MixinCreeperEntity extends MonsterEntity {
        @Shadow
        private int swell;

        protected MixinCreeperEntity(EntityType<? extends MonsterEntity> entityType, World world) {super(entityType, world);}

        @Inject(at = @At("HEAD"), method = "tick")
        protected void chestCavityCreeperTickMixin(CallbackInfo info) {
            if(this.isAlive()
                    && swell > 1){
                ChestCavityEntity.of(this).ifPresent(cce -> {
                    if(cce.getChestCavityInstance().opened
                            && cce.getChestCavityInstance().getOrganScore(CCOrganScores.CREEPY) <= 0){
                        swell = 1;
                    }
                });
            }
        }
        /*value = "FIELD",
                                target = "Lnet/minecraft/entity/CreeperEntity;dead:Z"*/
        //"Lnet/minecraft/world/explosion/Explosion;createExplosion("+
        //  "Lnet/minecraft/entity/Entity;"+
        //  "DDDF"+
        //  "Lnet/minecraft/world/explosion/Explosion/DestructionType;"+
        //  ")V"
        /*
        @ModifyVariable(at = @At(value = "INVOKE",
                target = "Lnet/minecraft/world/explosion/Explosion;createExplosion("+
                "Lnet/minecraft/entity/Entity;"+
                "DDDF"+
                "Lnet/minecraft/world/explosion/Explosion/DestructionType;"+
                ")V"), ordinal = 0, method = "explode")
        public float chestCavityCreeperExplodeMixin(float f){
            MutableFloat boom = new MutableFloat(f);
            ChestCavityEntity.of(this).ifPresent(cce -> {
                ChestCavityInstance cc = cce.getChestCavityInstance();
                if(cc.opened){
                    boom.setValue(f*Math.sqrt(cc.getOrganScore(CCOrganScores.EXPLOSIVE))
                            /Math.min(1,Math.sqrt(cc.getChestCavityType().getDefaultOrganScore(CCOrganScores.EXPLOSIVE))));
                }
            });
            return f;
        }*/
    }

    @Mixin(ServerPlayerEntity.class)
    private static abstract class MixinServerPlayerEntity extends PlayerEntity {
        public MixinServerPlayerEntity(World world, BlockPos pos, float yaw, GameProfile profile) {
            super(world, pos, yaw, profile);
        }

        @Inject(method = "restoreFrom", at = @At("TAIL"))
        public void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo callbackInfo) {
            ChestCavity.printOnDebug("Attempting to load ChestCavityInstance");
            ChestCavityEntity.of(this).ifPresent(chestCavityEntity -> ChestCavityEntity.of(oldPlayer).ifPresent(oldCCPlayerEntityInterface -> {
                ChestCavity.printOnDebug("Copying ChestCavityInstance");
                chestCavityEntity.getChestCavityInstance().clone(oldCCPlayerEntityInterface.getChestCavityInstance());
            }));
        }

        @Inject(at = @At("RETURN"), method = "changeDimension", cancellable = true, remap = false)
        public void chestCavityEntityMoveToWorldMixin(ServerWorld level, ITeleporter teleporter, CallbackInfoReturnable<Entity> info){
            Entity entity = info.getReturnValue();
            if(entity instanceof ChestCavityEntity && !entity.level.isClientSide){
                NetworkUtil.SendS2CChestCavityUpdatePacket(((ChestCavityEntity)entity).getChestCavityInstance());
            }
        }
    }

    @Mixin(SheepEntity.class)
    private static abstract class MixinSheepEntity extends AnimalEntity {

        protected MixinSheepEntity(EntityType<? extends AnimalEntity> entityType, World world) {super(entityType, world);}

        @Inject(method = "shear",
                at = @At(value = "HEAD")
        )
        protected void chestCavitySheared(SoundCategory shearedSoundCategory, CallbackInfo info) {
            CommonOrganUtil.shearSilk(this);
        }
    }

    @Mixin(WitherEntity.class)
    private static abstract class MixinWitherEntity extends MonsterEntity {


        protected MixinWitherEntity(EntityType<? extends MonsterEntity> entityType, World world) {
            super(entityType, world);
        }

        //Lnet/minecraft/entity/boss/WitherEntity;dropItem(      //note that this might just be Entity instead.
        //  Lnet/minecraft/item/ItemConvertible;
        //)Lnet/minecraft/entity/ItemEntity;
        @Inject(method = "dropCustomDeathLoot",
                at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/boss/WitherEntity;spawnAtLocation(Lnet/minecraft/util/IItemProvider;)Lnet/minecraft/entity/item/ItemEntity;"),
                cancellable = true
        )
        protected void chestCavityPreventNetherStarDrop(DamageSource source, int lootingMultiplier, boolean allowDrops, CallbackInfo info) {
            Optional<ChestCavityEntity> chestCavityEntity = ChestCavityEntity.of(this);
            if(chestCavityEntity.isPresent()){
                ChestCavityInstance cc = chestCavityEntity.get().getChestCavityInstance();

                //if the nether star was taken from the wither's chest, refuse to drop
                if(cc.opened && cc.inventory.countItem(Items.NETHER_STAR) == 0){
                    info.cancel();
                }
            }
        }
    }


    @Shadow
    protected void defineSynchedData() {}

    @Shadow
    protected void readAdditionalSaveData(CompoundNBT tag) {}

    @Shadow
    protected void addAdditionalSaveData(CompoundNBT tag) {}

    @Shadow
    public IPacket<?> getAddEntityPacket() {return null;}

    @Shadow
    protected int increaseAirSupply(int air) {
        return 0;
    }
}
