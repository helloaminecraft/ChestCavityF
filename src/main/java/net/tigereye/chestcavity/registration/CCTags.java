package net.tigereye.chestcavity.registration;


import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.tigereye.chestcavity.ChestCavity;

public class CCTags {
    public static final ITag.INamedTag<Item> BUTCHERING_TOOL = ItemTags.createOptional(new ResourceLocation(ChestCavity.MODID,"butchering_tool"));
    public static final ITag.INamedTag<Item> ROTTEN_FOOD = ItemTags.createOptional(new ResourceLocation(ChestCavity.MODID,"rotten_food"));
    public static final ITag.INamedTag<Item> CARNIVORE_FOOD = ItemTags.createOptional(new ResourceLocation(ChestCavity.MODID,"carnivore_food"));
    public static final ITag.INamedTag<Item> SALVAGEABLE = ItemTags.createOptional(new ResourceLocation(ChestCavity.MODID,"salvageable"));
    public static final ITag.INamedTag<Item> IRON_REPAIR_MATERIAL = ItemTags.createOptional(new ResourceLocation(ChestCavity.MODID,"iron_repair_material"));

    //public static final Tag<Item> BUTCHERING_TOOL = TagRegistry.item(new ResourceLocation(ChestCavity.MODID,"butchering_tool"));
    //public static final Tag<Item> ROTTEN_FOOD = TagRegistry.item(new ResourceLocation(ChestCavity.MODID,"rotten_food"));
    //public static final Tag<Item> CARNIVORE_FOOD = TagRegistry.item(new ResourceLocation(ChestCavity.MODID,"carnivore_food"));
    //public static final Tag<Item> SALVAGEABLE = TagRegistry.item(new ResourceLocation(ChestCavity.MODID,"salvageable"));
    //public static final Tag<Item> IRON_REPAIR_MATERIAL = TagRegistry.item(new ResourceLocation(ChestCavity.MODID,"iron_repair_material"));
}
