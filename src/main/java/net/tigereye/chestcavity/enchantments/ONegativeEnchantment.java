package net.tigereye.chestcavity.enchantments;


import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class ONegativeEnchantment extends Enchantment {

    public ONegativeEnchantment() {
        super(Rarity.RARE, EnchantmentType.VANISHABLE, new EquipmentSlotType[]{});
    }


    public int getMinCost(int level) {
        return 50*level;
    }

    public int getMaxCost(int level) {
        return 100*level;
    }

    public int getMaxLevel() {
        return 2;
    }

    public boolean canEnchant(ItemStack stack) {
        return true;
    }

    public boolean isTreasureOnly() {
        return true;
    }

    public boolean isTradeable() {
        return false;
    }

    public boolean checkCompatibility(Enchantment other) {
        return super.checkCompatibility(other);
    }



}
