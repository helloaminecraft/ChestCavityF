package net.tigereye.chestcavity.recipes;


import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.tigereye.chestcavity.registration.CCItems;
import net.tigereye.chestcavity.registration.CCRecipes;
import net.tigereye.chestcavity.util.CommonOrganUtil;

public class InfuseVenomGland extends SpecialRecipe {
    public InfuseVenomGland(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        boolean foundVenomGland = false;
        boolean foundPotion = false;
        for(int i = 0; i < craftingInventory.getWidth(); ++i) {
            for(int j = 0; j < craftingInventory.getHeight(); ++j) {
                ItemStack itemStack = craftingInventory.getItem(i + j * craftingInventory.getWidth());
                if (itemStack.getItem() == CCItems.VENOM_GLAND.get()) {
                    if(foundVenomGland){
                        return false;
                    }
                    foundVenomGland = true;
                }
                else if(itemStack.getItem() == Items.POTION ||
                        itemStack.getItem() == Items.SPLASH_POTION ||
                        itemStack.getItem() == Items.LINGERING_POTION){
                    if(foundPotion){
                        return false;
                    }
                    foundPotion = true;
                }
            }
        }
        return foundVenomGland&&foundPotion;
    }

    @Override
    public ItemStack assemble(CraftingInventory craftingInventory) {
        ItemStack venomGland = null;
        ItemStack potion = null;
        ItemStack output = null;
        for(int i = 0; i < craftingInventory.getWidth(); ++i) {
            for(int j = 0; j < craftingInventory.getHeight(); ++j) {
                ItemStack itemStack = craftingInventory.getItem(i + j * craftingInventory.getWidth());
                if (itemStack.getItem() == CCItems.VENOM_GLAND.get()) {
                    if(venomGland != null){
                        return ItemStack.EMPTY;
                    }
                    venomGland = itemStack;
                }
                else if(itemStack.getItem() == Items.POTION ||
                        itemStack.getItem() == Items.SPLASH_POTION ||
                        itemStack.getItem() == Items.LINGERING_POTION){
                    if(potion != null){
                        return ItemStack.EMPTY;
                    }
                    potion = itemStack;
                }
            }
        }
        if(venomGland != null && potion != null){
            output = venomGland.copy();
            CommonOrganUtil.setStatusEffects(output, potion);
            return output;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return (width * height >= 2);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return CCRecipes.INFUSE_VENOM_GLAND.get();
    }
}
