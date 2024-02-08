package net.tigereye.chestcavity.mixin;


import com.google.gson.JsonElement;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.chestcavities.organs.OrganManager;
import net.tigereye.chestcavity.chestcavities.types.json.GeneratedChestCavityAssignmentManager;
import net.tigereye.chestcavity.chestcavities.types.json.GeneratedChestCavityTypeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
//@Mod.EventBusSubscriber(modid = ChestCavity.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ResourceReloadMixin {

    @Inject(at = @At("TAIL"), method = "apply(Ljava/util/Map;Lnet/minecraft/resources/IResourceManager;Lnet/minecraft/profiler/IProfiler;)V")
    private void loadCustomResources(Map<ResourceLocation, JsonElement> map, IResourceManager manager, IProfiler profiler, CallbackInfo ci) {
        ChestCavity.printOnDebug("ResourceReload Mixin called, Loading Resources!");
        //SimpleReloadableResourceManager manager = new SimpleReloadableResourceManager(ResourcePackType.SERVER_DATA);
        //manager.add(new VanillaPack("minecraft"));
        //manager.add(new ModFileResourcePack(ModList.get().getModFileById("chestcavity").getFile()));

        new OrganManager().onResourceManagerReload(manager);
        new GeneratedChestCavityTypeManager().onResourceManagerReload(manager);
        new GeneratedChestCavityAssignmentManager().onResourceManagerReload(manager);
        //manager.close();
    }
}
