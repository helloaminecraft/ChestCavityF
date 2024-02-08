package net.tigereye.chestcavity.registration;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.ui.ChestCavityScreenHandler;

public class CCContainers {
    public static final DeferredRegister<ContainerType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, ChestCavity.MODID);

    public static final RegistryObject<ContainerType<ChestCavityScreenHandler>> CHEST_CAVITY_SCREEN_HANDLER = MENU_TYPES.register("chest_cavity_screen", () -> new ContainerType<>(ChestCavityScreenHandler::new));

}
