package net.tigereye.chestcavity.mixin;


import net.minecraft.potion.EffectInstance;
import net.tigereye.chestcavity.interfaces.CCStatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EffectInstance.class)
public abstract class MixinStatusEffectInstance implements CCStatusEffectInstance {

	@Shadow
	private int duration;

	public void CC_setDuration(int duration) {
		this.duration = duration;
	}
}
