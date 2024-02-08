 package net.tigereye.chestcavity;


import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tigereye.chestcavity.config.CCConfig;
import net.tigereye.chestcavity.network.NetworkHandler;
import net.tigereye.chestcavity.registration.*;
import net.tigereye.chestcavity.ui.ChestCavityScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ChestCavity.MODID)
public class ChestCavity {
	public static final String MODID = "chestcavity";
    private static final boolean DEBUG_MODE = false; //Make SURE that this is false when building
	public static final Logger LOGGER = LogManager.getLogger();
	public static CCConfig config;
	public static final ResourceLocation COMPATIBILITY_TAG = new ResourceLocation(MODID,"organ_compatibility");
	public static final ItemGroup ORGAN_ITEM_GROUP = new ItemGroup("chestcavity.organs") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(CCItems.HUMAN_STOMACH.get());
		}
	};

	public ChestCavity() {
		LOGGER.info("构建 Chest Cavity 模组，开始加载序列!");
		printOnDebug("Chest Cavity 模组构建完成!");
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(this::setup);
		bus.addListener(this::doClientStuff);

		//Register mod resources
		AutoConfig.register(CCConfig.class, GsonConfigSerializer::new);
		config = AutoConfig.getConfigHolder(CCConfig.class).getConfig();
		CCContainers.MENU_TYPES.register(bus);
		CCItems.ITEMS.register(bus);
		CCRecipes.RECIPE_SERIALIZERS.register(bus);
		CCEnchantments.ENCHANTMENTS.register(bus);
		CCListeners.register();
		CCStatusEffects.MOB_EFFECTS.register(bus);
		CCTagOrgans.init();
}

	public void setup(FMLCommonSetupEvent event) {
		NetworkHandler.init();
	}

	public void doClientStuff(FMLClientSetupEvent event) {
		ScreenManager.register(CCContainers.CHEST_CAVITY_SCREEN_HANDLER.get(), ChestCavityScreen::new);
		CCKeybindings.init();
	}

	public static boolean isDebugMode() {
		return DEBUG_MODE;
	}

	public static void printOnDebug(String stringToPrint) {
		if(DEBUG_MODE) {
			System.out.println("DEBUG: " + stringToPrint);
		}
	}
}
