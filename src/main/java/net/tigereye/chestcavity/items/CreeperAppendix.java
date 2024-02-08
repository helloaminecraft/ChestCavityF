package net.tigereye.chestcavity.items;



import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.tigereye.chestcavity.ChestCavity;

import javax.annotation.Nullable;
import java.util.List;

public class CreeperAppendix extends Item {

    public CreeperAppendix() {
        super(new Properties().stacksTo(1).tab(ChestCavity.ORGAN_ITEM_GROUP));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag tooltipContext) {
        super.appendHoverText(itemStack,world,tooltip,tooltipContext);
        tooltip.add(new StringTextComponent("这似乎是一个导火索").withStyle(TextFormatting.ITALIC));
        tooltip.add(new StringTextComponent("其本身没有任何作用").withStyle(TextFormatting.ITALIC));
    }
}
