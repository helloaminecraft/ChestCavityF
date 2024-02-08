package net.tigereye.chestcavity.chestcavities.types;


import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.chestcavities.ChestCavityInventory;
import net.tigereye.chestcavity.chestcavities.ChestCavityType;
import net.tigereye.chestcavity.chestcavities.instance.ChestCavityInstance;
import net.tigereye.chestcavity.chestcavities.organs.OrganData;
import net.tigereye.chestcavity.chestcavities.organs.OrganManager;
import net.tigereye.chestcavity.registration.CCOrganScores;
import net.tigereye.chestcavity.util.ChestCavityUtil;

import java.util.*;

public class DefaultChestCavityType implements ChestCavityType {

    private Map<ResourceLocation,Float> defaultOrganScores = null;
    private ChestCavityInventory defaultChestCavity = new ChestCavityInventory();
    private Map<ResourceLocation,Float> baseOrganScores = new HashMap<>();
    private Map<Ingredient,Map<ResourceLocation,Float>> exceptionalOrganList = new HashMap<>();
    private List<ItemStack> droppableOrgans = new LinkedList<>();
    private List<Integer> forbiddenSlots = new ArrayList<>();
    private boolean bossChestCavity = false;
    private boolean playerChestCavity = false;

    public DefaultChestCavityType(){
        prepareDefaultChestCavity();
    }

    private void prepareDefaultChestCavity(){
        for(int i = 0; i < defaultChestCavity.getContainerSize(); i++){
            defaultChestCavity.setItem(i,new ItemStack(Items.DIRT,64));
        }
    }



    @Override
    public Map<ResourceLocation,Float> getDefaultOrganScores(){
        if(defaultOrganScores == null){
            defaultOrganScores = new HashMap<>();
            if(!ChestCavityUtil.determineDefaultOrganScores(this)){
                defaultOrganScores = null;
            }
        }
        return defaultOrganScores;
    }
    @Override
    public float getDefaultOrganScore(ResourceLocation id){return getDefaultOrganScores().getOrDefault(id,0f);}
    @Override
    public ChestCavityInventory getDefaultChestCavity(){return defaultChestCavity;}
    public void setDefaultChestCavity(ChestCavityInventory inv){defaultChestCavity = inv;}

    public Map<ResourceLocation,Float> getBaseOrganScores(){return baseOrganScores;}
    public float getBaseOrganScore(ResourceLocation id){return getBaseOrganScores().getOrDefault(id,0f);}
    public void setBaseOrganScores(Map<ResourceLocation,Float> organScores){baseOrganScores = organScores;}
    public void setBaseOrganScore(ResourceLocation id, float score){baseOrganScores.put(id,score);}

    public Map<Ingredient,Map<ResourceLocation,Float>> getExceptionalOrganList(){return exceptionalOrganList;}
    public Map<ResourceLocation,Float> getExceptionalOrganScore(ItemStack itemStack){
        for(Ingredient ingredient:
                getExceptionalOrganList().keySet()){
            if(ingredient.test(itemStack)){
                return getExceptionalOrganList().get(ingredient);
            }
        }
        return null;
    }
    public void setExceptionalOrganList(Map<Ingredient,Map<ResourceLocation,Float>> list){exceptionalOrganList = list;}
    public void setExceptionalOrgan(Ingredient ingredient,Map<ResourceLocation,Float> scores){exceptionalOrganList.put(ingredient,scores);}

    public List<ItemStack> getDroppableOrgans(){
        if(droppableOrgans == null){
            deriveDroppableOrgans();
        }
        return droppableOrgans;}
    public void setDroppableOrgans(List<ItemStack> list){droppableOrgans = list;}
    private void deriveDroppableOrgans() {
        droppableOrgans = new LinkedList<>();
        for(int i = 0; i < defaultChestCavity.getContainerSize(); i++){
            ItemStack stack = defaultChestCavity.getItem(i);
            if(OrganManager.isTrueOrgan(stack.getItem())){
                droppableOrgans.add(stack);
            }
        }
    }

    public List<Integer> getForbiddenSlots(){return forbiddenSlots;}
    public void setForbiddenSlots(List<Integer> list){forbiddenSlots = list;}
    public void forbidSlot(int slot){forbiddenSlots.add(slot);}
    public void allowSlot(int slot){
        int index = forbiddenSlots.indexOf(slot);
        if(index != -1){
            forbiddenSlots.remove(index);
        }
    }
    @Override
    public boolean isSlotForbidden(int index){
        return forbiddenSlots.contains(index);
    }

    public boolean isBossChestCavity(){return bossChestCavity;}
    public void setBossChestCavity(boolean bool){
        bossChestCavity = bool;}

    public boolean isPlayerChestCavity(){return playerChestCavity;}
    public void setPlayerChestCavity(boolean bool){playerChestCavity = bool;}

    @Override
    public void fillChestCavityInventory(ChestCavityInventory chestCavity) {
        chestCavity.clearContent();
        for(int i = 0; i < chestCavity.getContainerSize(); i++){
            chestCavity.setItem(i,defaultChestCavity.getItem(i));
        }
    }

    @Override
    public void loadBaseOrganScores(Map<ResourceLocation, Float> organScores){
        organScores.clear();
    }

    @Override
    public OrganData catchExceptionalOrgan(ItemStack slot){
        Map<ResourceLocation,Float> organMap = getExceptionalOrganScore(slot);
        if(organMap != null){
            OrganData organData = new OrganData();
            organData.organScores = organMap;
            organData.pseudoOrgan = true;
            return organData;
        }
        return null;
    }

    @Override
    public List<ItemStack> generateLootDrops(Random random, int looting) {
        List<ItemStack> loot = new ArrayList<>();
        if(playerChestCavity){
            return loot;
        }
        if(bossChestCavity){
            generateGuaranteedOrganDrops(random,looting,loot);
            return loot;
        }
        if(random.nextFloat() < ChestCavity.config.UNIVERSAL_DONOR_RATE + (ChestCavity.config.ORGAN_BUNDLE_LOOTING_BOOST*looting)) {
            generateRareOrganDrops(random,looting,loot);
        }
        return loot;
    }
    public void generateRareOrganDrops(Random random, int looting, List<ItemStack> loot){
        LinkedList<ItemStack> organPile = new LinkedList<>(getDroppableOrgans());
        int rolls = 1 + random.nextInt(3) + random.nextInt(3);
        ChestCavityUtil.drawOrgansFromPile(organPile,rolls,random,loot);

    }
    public void generateGuaranteedOrganDrops(Random random, int looting, List<ItemStack> loot){
        LinkedList<ItemStack> organPile = new LinkedList<>(getDroppableOrgans());
        int rolls = 3 + random.nextInt(2+looting) + random.nextInt(2+looting);
        ChestCavityUtil.drawOrgansFromPile(organPile,rolls,random,loot);
    }

    @Override
    public void setOrganCompatibility(ChestCavityInstance instance){
        ChestCavityInventory chestCavity = instance.inventory;
        //first, make all organs personal
        for(int i = 0; i < chestCavity.getContainerSize();i++){
            ItemStack itemStack = chestCavity.getItem(i);
            if(itemStack != null && itemStack != itemStack.EMPTY){
                CompoundNBT tag = new CompoundNBT();
                tag.putUUID("owner",instance.compatibility_id);
                tag.putString("name",instance.owner.getDisplayName().getString());
                itemStack.addTagElement(ChestCavity.COMPATIBILITY_TAG.toString(),tag);
            }
        }
    }

    @Override
    public float getHeartBleedCap(){
        if(bossChestCavity){
            return 5;
        }
        return Float.MAX_VALUE;
    }

    @Override
    public boolean isOpenable(ChestCavityInstance instance){
        boolean weakEnough = instance.owner.getHealth() <= ChestCavity.config.CHEST_OPENER_ABSOLUTE_HEALTH_THRESHOLD
                || instance.owner.getHealth() <= instance.owner.getMaxHealth()*ChestCavity.config.CHEST_OPENER_FRACTIONAL_HEALTH_THRESHOLD;
        boolean chestVulnerable = instance.owner.getItemBySlot(EquipmentSlotType.CHEST).isEmpty();
        boolean easeOfAccess = instance.getOrganScore(CCOrganScores.EASE_OF_ACCESS) > 0;
        return chestVulnerable && (easeOfAccess || weakEnough);
    }

    @Override
    public void onDeath(ChestCavityInstance cc) {
        cc.projectileQueue.clear();
        if(cc.connectedCrystal != null) {
            cc.connectedCrystal.setBeamTarget(null);
            cc.connectedCrystal = null;
        }
        if(cc.opened && !(playerChestCavity && ChestCavity.config.KEEP_CHEST_CAVITY)) {
            ChestCavityUtil.dropUnboundOrgans(cc);
        }
        if(playerChestCavity){
            ChestCavityUtil.insertWelfareOrgans(cc);
        }
    }


}
