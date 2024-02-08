package net.tigereye.chestcavity.enchantments;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.tigereye.chestcavity.registration.CCEnchantments;

public class TomophobiaEnchantment extends Enchantment {
    public TomophobiaEnchantment(){
        super(Rarity.VERY_RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    public int getMinCost(int level) {
        return 25;
    }

    public int getMaxCost(int level) {
        return 50;
    }

    public int getMaxLevel() {
        return 1;
    }

    public boolean isTreasureOnly() {
        return true;
    }

    public boolean checkCompatibility(Enchantment other) {
        return super.checkCompatibility(other)
                && other != CCEnchantments.SURGICAL.get()
                && other != CCEnchantments.MALPRACTICE.get();
    }
}