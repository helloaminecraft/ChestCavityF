package net.tigereye.chestcavity.enchantments;



import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.tigereye.chestcavity.registration.CCEnchantments;

public class MalpracticeCurseEnchantment extends Enchantment {
    public MalpracticeCurseEnchantment(){
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

    public boolean canEnchant(ItemStack stack) {
        return true;
    }

    public boolean isTreasureOnly() {
        return true;
    }

    public boolean checkCompatibility(Enchantment other) {
        return super.checkCompatibility(other)
                && other != CCEnchantments.TOMOPHOBIA.get();
    }

    public boolean isCurse() {
        return true;
    }
}