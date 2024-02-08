package net.tigereye.chestcavity.registration;


import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class CCTagOrgans {

    public static Map<ITag.INamedTag<Item>,Map<ResourceLocation,Float>> tagMap = new HashMap<>();

    public static void init(){
        Map<ResourceLocation,Float> ease_of_access = new HashMap<>();
        ease_of_access.put(CCOrganScores.EASE_OF_ACCESS,1f* Items.OAK_DOOR.getMaxStackSize());
        tagMap.put(ItemTags.DOORS,ease_of_access);
        tagMap.put(ItemTags.TRAPDOORS,ease_of_access);
    }
}