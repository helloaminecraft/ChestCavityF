package net.tigereye.chestcavity.util;


import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.interfaces.ChestCavityEntity;
import net.tigereye.chestcavity.registration.CCEnchantments;
import net.tigereye.chestcavity.registration.CCOrganScores;


import java.util.*;

public class ClientOrganUtil { //I moved the code that is ran on the server to CommonOrganUtil
    public static void displayOrganQuality(Map<ResourceLocation,Float> organQualityMap, List<ITextComponent> tooltip){
        organQualityMap.forEach((organ,score) -> {
            String tier;
            if(organ.equals(CCOrganScores.HYDROALLERGENIC)){
                if(score >= 2){
                    tier = "quality.chestcavity.severely";
                }
                else{
                    tier = "";
                }
            }
            else {
                if (score >= 1.5f) {
                    tier = "quality.chestcavity.supernatural";
                } else if (score >= 1.25) {
                    tier = "quality.chestcavity.exceptional";
                } else if (score >= 1) {
                    tier = "quality.chestcavity.good";
                } else if (score >= .75f) {
                    tier = "quality.chestcavity.average";
                } else if (score >= .5f) {
                    tier = "quality.chestcavity.poor";
                } else if (score >= 0) {
                    tier = "quality.chestcavity.pathetic";
                } else if (score >= -.25f) {
                    tier = "quality.chestcavity.slightly_reduces";
                } else if (score >= -.5f) {
                    tier = "quality.chestcavity.reduces";
                } else if (score >= -.75f) {
                    tier = "quality.chestcavity.greatly_reduces";
                } else {
                    tier = "残废";
                }
            }
            TranslationTextComponent text = new TranslationTextComponent("organscore." + organ.getNamespace() + "." + organ.getPath(), new TranslationTextComponent(tier));
            tooltip.add(text);
        });
    }

    public static void displayCompatibility(ItemStack itemStack, World world, List<ITextComponent> tooltip, ITooltipFlag tooltipContext) {
        CompoundNBT tag = itemStack.getTag();
        String textString;
        boolean uuidMatch = false;
        int compatLevel = 0;
        PlayerEntity serverPlayer = null;
        net.minecraft.server.MinecraftServer server = world.getServer();
        if(server == null) {
            server = Minecraft.getInstance().getSingleplayerServer();
        }
        if(server != null) {
            serverPlayer = server.getPlayerList().getPlayer(Minecraft.getInstance().player.getUUID());
            if(serverPlayer instanceof ChestCavityEntity){
                ChestCavityEntity ccPlayer = (ChestCavityEntity) serverPlayer;
                UUID ccID = ccPlayer.getChestCavityInstance().compatibility_id;
                //tooltip.add(new StringTextComponent("ServerPlayerCC: "+ccID));
                compatLevel = ChestCavityUtil.getCompatibilityLevel(ccPlayer.getChestCavityInstance(),itemStack);
            }
        }
        else{
            compatLevel = -1;
        }


        if(EnchantmentHelper.getItemEnchantmentLevel(CCEnchantments.MALPRACTICE.get(),itemStack) > 0){
            textString = "不可安全使用";
        }
        else if (tag != null && tag.contains(ChestCavity.COMPATIBILITY_TAG.toString())
                && EnchantmentHelper.getItemEnchantmentLevel(CCEnchantments.O_NEGATIVE.get(),itemStack) <= 0) {
            tag = tag.getCompound(ChestCavity.COMPATIBILITY_TAG.toString());
            String name = tag.getString("name");
            //tooltip.add(new StringTextComponent("OrganOwnerCC: "+tag.getUUID("owner")));
            textString = "仅适用于: "+name;//+" ("+compatLevel+" compat)";
        }
        else{
            textString = "可安全使用";
        }

        StringTextComponent text = new StringTextComponent("");
        if(compatLevel > 0) {
            text.withStyle(TextFormatting.GREEN);
        }
        else if(compatLevel == 0){
            text.withStyle(TextFormatting.RED);
        }
        else{
            text.withStyle(TextFormatting.YELLOW);
        }
        text.append(textString);
        tooltip.add(text);
    }


}
