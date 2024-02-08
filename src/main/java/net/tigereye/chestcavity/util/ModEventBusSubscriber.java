package net.tigereye.chestcavity.util;


import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.registration.CCRecipes;

@Mod.EventBusSubscriber(modid = ChestCavity.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusSubscriber {

    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
        CCRecipes.SALVAGE_RECIPE_TYPE = Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(ChestCavity.MODID,"crafting_salvage"), CCRecipes.SALVAGE_RECIPE_TYPE);
    }
}
