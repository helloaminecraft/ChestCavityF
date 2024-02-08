package net.tigereye.chestcavity.mixin;



import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.tigereye.chestcavity.interfaces.CCStatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Effect.class)
public class MixinStatusEffect implements CCStatusEffect {

    @Shadow
    private EffectType category;

    @Override
    public boolean CC_IsHarmful() {
        return (category == EffectType.HARMFUL);
    }

    @Override
    public boolean CC_IsBeneficial() {
        return (category == EffectType.BENEFICIAL);
    }
}
