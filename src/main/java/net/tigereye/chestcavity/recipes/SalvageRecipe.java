package net.tigereye.chestcavity.recipes;


import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.tigereye.chestcavity.registration.CCRecipes;

public class SalvageRecipe implements ICraftingRecipe {
    private final Ingredient input;
    private int required;
    private final ItemStack outputStack;
    private final ResourceLocation id;

    public SalvageRecipe(Ingredient input, int required, ItemStack outputStack, ResourceLocation id){
        this.input = input;
        this.required = required;
        this.outputStack = outputStack;
        this.id = id;
    }

    public Ingredient getInput(){
        return input;
    }

    public int getRequired(){return required;}

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.withSize(required,input);
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        //ChestCavity.LOGGER.info("Attempting to match salvage recipe");
        int count = 0;
        ItemStack target;
        for(int i = 0; i < inv.getContainerSize(); ++i) {
            target = inv.getItem(i);
            if(target != null && target != ItemStack.EMPTY && target.getItem() != Items.AIR) {
                if (input.test(inv.getItem(i))) {
                    count++;
                    //ChestCavity.LOGGER.info("Salvage recipe counts "+count+" "+target.getName()+"s");
                }
                else{
                    //ChestCavity.LOGGER.info("Salvage recipe found bad item: "+target.getName().getString());
                    return false;
                }
            }
        }
        //if(count > 0){
            //ChestCavity.LOGGER.info("Found salvage recipe match");
        //}
        return count > 0 && count % required == 0;
    }

    @Override
    public ItemStack assemble(CraftingInventory inv) {
        //ChestCavity.LOGGER.info("Attempting to craft salvage recipe");
        int count = 0;
        ItemStack target;
        for(int i = 0; i < inv.getContainerSize(); ++i) {
            target = inv.getItem(i);
            if(target != null && target != ItemStack.EMPTY && target.getItem() != Items.AIR) {
                if (input.test(inv.getItem(i))) {
                    count++;
                }
                else{
                    return ItemStack.EMPTY;
                }
            }
        }
        //ChestCavity.LOGGER.info("Found salvage recipe, count "+count);
        if(count == 0 || count % required != 0){
            //ChestCavity.LOGGER.info("Salvage recipe failed modulo check");
            return ItemStack.EMPTY;
        }
        count = (count / required) * outputStack.getCount();
        if(count > outputStack.getMaxStackSize()) {
            //ChestCavity.LOGGER.info("Salvage recipe exceeded max stack size");
            return ItemStack.EMPTY;
        }
        ItemStack out = getResultItem();
        out.setCount(count);
        return out;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return outputStack.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return CCRecipes.SALVAGE_RECIPE_SERIALIZER.get();
    }

    /*@Override
    public RecipeType<SalvageRecipe> getType() {
        return CCRecipes.SALVAGE_RECIPE_TYPE;
    }
    */

}
