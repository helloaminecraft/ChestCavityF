package net.tigereye.chestcavity.enchantments;



import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.LootBonusEnchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.tigereye.chestcavity.registration.CCEnchantments;

public class SurgicalEnchantment extends LootBonusEnchantment {
    public SurgicalEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentType.WEAPON, EquipmentSlotType.MAINHAND);
    }

    public int getMinCost(int level) {
        return 15 + (level - 1) * 9;
    }

    public int getMaxCost(int level) {
        return super.getMinCost(level) + 50;
    }

    public int getMaxLevel() {
        return 3;
    }

    public boolean checkCompatibility(Enchantment other) {
        return super.checkCompatibility(other) &&
                other.isCompatibleWith(Enchantments.MOB_LOOTING)
                && other != CCEnchantments.TOMOPHOBIA.get();
    }
}
