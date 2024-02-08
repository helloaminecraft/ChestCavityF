package net.tigereye.chestcavity.registration;


import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.recipes.InfuseVenomGland;
import net.tigereye.chestcavity.recipes.SalvageRecipe;
import net.tigereye.chestcavity.recipes.json.SalvageRecipeSerializer;

public class CCRecipes {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ChestCavity.MODID);

    public static RegistryObject<SpecialRecipeSerializer<InfuseVenomGland>> INFUSE_VENOM_GLAND = RECIPE_SERIALIZERS.register("crafting_special_infuse_venom_gland", () -> new SpecialRecipeSerializer<>(InfuseVenomGland::new));
    public static ResourceLocation SALVAGE_RECIPE_ID = new ResourceLocation(ChestCavity.MODID,"crafting_salvage");
    public static IRecipeType<SalvageRecipe> SALVAGE_RECIPE_TYPE = new IRecipeType<SalvageRecipe>() {public String toString() {return SALVAGE_RECIPE_ID.toString();}};
    public static RegistryObject<SalvageRecipeSerializer> SALVAGE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_salvage", SalvageRecipeSerializer::new);
}