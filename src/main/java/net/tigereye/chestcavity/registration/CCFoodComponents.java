package net.tigereye.chestcavity.registration;


import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.tigereye.chestcavity.ChestCavity;

public class CCFoodComponents {
    public static final Food ANIMAL_MUSCLE_FOOD_COMPONENT = new Food.Builder().nutrition(1).saturationMod(.4f).meat().fast().build();
    public static final Food SMALL_ANIMAL_MUSCLE_FOOD_COMPONENT = new Food.Builder().nutrition(1).saturationMod(.2f).meat().fast().build();
    public static final Food BURNT_MEAT_CHUNK_COMPONENT = new Food.Builder().nutrition(1).saturationMod(.8f).meat().fast().build();
    public static final Food RAW_BUTCHERED_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(2).saturationMod(.4f).meat().build();
    public static final Food COOKED_BUTCHERED_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(3).saturationMod(.8f).meat().build();
    public static final Food RAW_ORGAN_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(2).saturationMod(0.6f).meat().fast().build();
    public static final Food COOKED_ORGAN_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(3).saturationMod(1.2f).meat().fast().build();
    public static final Food RAW_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(4).saturationMod(.4f).meat().build();
    public static final Food COOKED_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(8).saturationMod(.8f).meat().build();
    public static final Food RAW_RICH_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(4).saturationMod(.6f).meat().build();
    public static final Food COOKED_RICH_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(8).saturationMod(1.2f).meat().build();
    public static final Food RAW_MINI_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(3).saturationMod(.4f).meat().build();
    public static final Food COOKED_MINI_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(6).saturationMod(.8f).meat().build();
    public static final Food RAW_RICH_MINI_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(3).saturationMod(.6f).meat().build();
    public static final Food COOKED_RICH_MINI_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(6).saturationMod(1.2f).meat().build();

    public static final Food ROTTEN_MUSCLE_FOOD_COMPONENT = new Food.Builder().nutrition(1).saturationMod(0.1F).effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.8F).meat().build();
    public static final Food ROTTEN_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(8).saturationMod(0.1F).effect(new EffectInstance(Effects.HUNGER, 600, 0), 0.8F).meat().build();

    public static final Food INSECT_MUSCLE_FOOD_COMPONENT = new Food.Builder().nutrition(1).saturationMod(.4f).meat().fast().effect(new EffectInstance(Effects.POISON, 80), 1f).build();
    public static final Food RAW_TOXIC_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(2).saturationMod(.4f).meat().effect(new EffectInstance(Effects.POISON, 80), 1f).build();
    public static final Food COOKED_TOXIC_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(3).saturationMod(.8f).meat().effect(new EffectInstance(Effects.CONFUSION, 160, 1), 1f).build();
    public static final Food RAW_TOXIC_ORGAN_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(2).saturationMod(0.6f).meat().fast().effect(new EffectInstance(Effects.POISON, 80), 1f).build();
    public static final Food COOKED_TOXIC_ORGAN_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(3).saturationMod(1.2f).meat().fast().effect(new EffectInstance(Effects.CONFUSION, 160, 1), 1f).build();
    public static final Food RAW_TOXIC_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(4).saturationMod(.4f).meat().effect(new EffectInstance(Effects.POISON, 80), 1f).build();
    public static final Food COOKED_TOXIC_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(8).saturationMod(.8f).meat().effect(new EffectInstance(Effects.CONFUSION, 160, 1), 1f).build();
    public static final Food RAW_RICH_TOXIC_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(4).saturationMod(.6f).meat().effect(new EffectInstance(Effects.POISON, 80), 1f).build();
    public static final Food COOKED_RICH_TOXIC_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(8).saturationMod(1.2f).meat().effect(new EffectInstance(Effects.CONFUSION, 160, 1), 1f).build();

    public static final Food ALIEN_MUSCLE_FOOD_COMPONENT = new Food.Builder().nutrition(1).saturationMod(.4f).meat().fast().effect(new EffectInstance(Effects.LEVITATION, 20), 1f).build();
    public static final Food RAW_ALIEN_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(2).saturationMod(.4f).meat().effect(new EffectInstance(Effects.LEVITATION, 80), 1f).build();
    public static final Food COOKED_ALIEN_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(3).saturationMod(.8f).meat().effect(new EffectInstance(Effects.SLOW_FALLING, 10, 1), 1f).build();
    public static final Food RAW_ALIEN_ORGAN_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(2).saturationMod(0.6f).meat().fast().effect(new EffectInstance(Effects.LEVITATION, 40), 1f).build();
    public static final Food COOKED_ALIEN_ORGAN_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(3).saturationMod(1.2f).meat().fast().effect(new EffectInstance(Effects.SLOW_FALLING, 15, 1), 1f).build();
    public static final Food RAW_ALIEN_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(4).saturationMod(.4f).meat().effect(new EffectInstance(Effects.LEVITATION, 80), 1f).build();
    public static final Food COOKED_ALIEN_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(8).saturationMod(.8f).meat().effect(new EffectInstance(Effects.SLOW_FALLING, 20, 1), 1f).build();
    public static final Food RAW_RICH_ALIEN_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(4).saturationMod(.6f).meat().effect(new EffectInstance(Effects.LEVITATION, 320), 1f).build();
    public static final Food COOKED_RICH_ALIEN_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(8).saturationMod(1.2f).meat().effect(new EffectInstance(Effects.SLOW_FALLING, 40, 1), 1f).build();

    public static final Food DRAGON_MUSCLE_FOOD_COMPONENT = new Food.Builder().nutrition(1).saturationMod(.4f).meat().fast()
            .effect(new EffectInstance(Effects.DAMAGE_BOOST, 300), 1f)
            .effect(new EffectInstance(Effects.DIG_SPEED, 300), 1f)
            .alwaysEat().build();
    public static final Food RAW_DRAGON_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(2).saturationMod(.4f).meat()
            .effect(new EffectInstance(Effects.DAMAGE_BOOST, 900), 1f)
            .effect(new EffectInstance(Effects.DIG_SPEED, 900), 1f)
            .alwaysEat().build();
    public static final Food COOKED_DRAGON_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(3).saturationMod(.8f).meat()
            .effect(new EffectInstance(Effects.DAMAGE_BOOST, 150, 1), 1f)
            .effect(new EffectInstance(Effects.DIG_SPEED, 150, 1), 1f)
            .alwaysEat().build();
    public static final Food RAW_DRAGON_ORGAN_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(2).saturationMod(0.6f).meat().fast()
            .effect(new EffectInstance(Effects.DAMAGE_BOOST, 90*20), 1f)
            .effect(new EffectInstance(Effects.DIG_SPEED, 90*20), 1f)
            .alwaysEat().build();
    public static final Food COOKED_DRAGON_ORGAN_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(3).saturationMod(1.2f).meat().fast()
            .effect(new EffectInstance(Effects.DAMAGE_BOOST, 15*20, 1), 1f)
            .effect(new EffectInstance(Effects.DIG_SPEED, 15*20, 1), 1f)
            .alwaysEat().build();
    public static final Food RAW_DRAGON_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(4).saturationMod(.4f).meat()
            .effect(new EffectInstance(Effects.DAMAGE_BOOST, 480*20), 1f)
            .effect(new EffectInstance(Effects.DIG_SPEED, 480*20), 1f)
            .alwaysEat().build();
    public static final Food COOKED_DRAGON_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(8).saturationMod(.8f).meat()
            .effect(new EffectInstance(Effects.DAMAGE_BOOST, 90*20, 1), 1f)
            .effect(new EffectInstance(Effects.DIG_SPEED, 90*20, 1), 1f)
            .alwaysEat().build();
    public static final Food RAW_RICH_DRAGON_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(4).saturationMod(.6f).meat()
            .effect(new EffectInstance(Effects.DAMAGE_BOOST, 960*20), 1f)
            .effect(new EffectInstance(Effects.DIG_SPEED, 960*20), 1f)
            .alwaysEat().build();
    public static final Food COOKED_RICH_DRAGON_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(8).saturationMod(1.2f).meat()
            .effect(new EffectInstance(Effects.DAMAGE_BOOST, 180*20, 1), 1f)
            .effect(new EffectInstance(Effects.DIG_SPEED, 180*20, 1), 1f)
            .alwaysEat().build();
    public static final Food DRAGON_HEART_FOOD_COMPONENT = new Food.Builder().nutrition(1).saturationMod(.4f).meat().fast()
            .effect(new EffectInstance(Effects.DAMAGE_BOOST, 30*20, 3), 1f)
            .effect(new EffectInstance(Effects.DIG_SPEED, 30*20, 3), 1f)
            .effect(new EffectInstance(Effects.POISON, 2, 3), 1f)
            .effect(new EffectInstance(Effects.WITHER, 2, 3), 1f)
            .effect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 2, 3), 1f)
            .effect(new EffectInstance(Effects.HUNGER, 2, 3), 1f)
            .effect(new EffectInstance(Effects.CONFUSION, 2, 3), 1f)
            .effect(new EffectInstance(Effects.BLINDNESS, 2, 3), 1f)
            .effect(new EffectInstance(Effects.SLOW_FALLING, 2, 3), 1f)
            .effect(new EffectInstance(Effects.LEVITATION, 2, 3), 1f)
            .alwaysEat().build();
    
    public static final Food HUMAN_MUSCLE_FOOD_COMPONENT = new Food.Builder().nutrition(2).saturationMod(.4f).meat().fast()
            .effect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 24000, 1), ChestCavity.config.RISK_OF_PRIONS).effect(new EffectInstance(Effects.DIG_SLOWDOWN, 24000, 1), ChestCavity.config.RISK_OF_PRIONS).effect(new EffectInstance(Effects.WEAKNESS, 24000, 1), ChestCavity.config.RISK_OF_PRIONS).build();
    public static final Food RAW_MAN_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(3).saturationMod(.4f).meat()
            .effect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 24000, 1), ChestCavity.config.RISK_OF_PRIONS).effect(new EffectInstance(Effects.DIG_SLOWDOWN, 24000, 1), ChestCavity.config.RISK_OF_PRIONS).effect(new EffectInstance(Effects.WEAKNESS, 24000, 1), ChestCavity.config.RISK_OF_PRIONS).build();
    public static final Food COOKED_MAN_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(4).saturationMod(.8f).meat()
            .effect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 24000, 0), ChestCavity.config.RISK_OF_PRIONS).effect(new EffectInstance(Effects.DIG_SLOWDOWN, 24000, 0), ChestCavity.config.RISK_OF_PRIONS).effect(new EffectInstance(Effects.WEAKNESS, 24000, 0), ChestCavity.config.RISK_OF_PRIONS).build();
    public static final Food RAW_HUMAN_ORGAN_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(3).saturationMod(0.6f).meat().fast()
            .effect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 24000, 1), ChestCavity.config.RISK_OF_PRIONS).effect(new EffectInstance(Effects.DIG_SLOWDOWN, 24000, 1), ChestCavity.config.RISK_OF_PRIONS).effect(new EffectInstance(Effects.WEAKNESS, 24000, 1), ChestCavity.config.RISK_OF_PRIONS).build();
    public static final Food COOKED_HUMAN_ORGAN_MEAT_FOOD_COMPONENT = new Food.Builder().nutrition(4).saturationMod(1.2f).meat().fast()
            .effect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 24000, 0), ChestCavity.config.RISK_OF_PRIONS).effect(new EffectInstance(Effects.DIG_SLOWDOWN, 24000, 0), ChestCavity.config.RISK_OF_PRIONS).effect(new EffectInstance(Effects.WEAKNESS, 24000, 0), ChestCavity.config.RISK_OF_PRIONS).build();
    public static final Food RAW_HUMAN_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(5).saturationMod(.4f).meat()
            .effect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 24000, 1), ChestCavity.config.RISK_OF_PRIONS).effect(new EffectInstance(Effects.DIG_SLOWDOWN, 24000, 1), ChestCavity.config.RISK_OF_PRIONS).effect(new EffectInstance(Effects.WEAKNESS, 24000, 1), ChestCavity.config.RISK_OF_PRIONS).build();
    public static final Food COOKED_HUMAN_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(9).saturationMod(.8f).meat()
            .effect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 24000, 0), ChestCavity.config.RISK_OF_PRIONS).effect(new EffectInstance(Effects.DIG_SLOWDOWN, 24000, 0), ChestCavity.config.RISK_OF_PRIONS).effect(new EffectInstance(Effects.WEAKNESS, 24000, 0), ChestCavity.config.RISK_OF_PRIONS).build();
    public static final Food RAW_RICH_HUMAN_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(5).saturationMod(.6f).meat()
            .effect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 24000, 1), ChestCavity.config.RISK_OF_PRIONS).effect(new EffectInstance(Effects.DIG_SLOWDOWN, 24000, 1), ChestCavity.config.RISK_OF_PRIONS).effect(new EffectInstance(Effects.WEAKNESS, 24000, 1), ChestCavity.config.RISK_OF_PRIONS).build();
    public static final Food COOKED_RICH_HUMAN_SAUSAGE_FOOD_COMPONENT = new Food.Builder().nutrition(9).saturationMod(1.2f).meat()
            .effect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 24000, 0), ChestCavity.config.RISK_OF_PRIONS).effect(new EffectInstance(Effects.DIG_SLOWDOWN, 24000, 0), ChestCavity.config.RISK_OF_PRIONS).effect(new EffectInstance(Effects.WEAKNESS, 24000, 0), ChestCavity.config.RISK_OF_PRIONS).build();

    public static final Food CUD_FOOD_COMPONENT = new Food.Builder().nutrition(1).saturationMod(.1f).build();
    public static final Food FURNACE_POWER_FOOD_COMPONENT = new Food.Builder().nutrition(1).saturationMod(.6f).build();
}
