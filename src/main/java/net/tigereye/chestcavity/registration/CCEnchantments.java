package net.tigereye.chestcavity.registration;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.enchantments.MalpracticeCurseEnchantment;
import net.tigereye.chestcavity.enchantments.ONegativeEnchantment;
import net.tigereye.chestcavity.enchantments.SurgicalEnchantment;
import net.tigereye.chestcavity.enchantments.TomophobiaEnchantment;

public class CCEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ChestCavity.MODID);

    public static final RegistryObject<Enchantment> O_NEGATIVE = ENCHANTMENTS.register("o_negative", ONegativeEnchantment::new);
    public static final RegistryObject<Enchantment> SURGICAL = ENCHANTMENTS.register("surgical", SurgicalEnchantment::new);
    public static final RegistryObject<Enchantment> MALPRACTICE = ENCHANTMENTS.register("malpractice", MalpracticeCurseEnchantment::new);
    public static final RegistryObject<Enchantment> TOMOPHOBIA = ENCHANTMENTS.register("tomophobia", TomophobiaEnchantment::new);
}
