package net.tigereye.chestcavity.registration;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.mob_effect.CCStatusEffect;
import net.tigereye.chestcavity.mob_effect.FurnacePower;
import net.tigereye.chestcavity.mob_effect.OrganRejection;
import net.tigereye.chestcavity.mob_effect.Ruminating;

public class CCStatusEffects {
    public static final DeferredRegister<Effect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, ChestCavity.MODID);

    public static final RegistryObject<Effect> ORGAN_REJECTION = MOB_EFFECTS.register("organ_rejection", OrganRejection::new);
    public static final RegistryObject<Effect> ARROW_DODGE_COOLDOWN = MOB_EFFECTS.register("arrow_dodge_cooldown", () -> new CCStatusEffect(EffectType.NEUTRAL,0x000000));
    public static final RegistryObject<Effect> DRAGON_BOMB_COOLDOWN = MOB_EFFECTS.register("dragon_bomb_cooldown", () -> new CCStatusEffect(EffectType.NEUTRAL,0x000000));
    public static final RegistryObject<Effect> DRAGON_BREATH_COOLDOWN = MOB_EFFECTS.register("dragon_breath_cooldown", () -> new CCStatusEffect(EffectType.NEUTRAL,0x000000));
    public static final RegistryObject<Effect> EXPLOSION_COOLDOWN = MOB_EFFECTS.register("explosion_cooldown", () -> new CCStatusEffect(EffectType.NEUTRAL,0x000000));
    public static final RegistryObject<Effect> FORCEFUL_SPIT_COOLDOWN = MOB_EFFECTS.register("forceful_spit_cooldown", () -> new CCStatusEffect(EffectType.NEUTRAL,0x000000));
    public static final RegistryObject<Effect> FURNACE_POWER = MOB_EFFECTS.register("furnace_power", FurnacePower::new);
    public static final RegistryObject<Effect> GHASTLY_COOLDOWN = MOB_EFFECTS.register("ghastly_cooldown", () -> new CCStatusEffect(EffectType.NEUTRAL,0x000000));
    public static final RegistryObject<Effect> IRON_REPAIR_COOLDOWN = MOB_EFFECTS.register("iron_repair_cooldown", () -> new CCStatusEffect(EffectType.NEUTRAL,0x000000));
    public static final RegistryObject<Effect> PYROMANCY_COOLDOWN = MOB_EFFECTS.register("pyromancy_cooldown", () -> new CCStatusEffect(EffectType.NEUTRAL,0x000000));
    public static final RegistryObject<Effect> RUMINATING = MOB_EFFECTS.register("ruminating", Ruminating::new);
    public static final RegistryObject<Effect> SHULKER_BULLET_COOLDOWN = MOB_EFFECTS.register("shulker_bullet_cooldown", () -> new CCStatusEffect(EffectType.NEUTRAL,0x000000));
    public static final RegistryObject<Effect> SILK_COOLDOWN = MOB_EFFECTS.register("silk_cooldown", () -> new CCStatusEffect(EffectType.NEUTRAL,0x000000));
    public static final RegistryObject<Effect> VENOM_COOLDOWN = MOB_EFFECTS.register("venom_cooldown", () -> new CCStatusEffect(EffectType.NEUTRAL,0x000000));
    public static final RegistryObject<Effect> WATER_VULNERABILITY = MOB_EFFECTS.register("water_vulnerability", () -> new CCStatusEffect(EffectType.NEUTRAL,0x000000));
}
