package net.tigereye.chestcavity.chestcavities.types.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import net.minecraft.util.ResourceLocation;
import net.tigereye.chestcavity.ChestCavity;

import java.util.HashMap;
import java.util.Map;

public class ChestCavityAssignmentSerializer {
    //remember: the first ResourceLocation is the entity, the second is the chest cavity type
    public Map<ResourceLocation, ResourceLocation> read(ResourceLocation id, ChestCavityAssignmentJsonFormat ccaJson) {

        if (ccaJson.chestcavity == null) {
            throw new JsonSyntaxException("Chest cavity assignment " + id + " must have a chest cavity type");
        }
        if (ccaJson.entities == null) {
            throw new JsonSyntaxException("Chest cavity assignment " + id + " must have a list of entities");
        }
        //bossChestCavity should default to false
        //playerChestCavity should default to false

        Map<ResourceLocation, ResourceLocation> assignments = new HashMap<>();
        ResourceLocation chestcavitytype = new ResourceLocation(ccaJson.chestcavity);
        int i = 0;
        for (JsonElement entry :
                ccaJson.entities) {
            ++i;
            try {
                assignments.put(new ResourceLocation(entry.getAsString()),chestcavitytype);
            } catch (Exception e) {
                ChestCavity.LOGGER.error("Error parsing entry no. " + i + " in " + id.toString() + "'s entity list");
            }
        }
        return assignments;
    }
}