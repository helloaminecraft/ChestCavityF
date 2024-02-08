package net.tigereye.chestcavity.items;



import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.tigereye.chestcavity.ChestCavity;
import net.tigereye.chestcavity.chestcavities.ChestCavityInventory;
import net.tigereye.chestcavity.chestcavities.instance.ChestCavityInstance;
import net.tigereye.chestcavity.interfaces.ChestCavityEntity;
import net.tigereye.chestcavity.registration.CCItems;
import net.tigereye.chestcavity.registration.CCOrganScores;
import net.tigereye.chestcavity.ui.ChestCavityScreenHandler;
import net.tigereye.chestcavity.util.ChestCavityUtil;

import java.util.Optional;
import java.util.UUID;

public class ChestOpener extends Item {
	
	public ChestOpener() {
		super(CCItems.CHEST_OPENER_PROPERTIES);
	}

	@Override
	public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ChestCavity.printOnDebug("ChestOpener.use() called!");
		LivingEntity target = player;
		if (openChestCavity(player, target,false)) {
			return ActionResult.sidedSuccess(player.getItemInHand(hand), false);
		} else {
			return ActionResult.pass(player.getItemInHand(hand));
		}
	}

	public boolean openChestCavity(PlayerEntity player, LivingEntity target){
		return openChestCavity(player,target,true);
	}
	public boolean openChestCavity(PlayerEntity player, LivingEntity target, boolean shouldKnockback){
		Optional<ChestCavityEntity> optional = ChestCavityEntity.of(target);
		ChestCavity.printOnDebug("ChestOpener.openChestCavity() called! Optional: " + optional.isPresent());
		ChestCavity.printOnDebug("Target Entity: " + target.toString());
		if(optional.isPresent()){
			ChestCavityEntity chestCavityEntity = optional.get();
			ChestCavityInstance cc = chestCavityEntity.getChestCavityInstance();
			if(target == player || cc.getChestCavityType().isOpenable(cc)) {
				if (cc.getOrganScore(CCOrganScores.EASE_OF_ACCESS) > 0) {
					if(player.level.isClientSide) {
						player.playNotifySound(SoundEvents.CHEST_OPEN, SoundCategory.PLAYERS, .75f, 1);
					}
				}
				else{
					if (!shouldKnockback) {
						target.hurt(DamageSource.GENERIC, 4f); // this is to prevent self-knockback, as that feels weird.
					} else {
						target.hurt(DamageSource.playerAttack(player), 4f);
					}
				}
				if (target.isAlive()) {
					String name;
					try {
						name = target.getDisplayName().getString();
						name = name.concat("'s ");
					} catch (Exception e) {
						name = "";
					}
					ChestCavityInventory inv = ChestCavityUtil.openChestCavity(cc);
					((ChestCavityEntity)player).getChestCavityInstance().ccBeingOpened = cc;
					player.openMenu(new SimpleNamedContainerProvider((i, playerInventory, playerEntity) -> new ChestCavityScreenHandler(i, playerInventory, inv), new TranslationTextComponent(name + "Chest Cavity")));
				}
				return true;
			}
			else{
				if(player.level.isClientSide) {
					if (!target.getItemBySlot(EquipmentSlotType.CHEST).isEmpty()) {
						player.sendMessage(new StringTextComponent("目标被阻挡"), new UUID(100, 0));
						player.playNotifySound(SoundEvents.CHAIN_HIT, SoundCategory.PLAYERS, .75f, 1);
					} else {
						player.sendMessage(new StringTextComponent("目标生命值过高"),new UUID(100, 0));
						player.playNotifySound(SoundEvents.ARMOR_EQUIP_TURTLE, SoundCategory.PLAYERS, .75f, 1);
					}
				}
			}
			return false;
		}
		else{
			return false;
		}
	}
}
