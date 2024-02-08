package net.tigereye.chestcavity.chestcavities.organs;

import com.google.gson.Gson;
import net.minecraft.item.Item;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.util.Pair;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class OrganManager implements IResourceManagerReloadListener {
    private static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation(ChestCavity.MODID, "organs");
    private final OrganSerializer SERIALIZER = new OrganSerializer();
    public static Map<ResourceLocation, OrganData> GeneratedOrganData = new HashMap<>();

    @Override
    public void onResourceManagerReload(IResourceManager manager) {
        GeneratedOrganData.clear();
        ChestCavity.LOGGER.info("Loading organs.");
        for (ResourceLocation id : manager.listResources(RESOURCE_LOCATION.getPath(), path -> path.endsWith(".json"))) {
            try (InputStream stream = manager.getResource(id).getInputStream()) {
                Reader reader = new InputStreamReader(stream);
                Pair<ResourceLocation, OrganData> organDataPair = SERIALIZER.read(id, new Gson().fromJson(reader, OrganJsonFormat.class));
                GeneratedOrganData.put(organDataPair.getLeft(), organDataPair.getRight());
            } catch (Exception e) {
                ChestCavity.LOGGER.error("Error occurred while loading resource json " + id.toString(), e);
            }
        }
        ChestCavity.LOGGER.info("Loaded " + GeneratedOrganData.size() + " organs.");
    }

    public static boolean hasEntry(Item item) {
        return GeneratedOrganData.containsKey(ForgeRegistries.ITEMS.getKey(item));
    }

    public static OrganData getEntry(Item item) {
        return GeneratedOrganData.get(ForgeRegistries.ITEMS.getKey(item));
    }

    public static boolean isTrueOrgan(Item item) {
        if (hasEntry(item)) {
            return !getEntry(item).pseudoOrgan;
        }
        return false;
    }
}
